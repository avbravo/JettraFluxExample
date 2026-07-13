package com.facli.server.pages;

import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Dashboard;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Checkbox;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.TextField;
import io.jettra.flux.widgets.Footer;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.Div;
import io.jettra.flux.widgets.RadioButton;
import io.jettra.flux.widgets.Form;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Link;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Left;
import io.jettra.flux.widgets.MenuItem;
import io.jettra.flux.widgets.Menu;
import io.jettra.flux.widgets.ElevatedButton;
import io.jettra.flux.widgets.Label;
import io.jettra.flux.widgets.Notification;
import io.jettra.flux.widgets.Center;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;

import java.io.IOException;
import java.util.Map;
import io.jettra.flux.widgets.ThemeChanged;

public class FormsPage extends FluxBaseHandler {

    @Override
    protected String getTitle() {
        return "Forms - JettraFlux";
    }

    @Override
    protected boolean onGet(HttpExchange exchange, Map<String, String> params) throws IOException {
        String user = getLoggedUser(exchange);
        if (user == null || user.isEmpty()) {
            redirect(exchange, "/login");
            return true;
        }
        return false;
    }

    @Override
    protected boolean onPost(HttpExchange exchange, Map<String, String> params) throws IOException {
        // En un caso real, procesaríamos el formulario aquí
        System.out.println("Formulario recibido con datos: " + params);
        // Redirigir de vuelta con un mensaje de éxito
        redirect(exchange, "/forms?success=true");
        return true;
    }

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        String username = getLoggedUser(exchange);
        if (username == null) {
            username = "Guest";
        }

        // --- Menu (Left) ---
        Widget menu = Left.of(
            Header.of(3, "Navegación"),
            Menu.of(
                MenuItem.of(Link.of(JettraServer.resolvePath("/dashboard"), "Dashboard Principal")),
                MenuItem.of(Link.of(JettraServer.resolvePath("/forms"), "Ejemplos Formularios")),
                MenuItem.of(Link.of(JettraServer.resolvePath("/login?logout=true"), "Cerrar Sesión"))
            )
        );

        // --- Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                Header.of(2, "JettraFlux Admin - Formularios"),
                ThemeChanged.of().current(currentTheme),
                Paragraph.of("Usuario: " + username)
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-row"))
        );

        Widget alert = null;
        if ("true".equals(params.get("success"))) {
            alert = Notification.of(Paragraph.of("¡Formulario enviado correctamente!"));
        }

        // --- Forms Demo Content ---
        Widget sampleForm = Form.of(Card.of(Column.of(
                    Header.of(3, "Datos Personales"),
                    
                    Label.of("Nombre Completo").forId("nombre"),
                    TextField.of("nombre", "Ingrese su nombre..."),
                    
                    Label.of("Correo Electrónico").forId("correo"),
                    TextField.of("correo", "ejemplo@correo.com"),

                    Header.of(4, "Preferencias"),
                    Checkbox.of("suscripcion", "Suscribirse al boletín"),
                    Checkbox.of("terminos", "Acepto los términos y condiciones"),
                    
                    Header.of(4, "Opciones de Tema"),
                    RadioButton.create().name("tema").value("claro").label("Claro").checked(true),
                    RadioButton.create().name("tema").value("oscuro").label("Oscuro"),

                    ElevatedButton.of("Guardar Cambios")
                ).modifier(new io.jettra.flux.core.Modifier().padding(15))
            )
        ).action(JettraServer.resolvePath("/forms")).method("POST");

        // --- Center Content ---
        Widget centerContent = Center.of(
            Column.of(
                Header.of(1, "Componentes de Formulario JettraFlux"),
                Paragraph.of("Aquí puedes ver ejemplos de inputs, checkboxes, y radio buttons usando la librería Espresso."),
                (alert != null ? alert : Div.of()),
                sampleForm
            )
        );

        // --- Footer ---
        Widget footerContent = Footer.of(
            Paragraph.of("© 2026 JettraStack Foundation - Powered by JettraFlux")
        );

        Widget body = Dashboard.of(Row.of(menu,
                Column.of(
                    topBar,
                    centerContent,
                    footerContent
                ).modifier(new io.jettra.flux.core.Modifier().width("80%"))
            ).modifier(new io.jettra.flux.core.Modifier().width("100%"))
        );

        return Scaffold.of().body(body);
    }
}
