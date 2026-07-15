package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.jettra.flux.core.Widget;
import io.jettra.flux.theme.ThemeData;
import io.jettra.server.JettraServer;
import io.jettra.server.core.JettraContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class FluxBaseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // --- Security Filter ---
        boolean isNoLogin = this.getClass().isAnnotationPresent(io.jettra.core.login.NoLoginRequired.class);
        
        if (!isNoLogin) {
            String username = getLoggedUser(exchange);
            if (username == null || username.isEmpty()) {
                io.jettra.flux.widgets.Modal modal = io.jettra.flux.widgets.Modal.of(
                    io.jettra.flux.widgets.Column.of(
                        io.jettra.flux.widgets.Label.of("Sesión Requerida").modifier(new io.jettra.flux.core.Modifier().cssClass("bold").padding(10)),
                        io.jettra.flux.widgets.Paragraph.of("Su sesión ha expirado o no ha iniciado sesión. Redirigiendo al login en 3 segundos..."),
                        io.jettra.flux.widgets.Paragraph.of("<script>setTimeout(function(){ window.location.href='" + io.jettra.server.JettraServer.resolvePath("/login") + "'; }, 3000);</script>")
                    )
                );
                modal.open(true);
                
                String themeName = getThemeCookie(exchange);
                if (themeName == null || themeName.isEmpty()) themeName = "Ast";
                io.jettra.flux.theme.ThemeData theme = getThemeByName(themeName);
                
                String html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
                        + "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\">\n"
                        + theme.generateGlobalCss()
                        + "</head><body style=\"margin:0; padding:0; box-sizing: border-box;\">"
                        + io.jettra.flux.widgets.Scaffold.of().body(modal).render(theme)
                        + "</body></html>";
                        
                renderResponse(exchange, html, 401);
                return;
            }
            
            // Verificación de @PageWidgetAllow
            if (this.getClass().isAnnotationPresent(io.jettra.core.security.widget.PageWidgetAllow.class)) {
                io.jettra.core.security.widget.PageWidgetAllow pageAllow = this.getClass().getAnnotation(io.jettra.core.security.widget.PageWidgetAllow.class);
                String userRole = getLoggedRole(exchange);
                String userDept = getLoggedDepartment(exchange);
                boolean hasAccess = false;
                if (pageAllow.role().length == 0) {
                    hasAccess = true;
                } else {
                    for (String r : pageAllow.role()) {
                        if (r.equalsIgnoreCase(userRole)) {
                            hasAccess = true;
                            break;
                        }
                    }
                }
                if (hasAccess && !pageAllow.department().isEmpty()) {
                    if (!pageAllow.department().equalsIgnoreCase(userDept)) {
                        hasAccess = false;
                    }
                }
                if (!hasAccess) {
                    io.jettra.flux.widgets.Modal modal = io.jettra.flux.widgets.Modal.of(
                        io.jettra.flux.widgets.Column.of(
                            io.jettra.flux.widgets.Label.of("Acceso Denegado").modifier(new io.jettra.flux.core.Modifier().cssClass("bold").padding(10)),
                            io.jettra.flux.widgets.Paragraph.of("No tienes los permisos (" + String.join(",", pageAllow.role()) + ") necesarios para ver esta página."),
                            io.jettra.flux.widgets.Paragraph.of("<button onclick=\"window.location.href='" + io.jettra.server.JettraServer.resolvePath("/login") + "'\" style=\"padding: 10px 20px; background: #3b82f6; color: white; border: none; border-radius: 5px; cursor: pointer; margin-top: 15px;\">Cerrar</button>")
                        )
                    );
                    modal.open(true);
                    String themeName = getThemeCookie(exchange);
                    if (themeName == null || themeName.isEmpty()) themeName = "Ast";
                    io.jettra.flux.theme.ThemeData theme = getThemeByName(themeName);
                    String html = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\">"
                            + "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\">\n"
                            + theme.generateGlobalCss()
                            + "</head><body style=\"margin:0; padding:0; box-sizing: border-box;\">"
                            + io.jettra.flux.widgets.Scaffold.of().body(modal).render(theme)
                            + "</body></html>";
                    renderResponse(exchange, html, 403);
                    return;
                }
            }
        }
        // --- Fin Security Filter ---
        
        Map<String, String> params = new HashMap<>(parseQueryParams(exchange.getRequestURI().getQuery()));
        
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            params.putAll(parseRequestBody(exchange));
            
            // Verificación de @ActionWidgetAllow (Invocación de Método)
            if (params.containsKey("_action_method")) {
                String methodName = params.get("_action_method");
                try {
                    java.lang.reflect.Method method = this.getClass().getDeclaredMethod(methodName, HttpExchange.class, Map.class);
                    if (method.isAnnotationPresent(io.jettra.core.security.widget.ActionWidgetAllow.class)) {
                        io.jettra.core.security.widget.ActionWidgetAllow actionAllow = method.getAnnotation(io.jettra.core.security.widget.ActionWidgetAllow.class);
                        String userRole = getLoggedRole(exchange);
                        String userDept = getLoggedDepartment(exchange);
                        boolean hasAccess = false;
                        if (actionAllow.role().length == 0) {
                            hasAccess = true;
                        } else {
                            for (String r : actionAllow.role()) {
                                if (r.equalsIgnoreCase(userRole)) {
                                    hasAccess = true;
                                    break;
                                }
                            }
                        }
                        if (hasAccess && !actionAllow.department().isEmpty()) {
                            if (!actionAllow.department().equalsIgnoreCase(userDept)) {
                                hasAccess = false;
                            }
                        }
                        if (!hasAccess) {
                            String errorHtml = "<script>alert('Acceso Denegado al Método: " + methodName + "'); window.history.back();</script>";
                            renderResponse(exchange, errorHtml, 403);
                            return;
                        }
                    }
                    method.setAccessible(true);
                    method.invoke(this, exchange, params);
                    return; // Si el método se invoca con éxito, asume que manejó la redirección.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (onPost(exchange, params)) {
                return; // If onPost returns true, it handled the response (e.g. redirect)
            }
        } else {
            if (onGet(exchange, params)) {
                return; // If onGet returns true, it handled the response
            }
        }

        // Render UI
        String themeName = getThemeCookie(exchange);
        if (themeName == null || themeName.isEmpty()) {
            themeName = "Ast"; // Default theme
        }
        
        io.jettra.flux.theme.ThemeData theme = getThemeByName(themeName);
        Widget ui = buildUI(exchange, params, themeName);
        if (ui != null) {
            String html = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n" +
                          "<meta charset=\"UTF-8\">\n" +
                          "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                          "<title>" + getTitle() + "</title>\n" +
                          "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css\">\n" +
                          theme.generateGlobalCss() + "\n" +
                          "</head>\n<body style=\"margin: 0; padding: 0; box-sizing: border-box;\">\n" +
                          ui.render(theme) + "\n" +
                          "</body>\n</html>";
            renderResponse(exchange, html, 200);
        }
    }

    protected abstract Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme);

    protected String getTitle() {
        return "JettraFlux App";
    }

    protected boolean onGet(HttpExchange exchange, Map<String, String> params) throws IOException {
        return false;
    }

    protected boolean onPost(HttpExchange exchange, Map<String, String> params) throws IOException {
        return false;
    }

    protected void redirect(HttpExchange exchange, String path) throws IOException {
        exchange.getResponseHeaders().set("Location", JettraServer.resolvePath(path));
        exchange.sendResponseHeaders(302, -1);
        exchange.getResponseBody().close();
    }

    protected void renderResponse(HttpExchange exchange, String html, int statusCode) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    protected void setSessionCookie(HttpExchange exchange, String username, String role, String department) {
        String cPath = JettraServer.getContextPath();
        if (cPath == null || cPath.isEmpty()) cPath = "/";
        exchange.getResponseHeaders().set("Set-Cookie", "username=" + username + "; Path=" + cPath);
        exchange.getResponseHeaders().add("Set-Cookie", "role=" + role + "; Path=" + cPath);
        exchange.getResponseHeaders().add("Set-Cookie", "department=" + department + "; Path=" + cPath);
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "username", username);
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "role", role);
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "department", department);
    }

    protected void setSessionCookie(HttpExchange exchange, String username, String role) {
        setSessionCookie(exchange, username, role, "");
    }

    protected void setSessionCookie(HttpExchange exchange, String username) {
        setSessionCookie(exchange, username, "USER", "");
    }

    protected void clearSessionCookie(HttpExchange exchange) {
        String cPath = JettraServer.getContextPath();
        if (cPath == null || cPath.isEmpty()) cPath = "/";
        exchange.getResponseHeaders().set("Set-Cookie", "username=; Path=" + cPath + "; Max-Age=0");
        exchange.getResponseHeaders().add("Set-Cookie", "role=; Path=" + cPath + "; Max-Age=0");
        exchange.getResponseHeaders().add("Set-Cookie", "department=; Path=" + cPath + "; Max-Age=0");
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "username", null);
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "role", null);
        JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "department", null);
    }

    protected String getLoggedUser(HttpExchange exchange) {
        // Simple cookie parse
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookies != null) {
            for (String c : cookies.split(";")) {
                c = c.trim();
                if (c.startsWith("username=")) {
                    return c.substring("username=".length());
                }
            }
        }
        return null;
    }

    protected String getLoggedRole(HttpExchange exchange) {
        // Simple cookie parse for role
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookies != null) {
            for (String c : cookies.split(";")) {
                c = c.trim();
                if (c.startsWith("role=")) {
                    return c.substring("role=".length());
                }
            }
        }
        return "USER";
    }

    protected String getLoggedDepartment(HttpExchange exchange) {
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookies != null) {
            for (String c : cookies.split(";")) {
                c = c.trim();
                if (c.startsWith("department=")) {
                    return c.substring("department=".length());
                }
            }
        }
        return "";
    }

    protected String getThemeCookie(HttpExchange exchange) {
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookies != null) {
            for (String c : cookies.split(";")) {
                c = c.trim();
                if (c.startsWith("jettra_theme=")) {
                    return c.substring("jettra_theme=".length());
                }
            }
        }
        return null;
    }

    protected io.jettra.flux.theme.ThemeData getThemeByName(String name) {
        if ("FlatTheme".equalsIgnoreCase(name)) return io.jettra.flux.theme.Themes.FlatTheme();
        if ("Theme3D".equalsIgnoreCase(name)) return io.jettra.flux.theme.Themes.Theme3D();
        if ("FuturisticTheme".equalsIgnoreCase(name)) return io.jettra.flux.theme.Themes.FuturisticTheme();
        if ("AtlantisTheme".equalsIgnoreCase(name)) return io.jettra.flux.theme.Themes.AtlantisTheme();
        if ("OceanTheme".equalsIgnoreCase(name)) return io.jettra.flux.theme.Themes.OceanTheme();
        return io.jettra.flux.theme.Themes.AstTheme();
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) map.put(kv[0], kv[1]);
        }
        return map;
    }

    private Map<String, String> parseRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
        }
        String formData = sb.toString();
        Map<String, String> map = new HashMap<>();
        if (formData.isEmpty()) return map;
        for (String pair : formData.split("&")) {
            String[] kv = pair.split("=");
            try {
                if (kv.length == 2) map.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8), URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            } catch (Exception e) {}
        }
        return map;
    }
}
