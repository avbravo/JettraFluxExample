package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.widgets.Label;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Map;
import com.flux.example.pages.template.TemplatePage;

import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.core.security.widget.ActionWidgetAllow;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.server.JettraServer;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role = { "ADMIN", "MANAGER", "USER" })
public class ButtonDemoPage extends TemplatePage {

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        Widget content = Label.of("Demostración de Botón")
                .modifier(new io.jettra.flux.core.Modifier().cssClass("demo-title"));

        // Form to trigger _action_method
        String formHtml = "<form method=\"POST\" action=\"" + JettraServer.resolvePath("/button-demo") + "\">" +
                "<input type=\"hidden\" name=\"_action_method\" value=\"secureAction\">" +
                "<button type=\"submit\" style=\"padding: 10px; background-color: #3b82f6; color: white; border: none; border-radius: 4px; cursor: pointer;\">Secure Action (Admin/Manager)</button>"
                +
                "</form>";

        Widget formWidget = Paragraph.of(formHtml);

        return Column.of(content, formWidget);
    }

    @ActionWidgetAllow(role = { "ADMIN", "MANAGER" }, department = "")
    public void secureAction(HttpExchange exchange, Map<String, String> params) {
        try {
            String html = "<script>alert('Action executed successfully!'); window.location.href='"
                    + JettraServer.resolvePath("/button-demo") + "';</script>";
            renderResponse(exchange, html, 200);
        } catch (Exception e) {
        }
    }
}
