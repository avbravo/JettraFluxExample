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
public class ChartsPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Charts - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget lineChart = Card.of(Column.of(
            Header.of(4, "Line Chart").modifier(new Modifier().style("margin-bottom: 20px;")),
            ChartsLine.of("lineChart") // JettraFlux charts line
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; flex: 1; min-width: 300px;"));

        Widget barChart = Card.of(Column.of(
            Header.of(4, "Bar Chart").modifier(new Modifier().style("margin-bottom: 20px;")),
            CharsBar.of("barChart") // JettraFlux chars bar
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; flex: 1; min-width: 300px;"));
        
        Widget pieChart = Card.of(Column.of(
            Header.of(4, "Pie Chart").modifier(new Modifier().style("margin-bottom: 20px;")),
            CharsPie.of("pieChart") // JettraFlux chars pie
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; flex: 1; min-width: 300px;"));

        Widget doughnutChart = Card.of(Column.of(
            Header.of(4, "Doughnut Chart").modifier(new Modifier().style("margin-bottom: 20px;")),
            CharsDoughnut.of("doughnutChart") // JettraFlux chars doughnut
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; flex: 1; min-width: 300px;"));

        Widget row1 = Row.of(lineChart, barChart).modifier(new Modifier().style("gap: 20px; margin-bottom: 20px; flex-wrap: wrap;"));
        Widget row2 = Row.of(pieChart, doughnutChart).modifier(new Modifier().style("gap: 20px; flex-wrap: wrap;"));

        return Column.of(
            Header.of(2, "Charts Component").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            row1,
            row2
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
