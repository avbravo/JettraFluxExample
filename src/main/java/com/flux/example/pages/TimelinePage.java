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
public class TimelinePage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Timeline - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget timelineCard = Card.of(Column.of(
            Header.of(4, "Basic Timeline").modifier(new Modifier().style("margin-bottom: 20px;")),
            // Assuming TimeLine widget expects a string or some parameters in a real implementation
            // For now, we simulate the visual structure using rows and columns if TimeLine isn't fully configured yet,
            // but since there's a TimeLine.java we will use it
            TimeLine.of() // Might need specific configurations if TimeLine allows
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        return Column.of(
            Header.of(2, "Timeline").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            timelineCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
