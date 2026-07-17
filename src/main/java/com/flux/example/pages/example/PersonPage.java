package com.flux.example.pages.example;

import com.flux.example.model.PersonModel;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.TextField;
import io.jettra.flux.widgets.Div;
import io.jettra.flux.widgets.Form;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Label;
import io.jettra.flux.widgets.Notification;
import com.flux.example.pages.template.TemplatePage;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.core.inject.annotation.InjectProperties;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.core.security.widget.ActionWidgetAllow;
import io.jettra.flux.annotations.binding.FluxBinding;
import io.jettra.flux.binding.FluxBinder;
import io.jettra.flux.core.Modifier;
import io.jettra.flux.sync.JettraPageSincronized;
import io.jettra.flux.sync.JettraSyncManager;
import io.jettra.flux.sync.SyncType;
import io.jettra.flux.widgets.ElevatedButton;
import io.jettra.flux.widgets.Grid;
import io.jettra.flux.widgets.Row;
import io.jettra.rules.core.RuleResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import io.jettra.core.server.Page;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role = {"ADMIN", "MANAGER"}, department = "")
@Page(path = "/person")
public class PersonPage extends TemplatePage {

    @InjectProperties(name = "messages")
    private Properties msg;

    /**
     * Model
     */
    @FluxBinding(model = PersonModel.class)
    PersonModel personModel = new PersonModel();
    List<PersonModel> persons = new ArrayList<>();

    @Override
    public String getTitle() {
        return "Forms - JettraFlux Pro";
    }

    @ActionWidgetAllow(role = {"ADMIN", "MANAGER"})
    private void saveForm(HttpExchange exchange, Map<String, String> params) {
        IO.println("Formulario recibido con datos (Método de acción seguro): " + params);

        List<RuleResult> results = new FluxBinder(personModel)
                .bind(params)
                .compute()
                .validate();

        IO.print("--> personModel " + personModel.toString() + " " + personModel.getName());
        boolean hasErrors = false;
        StringBuilder errorMsg = new StringBuilder();
        for (RuleResult result : results) {
            if (!result.isValid()) {
                hasErrors = true;
                errorMsg.append(result.getMessage()).append(" ");
            }
        }

        if (hasErrors) {
            IO.println("Error de validación: " + errorMsg.toString());
        } else {
            JettraSyncManager.notifyChange("FormsModel", SyncType.UPDATE, getLoggedUser(exchange));
            try {
                redirect(exchange, "/person?success=true");
            } catch (Exception e) {
            }
        }
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

        // --- Vertical Form ---
        Widget verticalForm = Card.of(Column.of(
                Header.of(4, "Vertical").modifier(new Modifier().style("margin-top: 0; margin-bottom: 15px; font-weight: 600;")),
                Label.of("Name").forId("name").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.name") : "Name", "Enter your name").id("name").binding("name").modifier(new Modifier().style("margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;")),
                Label.of("Email").forId("email").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.email") : "Email", "Enter your email").id("email").binding("email").modifier(new Modifier().style("margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;")),
                Label.of("Age").forId("age").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.age") : "Age", "Enter your age").id("age").binding("age").modifier(new Modifier().style("margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;"))
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; gap: 5px;")));

        // Submit form wrapper
        Widget mainForm = Form.of(
                Column.of(
                        Grid.of(verticalForm).modifier(new Modifier().style("grid-template-columns: 1fr 1fr; gap: 20px; align-items: flex-start; margin-bottom: 20px;")),
                        Row.of(
                                ElevatedButton.of("Guardar Cambios").modifier(new Modifier().style("align-self: flex-start; padding: 10px 20px; background-color: #6366F1; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 600;"))
                        ).modifier(new Modifier().style("display: flex; flex-direction: row; align-items: center;"))
                ).modifier(new Modifier().style("width: 100%;"))
        ).action(JettraServer.resolvePath("/forms?_action_method=saveForm")).method("POST");

        // --- Center Content ---
        return Column.of(
                Header.of(2, "Person ").modifier(new Modifier().style("margin-top: 0; font-weight: 600; margin-bottom: 20px;")),
                (alert != null ? alert : Div.of()),
                mainForm
        ).modifier(new Modifier().style("width: 100%; align-items: flex-start; max-width: 1200px; padding: 20px;"));
    }
}
