package com.flux.example.pages.example;

import com.flux.example.model.PersonModel;
import com.flux.example.pages.template.TemplatePage;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.TextField;
import io.jettra.flux.widgets.Div;
import io.jettra.flux.widgets.Form;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Label;
import io.jettra.flux.widgets.Notification;

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
import io.jettra.flux.widgets.Alert;
import io.jettra.flux.widgets.Icon;
import io.jettra.flux.widgets.Text;
import io.jettra.server.core.JettraContext;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role = { "ADMIN", "MANAGER" }, department = "")
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
        return msg.getProperty("personpage.title");
    }

    @ActionWidgetAllow(role = { "ADMIN", "MANAGER" })
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
            try {
                redirect(exchange, "/person?error=" + java.net.URLEncoder.encode(errorMsg.toString(), "UTF-8"));
            } catch (Exception e) {
            }
        } else {
            JettraSyncManager.notifyChange("PersonModel", SyncType.UPDATE, getLoggedUser(exchange));
            
            io.jettra.flux.widgets.NotificationTop.broadcast("main_notification", io.jettra.flux.widgets.NotificationTop.NotificationTopType.GLOBAL, "Nueva persona agregada: " + personModel.getName());

            try {
                redirect(exchange, "/person?success=true");
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
       // Properties msg = (Properties) JettraContext.getCurrent().get(JettraContext.Scope.REQUEST, "messages");
        
       // Muestra como guardar y recuperar objetos de la sesion
        PersonModel personModel = (PersonModel) JettraContext.getCurrent().get(JettraContext.Scope.SESSION, "personForm");
        if (personModel == null) {
            personModel = new PersonModel();
            JettraContext.getCurrent().set(JettraContext.Scope.SESSION, "personForm", personModel);
        }

        Widget alert = null;
        if ("true".equals(params.get("success"))) {
            alert = Alert.of(Text.of(msg.getProperty("app.label.success"))).severity("success")
                    .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 20px; width: 100%;"));
        } else if ("true".equals(params.get("error"))) {
            alert = Alert.of(Text.of(msg.getProperty("app.label.error"))).severity("danger")
                    .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 20px; width: 100%;"));
        }

        // --- Vertical Form ---
        Widget verticalForm = Card.of(Column.of(
                Header.of(4, msg.getProperty("personpage.subtitle"))
                        .modifier(new io.jettra.flux.core.Modifier().style("margin-top: 0; margin-bottom: 15px; font-weight: 600;")),
                Label.of(msg.getProperty("person.name")).forId("name")
                        .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.name") : "Name", msg.getProperty("app.label.enteryour") + ""+msg.getProperty("person.name")).id("name")
                        .binding("name")
                        .modifier(new io.jettra.flux.core.Modifier().style(
                                "margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;")),
                Label.of(msg.getProperty("person.email")).forId("email")
                        .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.email") : "Email", msg.getProperty("app.label.enteryour") + ""+msg.getProperty("person.email")).id("email")
                        .binding("email")
                        .modifier(new io.jettra.flux.core.Modifier().style(
                                "margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;")),
                Label.of(msg.getProperty("person.age")).forId("age")
                        .modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 5px; font-weight: 500; display: block;")),
                TextField.of(msg != null ? msg.getProperty("person.age") : "Age", msg.getProperty("app.label.enteryour") + ""+msg.getProperty("person.age")).id("age")
                        .binding("age")
                        .modifier(new io.jettra.flux.core.Modifier().style(
                                "margin-bottom: 15px; width: 100%; border: 1px solid #d1d5db; border-radius: 6px; padding: 8px 12px;")))
                .modifier(new io.jettra.flux.core.Modifier().style("width: 100%; align-items: stretch; gap: 5px;")));

        // Submit form wrapper
        Widget mainForm = Form.of(
                Column.of(
                        Grid.of(verticalForm).modifier(new io.jettra.flux.core.Modifier().style(
                                "grid-template-columns: 1fr 1fr; gap: 20px; align-items: flex-start; margin-bottom: 20px;")),
                        Row.of(
                                ElevatedButton.of(msg != null ? msg.getProperty("btn.save") : "Save")
                                        .modifier(new io.jettra.flux.core.Modifier().style(
                                                "align-self: flex-start; padding: 10px 20px; background-color: #6366F1; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: 600;")),
                                
                                ElevatedButton.of(Row.of(Icon.of("fas fa-globe"), Text.of(" Send Global").modifier(new io.jettra.flux.core.Modifier().style("margin-left:5px;"))))
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #0d6efd; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;"))
                                        .onClick(c -> {
                                            if (hasRoleAdminOrManager()) {
                                                io.jettra.flux.widgets.NotificationTop.broadcast("global_notif", io.jettra.flux.widgets.NotificationTop.NotificationTopType.GLOBAL, "Alerta Global: Sistema actualizado");
                                            }
                                        }),
                                        
                                ElevatedButton.of(Row.of(Icon.of("fas fa-envelope"), Text.of(" Send Personal").modifier(new io.jettra.flux.core.Modifier().style("margin-left:5px;"))))
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #17a2b8; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;"))
                                        .onClick(c -> {
                                            if (hasRoleAdminOrManager()) {
                                                io.jettra.flux.widgets.NotificationTop.broadcast("personal_notif", io.jettra.flux.widgets.NotificationTop.NotificationTopType.PERSONAL, "Mensaje Privado: Tienes un nuevo correo");
                                            }
                                        }),
                                        
                                ElevatedButton.of(Row.of(Icon.of("fas fa-bullhorn"), Text.of(" Send Channel").modifier(new io.jettra.flux.core.Modifier().style("margin-left:5px;"))))
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #dc3545; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;"))
                                        .onClick(c -> {
                                            if (hasRoleAdminOrManager()) {
                                                io.jettra.flux.widgets.NotificationTop.broadcast("channel_notif", io.jettra.flux.widgets.NotificationTop.NotificationTopType.CHANNEL, "Canal Admins: Nuevo reporte disponible");
                                            }
                                        })
                                )
                                .modifier(new io.jettra.flux.core.Modifier()
                                        .style("display: flex; flex-direction: row; align-items: center;")))
                        .modifier(new io.jettra.flux.core.Modifier().style("width: 100%;")))
                .action(JettraServer.resolvePath("/person?_action_method=saveForm")).method("POST");

        // --- Center Content ---
        return Column.of(
                Header.of(2, msg.getProperty("personpage.title"))
                        .modifier(new io.jettra.flux.core.Modifier().style("margin-top: 0; font-weight: 600; margin-bottom: 20px;")),
                (alert != null ? alert : Div.of()),
                mainForm)
                .modifier(new io.jettra.flux.core.Modifier()
                        .style("width: 100%; align-items: flex-start; max-width: 1200px; padding: 20px;"));
    }

    private boolean hasRoleAdminOrManager() {
        String role = io.jettra.server.core.JettraContext.Scope.SESSION.getRole();
        return "ADMIN".equals(role) || "MANAGER".equals(role);
    }
}
