package com.flux.example.pages.login;

import com.flux.example.pages.FluxBaseHandler;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Login;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Notification;
import io.jettra.flux.widgets.Center;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.core.server.Page;
import io.jettra.flux.core.Widget;
import io.jettra.flux.model.CredentialFlux;
import io.jettra.json.SessionScoped;
import io.jettra.server.JettraServer;

import java.util.Map;

@io.jettra.core.login.NoLoginRequired
@SessionScoped
@Page(path = "/login")
public class LoginPage extends FluxBaseHandler {

    @Override
    protected String getTitle() {
        return "Login - JettraFlux";
    }

    @Override
    protected boolean onPost(HttpExchange exchange, Map<String, String> params) throws java.io.IOException {
        if (params.containsKey("username")) {
            String user = params.get("username");
            String pass = params.get("password");
            if (isValidUser(user, pass)) {
                CredentialFlux credentialFlux = new CredentialFlux(user, user + "Prueba", "ADMIN", "", "");
                io.jettra.server.core.JettraContext.getCurrent().set(io.jettra.server.core.JettraContext.Scope.SESSION, "credentialFlux", credentialFlux);
                setSessionCookie(exchange, user, credentialFlux.role(), credentialFlux.department());
                redirect(exchange, "/dashboard");
                return true;
            } else {
                redirect(exchange, "/login?error=invalid_credentials");
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onGet(HttpExchange exchange, Map<String, String> params) throws java.io.IOException {
        if ("true".equals(params.get("logout"))) {
            clearSessionCookie(exchange);
            redirect(exchange, "/login");
            return true;
        }
        return false;
    }

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        Widget loginForm = Login.create().action(JettraServer.resolvePath("/login")).title("JettraFlux Admin").logo("https://primefaces.org/cdn/primeng/images/galleria/galleria1.jpg").forgotPasswordUrl(JettraServer.resolvePath("/forgot-password"));
        
        Widget body = Center.of(
            Column.of(
                loginForm
            )
        );

        if (params.containsKey("error")) {
            Widget errorDialog = io.jettra.flux.widgets.Modal.of(
                Column.of(
                    io.jettra.flux.widgets.Header.of(4, "Advertencia de Autenticación")
                            .modifier(new io.jettra.flux.core.Modifier().style("color: #b91c1c; margin-top: 0; margin-bottom: 10px; font-weight: 600;")),
                    Paragraph.of("El nombre de usuario o la contraseña ingresados no son válidos. Por favor verifique sus credenciales.")
                            .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 20px; color: #374151; font-size: 14px;")),
                    io.jettra.flux.widgets.ElevatedButton.of("Aceptar")
                            .attribute("onclick", "this.closest('.espresso-modal-overlay').style.display='none'; return false;")
                            .modifier(new io.jettra.flux.core.Modifier().style("background-color: #ef4444; color: white; border: none; padding: 8px 20px; border-radius: 6px; cursor: pointer; font-weight: 500; align-self: flex-end;"))
                ).modifier(new io.jettra.flux.core.Modifier().style("width: 100%; max-width: 400px; gap: 10px;"))
            ).open(true);

            body = Center.of(
                Column.of(
                    errorDialog,
                    loginForm
                )
            );
        }

        return Scaffold.of().body(body);
    }

    private boolean isValidUser(String user, String pass) {
        return ("admin".equals(user) && "admin".equals(pass)) || 
               ("demo".equals(user) && "demo".equals(pass)) || 
               ("avbravo".equals(user) && "avbravo".equals(pass));
    }
}
