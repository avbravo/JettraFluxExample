package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.core.Modifier;
import io.jettra.flux.widgets.*;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Map;
import com.flux.example.pages.template.TemplatePage;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role = { "ADMIN", "MANAGER", "USER" })
public class MenuPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Menu - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget menubarCard = Card.of(Column.of(
            Header.of(4, "Menubar").modifier(new Modifier().style("margin-bottom: 20px;")),
            Row.of(
                Button.of("File").modifier(new Modifier().style("background: transparent; color: #1e293b;")),
                Button.of("Edit").modifier(new Modifier().style("background: transparent; color: #1e293b;")),
                Button.of("View").modifier(new Modifier().style("background: transparent; color: #1e293b;")),
                Button.of("Help").modifier(new Modifier().style("background: transparent; color: #1e293b;"))
            ).modifier(new Modifier().style("background: #f8fafc; border: 1px solid #e2e8f0; border-radius: 6px; padding: 10px;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        Widget contextMenuCard = Card.of(Column.of(
            Header.of(4, "Context Menu").modifier(new Modifier().style("margin-bottom: 20px;")),
            Card.of(Text.of("Right click me (Simulated)")).modifier(new Modifier().style("padding: 50px; text-align: center; border: 2px dashed #cbd5e1; border-radius: 8px; background: #f8fafc; cursor: context-menu;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        return Column.of(
            Header.of(2, "Menu Components").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            menubarCard,
            contextMenuCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
