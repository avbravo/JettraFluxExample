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
import io.jettra.server.JettraServer;

import java.util.Map;

@io.jettra.core.login.NoLoginRequired
public class LoginPage extends FluxBaseHandler {

    @Override
    protected String getTitle() {
        return "Login - JettraFlux";
    }

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        new io.jettra.flux.core.ActionBinder(params)
            .on("logout", () -> {
                clearSessionCookie(exchange);
                try { redirect(exchange, "/login"); } catch (Exception e) {}
            })
            .on("username", p -> {
                String user = p.get("username");
                String pass = p.get("password");
                try {
                    if (isValidUser(user, pass)) {
                        //Registra las credenciales
                        CredentialFlux CredentialFlux = new CredentialFlux(user, user+"Prueba", "ADMIN", "", "");
                        setSessionCookie(exchange, user);
                        redirect(exchange, "/dashboard");
                    } else {
                        redirect(exchange, "/login?error=invalid_credentials");
                    }
                } catch (Exception e) {}
            })
            .execute();
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
