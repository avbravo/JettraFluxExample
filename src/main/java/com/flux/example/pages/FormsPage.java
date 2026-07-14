package com.flux.example.pages;

import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Checkbox;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.TextField;
import io.jettra.flux.widgets.Div;
import io.jettra.flux.widgets.RadioButton;
import io.jettra.flux.widgets.Form;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.ElevatedButton;
import io.jettra.flux.widgets.Label;
import io.jettra.flux.widgets.Notification;
import io.jettra.flux.widgets.Center;
import com.flux.example.pages.template.TemplatePage;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.core.security.widget.ActionWidgetAllow;
import java.util.Map;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role={"ADMIN","MANAGER"}, department="") 
public class FormsPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Forms - JettraFlux Pro";
    }

    @ActionWidgetAllow(role={"ADMIN","MANAGER"}, department="")
    private void saveForm(HttpExchange exchange, Map<String, String> params) {
        System.out.println("Formulario recibido con datos (Método de acción seguro): " + params);
        try { redirect(exchange, "/forms?success=true"); } catch (Exception e) {}
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        // Fallback para ActionBinder en caso de no usar _action_method (Se deja por compatibilidad si es necesario, 
        // pero preferimos el _action_method)
        new io.jettra.flux.core.ActionBinder(params)
            .on("nombre", p -> {
                // Ignore, handled by saveForm if _action_method is present
            })
            .execute();

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
        ).action(JettraServer.resolvePath("/forms?_action_method=saveForm")).method("POST");

        // --- Center Content ---
        return Center.of(
            Column.of(
                Header.of(1, "Componentes de Formulario JettraFlux"),
                Paragraph.of("Aquí puedes ver ejemplos de inputs, checkboxes, y radio buttons usando la librería Espresso."),
                (alert != null ? alert : Div.of()),
                sampleForm
            )
        );
    }
}
