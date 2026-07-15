package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Datatable;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.flux.example.pages.template.TemplatePage;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role={"ADMIN", "MANAGER", "USER"})
public class TablePage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Table - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        // Define Custom CSS for the Datatable mimicking PrimeNG Atlantis Table
        String tableCss = "<style>\n"
            + ".espresso-datatable { width: 100%; border-collapse: separate; border-spacing: 0; margin-top: 10px; font-size: 14px; }\n"
            + ".espresso-datatable th { padding: 12px 16px; text-align: left; background-color: #f8fafc; color: #475569; font-weight: 600; border-bottom: 1px solid #e2e8f0; border-top: 1px solid #e2e8f0; }\n"
            + ".espresso-datatable td { padding: 16px; border-bottom: 1px solid #e2e8f0; color: #334155; }\n"
            + ".espresso-datatable tr:hover td { background-color: #f1f5f9; }\n"
            + ".badge { padding: 4px 8px; border-radius: 6px; font-weight: 700; font-size: 11px; text-transform: uppercase; letter-spacing: 0.3px; }\n"
            + ".badge.qualified { background-color: #dcfce7; color: #16a34a; }\n"
            + ".badge.new { background-color: #dbeafe; color: #2563eb; }\n"
            + ".badge.unqualified { background-color: #fee2e2; color: #dc2626; }\n"
            + ".badge.negotiation { background-color: #fef9c3; color: #ca8a04; }\n"
            + ".avatar-group { display: flex; align-items: center; gap: 8px; font-weight: 600; }\n"
            + ".avatar-img { width: 32px; height: 32px; border-radius: 50%; object-fit: cover; border: 2px solid white; box-shadow: 0 0 0 1px #e2e8f0; }\n"
            + ".progress-track { width: 100%; height: 8px; background-color: #e2e8f0; border-radius: 4px; overflow: hidden; }\n"
            + ".progress-bar { height: 100%; background-color: #3b82f6; border-radius: 4px; }\n"
            + ".activity-cell { display: flex; align-items: center; gap: 10px; }\n"
            + "</style>";

        // Table Data (Mocking Customers)
        List<String> headers = Arrays.asList("Name", "Country", "Agent", "Date", "Balance", "Status", "Activity");

        List<List<String>> rows = Arrays.asList(
            Arrays.asList(
                "<strong>James Butt</strong>", 
                "<img src='https://flagcdn.com/24x18/us.png' alt='USA' style='vertical-align:middle; margin-right:8px;'/> USA", 
                "<div class='avatar-group'><img src='https://randomuser.me/api/portraits/women/44.jpg' class='avatar-img'/> Ioni Bowcher</div>", 
                "09/13/2025", 
                "<strong>$70,663.00</strong>", 
                "<span class='badge qualified'>QUALIFIED</span>", 
                "<div class='activity-cell'><div class='progress-track'><div class='progress-bar' style='width: 50%;'></div></div><span>50%</span></div>"
            ),
            Arrays.asList(
                "<strong>Josephine Darakjy</strong>", 
                "<img src='https://flagcdn.com/24x18/es.png' alt='Spain' style='vertical-align:middle; margin-right:8px;'/> Spain", 
                "<div class='avatar-group'><img src='https://randomuser.me/api/portraits/men/32.jpg' class='avatar-img'/> Amy Elsner</div>", 
                "02/09/2025", 
                "<strong>$82,429.00</strong>", 
                "<span class='badge new'>NEW</span>", 
                "<div class='activity-cell'><div class='progress-track'><div class='progress-bar' style='width: 80%; background-color:#22c55e;'></div></div><span>80%</span></div>"
            ),
            Arrays.asList(
                "<strong>Art Venere</strong>", 
                "<img src='https://flagcdn.com/24x18/fr.png' alt='France' style='vertical-align:middle; margin-right:8px;'/> France", 
                "<div class='avatar-group'><img src='https://randomuser.me/api/portraits/men/62.jpg' class='avatar-img'/> Asiya Javayant</div>", 
                "05/13/2025", 
                "<strong>$10,934.00</strong>", 
                "<span class='badge unqualified'>UNQUALIFIED</span>", 
                "<div class='activity-cell'><div class='progress-track'><div class='progress-bar' style='width: 25%; background-color:#ef4444;'></div></div><span>25%</span></div>"
            ),
            Arrays.asList(
                "<strong>Lenna Paprocki</strong>", 
                "<img src='https://flagcdn.com/24x18/de.png' alt='Germany' style='vertical-align:middle; margin-right:8px;'/> Germany", 
                "<div class='avatar-group'><img src='https://randomuser.me/api/portraits/women/68.jpg' class='avatar-img'/> Bernardo Dominic</div>", 
                "09/15/2025", 
                "<strong>$500.00</strong>", 
                "<span class='badge negotiation'>NEGOTIATION</span>", 
                "<div class='activity-cell'><div class='progress-track'><div class='progress-bar' style='width: 59%; background-color:#eab308;'></div></div><span>59%</span></div>"
            )
        );

        Widget datatable = Datatable.of(headers, rows);
        
        Widget tableCard = Card.of(Column.of(
            Header.of(4, "Customers").modifier(new io.jettra.flux.core.Modifier().style("margin-top: 0; font-weight: 600; font-size: 1.25rem; color: #1e293b;")),
            datatable
        )).modifier(new io.jettra.flux.core.Modifier().style("width: 100%; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05);"));

        return Column.of(
            Paragraph.of(tableCss),
            Header.of(2, "Data Table").modifier(new io.jettra.flux.core.Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            tableCard
        ).modifier(new io.jettra.flux.core.Modifier().style("width: 100%; align-items: flex-start; max-width: 1200px; padding: 20px;"));
    }
}
