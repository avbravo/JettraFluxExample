package com.flux.example.pages;

import io.jettra.flux.widgets.ChartsLine;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Datatable;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.CharsPie;
import io.jettra.flux.widgets.Center;
import com.flux.example.pages.template.TemplatePage;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DashboardPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Dashboard - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget customCss = Paragraph.of("<style>\n"
            + ".atlantis-dashboard-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; width: 100%; }\n"
            + ".atlantis-main-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 1.5rem; width: 100%; margin-top: 1.5rem; }\n"
            + "@media (max-width: 992px) { .atlantis-dashboard-grid, .atlantis-main-grid { grid-template-columns: 1fr; } }\n"
            + ".stat-card { display: flex; flex-direction: column; gap: 10px; }\n"
            + ".stat-header { font-size: 0.85rem; color: #94a3b8; font-weight: 600; }\n"
            + ".stat-value { font-size: 1.8rem; font-weight: 700; color: var(--on-surface-color); }\n"
            + ".stat-badge { padding: 4px 8px; border-radius: 4px; font-size: 0.75rem; font-weight: bold; display: inline-flex; align-items: center; gap: 4px; }\n"
            + ".stat-badge.down { background-color: rgba(239, 68, 68, 0.15); color: #ef4444; }\n"
            + ".stat-badge.up { background-color: rgba(34, 197, 94, 0.15); color: #22c55e; }\n"
            + ".chart-card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }\n"
            + ".transaction-item { display: flex; align-items: center; justify-content: space-between; padding: 12px 0; border-bottom: 1px solid rgba(128,128,128,0.1); }\n"
            + ".transaction-item:last-child { border-bottom: none; }\n"
            + ".tx-icon { width: 36px; height: 36px; border-radius: 50%; display: flex; justify-content: center; align-items: center; color: white; font-size: 0.9rem; }\n"
            + ".tx-details { flex: 1; margin-left: 15px; }\n"
            + ".tx-title { font-size: 0.9rem; font-weight: 600; color: var(--on-surface-color); margin: 0 0 4px 0; }\n"
            + ".tx-date { font-size: 0.75rem; color: #94a3b8; margin: 0; }\n"
            + ".tx-amount { font-size: 0.9rem; font-weight: 700; }\n"
            + ".tx-amount.positive { color: #22c55e; }\n"
            + ".tx-amount.negative { color: #ef4444; }\n"
            + "</style>");

        // --- Stats Row ---
        Widget stat1 = Card.of(Paragraph.of(
            "<div class='stat-card'>" +
            "<div class='stat-header'>Conversion Rate</div>" +
            "<div style='display: flex; align-items: center; gap: 15px;'>" +
            "  <div class='stat-badge down'><i class='fas fa-arrow-down'></i> 0.8%</div>" +
            "  <div class='stat-value'>0.81%</div>" +
            "</div>" +
            "</div>"
        ));
        
        Widget stat2 = Card.of(Paragraph.of(
            "<div class='stat-card'>" +
            "<div class='stat-header'>Avg. Order Value</div>" +
            "<div style='display: flex; align-items: center; gap: 15px;'>" +
            "  <div class='stat-badge up'><i class='fas fa-arrow-up'></i> 4.2%</div>" +
            "  <div class='stat-value'>$306.2</div>" +
            "</div>" +
            "</div>"
        ));
        
        Widget stat3 = Card.of(Paragraph.of(
            "<div class='stat-card'>" +
            "<div class='stat-header'>Order Quantity</div>" +
            "<div style='display: flex; align-items: center; gap: 15px;'>" +
            "  <div class='stat-badge down'><i class='fas fa-arrow-down'></i> 2.1%</div>" +
            "  <div class='stat-value'>1,620</div>" +
            "</div>" +
            "</div>"
        ));

        
        // --- Main Row ---
        Widget mainChart = Card.of(Paragraph.of(
            "<div class='chart-card-header'>" +
            "  <div class='stat-header' style='color: var(--on-surface-color); font-size: 1rem;'>Unique Visitor Graph</div>" +
            "  <select style='background: transparent; border: 1px solid rgba(128,128,128,0.3); color: var(--on-surface-color); padding: 5px 10px; border-radius: 4px;'><option>2025</option></select>" +
            "</div>" +
            "<div style='display: flex; gap: 40px; margin-bottom: 30px;'>" +
            "  <div>" +
            "    <div class='stat-value'>$620,076</div>" +
            "    <div class='stat-header' style='margin-top: 5px; font-size: 0.75rem;'>MRR GROWTH</div>" +
            "  </div>" +
            "  <div>" +
            "    <div class='stat-value'>$1,120</div>" +
            "    <div class='stat-header' style='margin-top: 5px; font-size: 0.75rem;'>AVG. MRR/CUSTOMER</div>" +
            "  </div>" +
            "</div>" +
            "<div style='height: 250px; display: flex; align-items: flex-end; justify-content: space-between; gap: 10px; padding-top: 20px; border-bottom: 1px solid rgba(128,128,128,0.2);'>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 40%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 60%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 50%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 80%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 30%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 75%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 65%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 95%; border-radius: 4px 4px 0 0;'></div>" +
            "  <div style='width: 100%; background: var(--primary-color); height: 55%; border-radius: 4px 4px 0 0;'></div>" +
            "</div>"
        ));

        Widget transactions = Card.of(Paragraph.of(
            "<div class='chart-card-header'>" +
            "  <div class='stat-header' style='color: var(--on-surface-color); font-size: 1rem;'>Transaction History</div>" +
            "  <div><i class='fas fa-sync' style='color: #94a3b8; cursor: pointer; margin-right: 10px;'></i><i class='fas fa-filter' style='color: #94a3b8; cursor: pointer;'></i></div>" +
            "</div>" +
            "<div class='transaction-item'>" +
            "  <div class='tx-icon' style='background: #3b82f6;'><i class='fas fa-check'></i></div>" +
            "  <div class='tx-details'>" +
            "    <p class='tx-title'>Payment from #28492</p>" +
            "    <p class='tx-date'>June 13, 2025 11:09 AM</p>" +
            "  </div>" +
            "  <div class='tx-amount positive'>+$250.00</div>" +
            "</div>" +
            "<div class='transaction-item'>" +
            "  <div class='tx-icon' style='background: #ef4444;'><i class='fas fa-redo'></i></div>" +
            "  <div class='tx-details'>" +
            "    <p class='tx-title'>Process refund to #94830</p>" +
            "    <p class='tx-date'>June 13, 2025 08:22 AM</p>" +
            "  </div>" +
            "  <div class='tx-amount negative'>-$570.00</div>" +
            "</div>" +
            "<div class='transaction-item'>" +
            "  <div class='tx-icon' style='background: #22c55e;'><i class='fas fa-plus'></i></div>" +
            "  <div class='tx-details'>" +
            "    <p class='tx-title'>New 8 user to #5849</p>" +
            "    <p class='tx-date'>June 12, 2025 02:56 PM</p>" +
            "  </div>" +
            "  <div class='tx-amount positive'>+$50.00</div>" +
            "</div>" +
            "<div class='transaction-item'>" +
            "  <div class='tx-icon' style='background: #3b82f6;'><i class='fas fa-check'></i></div>" +
            "  <div class='tx-details'>" +
            "    <p class='tx-title'>Payment from #3382</p>" +
            "    <p class='tx-date'>June 11, 2025 06:11 AM</p>" +
            "  </div>" +
            "  <div class='tx-amount positive'>+$3830.00</div>" +
            "</div>"
        ));

        // Assemble structure manually using raw HTML strings since JettraFlux doesn't have a Grid widget
        Widget layout = Paragraph.of(
            "<div class='atlantis-dashboard-grid'>" +
               stat1.render(getThemeByName(currentTheme)) +
               stat2.render(getThemeByName(currentTheme)) +
               stat3.render(getThemeByName(currentTheme)) +
            "</div>" +
            "<div class='atlantis-main-grid'>" +
               mainChart.render(getThemeByName(currentTheme)) +
               transactions.render(getThemeByName(currentTheme)) +
            "</div>"
        );

        // --- Center Content ---
        return Column.of(
            customCss,
            layout
        );
    }
}
