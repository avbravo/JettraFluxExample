package com.flux.example.pages.login;

import com.flux.example.pages.FluxBaseHandler;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Login;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Notification;
import io.jettra.flux.widgets.Center;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.model.CredentialFlux;
import io.jettra.json.SessionScoped;
import io.jettra.server.JettraServer;

import java.util.Map;

@io.jettra.core.login.NoLoginRequired
@SessionScoped
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
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        if ("true".equals(params.get("logout"))) {
            clearSessionCookie(exchange);
            try { redirect(exchange, "/login"); } catch (Exception e) {}
            return Column.of();
        }
        Widget loginForm = Login.create().action(JettraServer.resolvePath("/login")).title("JettraFlux Admin");
        
        Widget body = Center.of(
            Column.of(
                loginForm
            )
        );

        if (params.containsKey("error")) {
            Widget errorAlert = Notification.of(Paragraph.of("Error: Username y/o password no válidos"));
            body = Center.of(
                Column.of(
                    errorAlert,
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
