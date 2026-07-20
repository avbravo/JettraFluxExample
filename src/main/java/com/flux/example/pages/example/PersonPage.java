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
import io.jettra.flux.widgets.Icon;
import io.jettra.flux.widgets.Text;

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
            
            io.jettra.server.core.JettraContext ctx = io.jettra.server.core.JettraContext.getCurrent();
            String currId = ctx != null ? ctx.getSessionId() : null;
            
            java.util.Map<String, java.util.Map<String, Object>> allSessions = io.jettra.server.core.JettraContext.getSessions();
            if (allSessions != null) {
                for (java.util.Map.Entry<String, java.util.Map<String, Object>> entry : allSessions.entrySet()) {
                    if (currId == null || !entry.getKey().equals(currId)) {
                        java.util.Map<String, Object> sessionVars = entry.getValue();
                        if (sessionVars != null) {
                            java.util.Map<String, io.jettra.flux.widgets.NotificationTop> notifs = 
                                (java.util.Map<String, io.jettra.flux.widgets.NotificationTop>) sessionVars.get("template_notifications");
                            if (notifs != null) {
                                io.jettra.flux.widgets.NotificationTop otherNt = notifs.get("main_notification");
                                if (otherNt != null) {
                                    otherNt.value = (otherNt.value != null ? otherNt.value : 0) + 1;
                                    otherNt.addMessage("Nueva persona agregada: " + personModel.getName());
                                }
                            }
                        }
                    }
                }
            }

            try {
                redirect(exchange, "/person?success=true");
            } catch (Exception e) {
            }
        }
    }

    @ActionWidgetAllow(role = { "ADMIN", "MANAGER" })
    private void notifyGlobal(HttpExchange exchange, Map<String, String> params) {
        java.util.Map<String, java.util.Map<String, Object>> allSessions = io.jettra.server.core.JettraContext.getSessions();
        if (allSessions != null) {
            for (java.util.Map.Entry<String, java.util.Map<String, Object>> entry : allSessions.entrySet()) {
                java.util.Map<String, Object> sessionVars = entry.getValue();
                if (sessionVars != null) {
                    java.util.Map<String, io.jettra.flux.widgets.NotificationTop> notifs = 
                        (java.util.Map<String, io.jettra.flux.widgets.NotificationTop>) sessionVars.get("template_notifications");
                    if (notifs != null) {
                        io.jettra.flux.widgets.NotificationTop globalNt = notifs.get("global_notif");
                        if (globalNt != null && globalNt.getType() == io.jettra.flux.widgets.NotificationTop.NotificationTopType.GLOBAL) {
                            globalNt.value = (globalNt.value != null ? globalNt.value : 0) + 1;
                            globalNt.addMessage("Alerta Global: Sistema actualizado");
                        }
                    }
                }
            }
        }
        try { redirect(exchange, "/person"); } catch (Exception e) {}
    }

    @ActionWidgetAllow(role = { "ADMIN", "MANAGER" })
    private void notifyPersonal(HttpExchange exchange, Map<String, String> params) {
        io.jettra.server.core.JettraContext ctx = io.jettra.server.core.JettraContext.getCurrent();
        if (ctx != null) {
            java.util.Map<String, io.jettra.flux.widgets.NotificationTop> notifs = 
                (java.util.Map<String, io.jettra.flux.widgets.NotificationTop>) ctx.get(io.jettra.server.core.JettraContext.Scope.SESSION, "template_notifications");
            if (notifs != null) {
                io.jettra.flux.widgets.NotificationTop personalNt = notifs.get("personal_notif");
                if (personalNt != null && personalNt.getType() == io.jettra.flux.widgets.NotificationTop.NotificationTopType.PERSONAL) {
                    personalNt.value = (personalNt.value != null ? personalNt.value : 0) + 1;
                    personalNt.addMessage("Mensaje Privado: Tienes un nuevo correo");
                }
            }
        }
        try { redirect(exchange, "/person"); } catch (Exception e) {}
    }

    @ActionWidgetAllow(role = { "ADMIN", "MANAGER" })
    private void notifyChannel(HttpExchange exchange, Map<String, String> params) {
        io.jettra.server.core.JettraContext ctx = io.jettra.server.core.JettraContext.getCurrent();
        String currId = ctx != null ? ctx.getSessionId() : null;
        
        java.util.Map<String, java.util.Map<String, Object>> allSessions = io.jettra.server.core.JettraContext.getSessions();
        if (allSessions != null) {
            for (java.util.Map.Entry<String, java.util.Map<String, Object>> entry : allSessions.entrySet()) {
                if (currId == null || !entry.getKey().equals(currId)) {
                    java.util.Map<String, Object> sessionVars = entry.getValue();
                    if (sessionVars != null) {
                        java.util.Map<String, io.jettra.flux.widgets.NotificationTop> notifs = 
                            (java.util.Map<String, io.jettra.flux.widgets.NotificationTop>) sessionVars.get("template_notifications");
                        if (notifs != null) {
                            io.jettra.flux.widgets.NotificationTop channelNt = notifs.get("channel_notif");
                            if (channelNt != null && channelNt.getType() == io.jettra.flux.widgets.NotificationTop.NotificationTopType.CHANNEL && "admin_channel".equals(channelNt.getChannel())) {
                                channelNt.value = (channelNt.value != null ? channelNt.value : 0) + 1;
                                channelNt.addMessage("Canal Admins: Nuevo reporte disponible");
                            }
                        }
                    }
                }
            }
        }
        try { redirect(exchange, "/person"); } catch (Exception e) {}
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        // Language properties are loaded automatically via @InjectProperties DependencyInjector

        // Fallback para ActionBinder en caso de no usar _action_method (Se deja por
        // compatibilidad si es necesario,
        // pero preferimos el _action_method)
        new io.jettra.flux.core.ActionBinder(params)
                .on("nombre", p -> {
                    // Ignore, handled by saveForm if _action_method is present
                })
                .execute();

        Widget alert = null;
        if ("true".equals(params.get("success"))) {
            alert = Notification.of(Paragraph.of("¡Formulario enviado correctamente!"));
        } else if (params.get("error") != null) {
            alert = Notification.of(Paragraph.of("Error: " + params.get("error")))
                    .modifier(new Modifier().style(
                            "background-color: #fee2e2; color: #b91c1c; padding: 10px; border-radius: 6px; margin-bottom: 10px; width: 100%;"));
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
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #0d6efd; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;").attribute("onclick", "event.preventDefault(); window.location.href='" + JettraServer.resolvePath("/person?_action_method=notifyGlobal") + "'")),
                                ElevatedButton.of(Row.of(Icon.of("fas fa-envelope"), Text.of(" Send Personal").modifier(new io.jettra.flux.core.Modifier().style("margin-left:5px;"))))
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #17a2b8; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;").attribute("onclick", "event.preventDefault(); window.location.href='" + JettraServer.resolvePath("/person?_action_method=notifyPersonal") + "'")),
                                ElevatedButton.of(Row.of(Icon.of("fas fa-bullhorn"), Text.of(" Send Channel").modifier(new io.jettra.flux.core.Modifier().style("margin-left:5px;"))))
                                        .modifier(new io.jettra.flux.core.Modifier().style("margin-left:10px; background-color: #dc3545; color: white; padding: 10px 15px; border:none; border-radius:6px; cursor:pointer; font-weight:600;").attribute("onclick", "event.preventDefault(); window.location.href='" + JettraServer.resolvePath("/person?_action_method=notifyChannel") + "'"))
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
}
