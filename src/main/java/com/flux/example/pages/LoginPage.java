package com.flux.example.pages;

import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Login;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Notification;
import io.jettra.flux.widgets.Center;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;

import java.io.IOException;
import java.util.Map;

public class LoginPage extends FluxBaseHandler {

    @Override
    protected String getTitle() {
        return "Login - JettraFlux";
    }

    @Override
    protected boolean onGet(HttpExchange exchange, Map<String, String> params) throws IOException {
        if (params.containsKey("logout")) {
            clearSessionCookie(exchange);
            redirect(exchange, "/login");
            return true;
        }
        return false;
    }

    @Override
    protected boolean onPost(HttpExchange exchange, Map<String, String> params) throws IOException {
        String user = params.get("username");
        String pass = params.get("password");

        if (isValidUser(user, pass)) {
            setSessionCookie(exchange, user);
            redirect(exchange, "/dashboard");
        } else {
            redirect(exchange, "/login?error=invalid_credentials");
        }
        return true;
    }

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
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
