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
public class OverlayPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Overlay - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget dialogCard = Card.of(Column.of(
            Header.of(4, "Dialog").modifier(new Modifier().style("margin-bottom: 20px;")),
            Button.of("Show Dialog").modifier(new Modifier().style("background: #3b82f6; color: white; border-radius: 6px; padding: 10px 20px;"))
            // Dialog widget would normally be hidden and shown via state or JS
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        Widget overlayPanelCard = Card.of(Column.of(
            Header.of(4, "Overlay Panel").modifier(new Modifier().style("margin-bottom: 20px;")),
            Row.of(
                Button.of("Toggle").modifier(new Modifier().style("background: #10b981; color: white; border-radius: 6px; padding: 10px 20px; margin-right: 20px;")),
                Card.of(Text.of("This is an overlay panel")).modifier(new Modifier().style("padding: 15px; border: 1px solid #e2e8f0; border-radius: 6px; box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1); display: none;")) // Simulated hidden
            ).modifier(new Modifier().style("align-items: center;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        return Column.of(
            Header.of(2, "Overlay Components").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            dialogCard,
            overlayPanelCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
