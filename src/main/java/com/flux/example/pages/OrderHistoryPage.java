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
public class OrderHistoryPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Order History - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget order1 = Card.of(Column.of(
            Row.of(
                Column.of(Text.of("Order Number").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("#4512").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Date").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("May 12, 2023").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Total Amount").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("$125.00").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Status").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Badge.of("DELIVERED").modifier(new Modifier().style("background: #22c55e20; color: #22c55e; border-radius: 12px; padding: 4px 8px; font-weight: 600; font-size: 0.75rem;")))
            ).modifier(new Modifier().style("margin-bottom: 20px; align-items: center; border-bottom: 1px solid #e2e8f0; padding-bottom: 20px;")),
            Row.of(
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 80px; border-radius: 8px; margin-right: 20px;")),
                Column.of(
                    Text.of("Bamboo Watch").modifier(new Modifier().style("font-weight: 700; color: #1e293b; margin-bottom: 5px;")),
                    Text.of("Quantity: 1").modifier(new Modifier().style("color: #64748b; font-size: 0.9rem;"))
                ).modifier(new Modifier().style("flex: 1; justify-content: center;")),
                Button.of("View Item").modifier(new Modifier().style("background: white; color: #1e293b; border: 1px solid #cbd5e1; padding: 8px 16px; border-radius: 6px; font-weight: 600; margin-right: 10px;")),
                Button.of("Buy Again").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 8px 16px; border-radius: 6px; font-weight: 600;"))
            ).modifier(new Modifier().style("align-items: center;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        Widget order2 = Card.of(Column.of(
            Row.of(
                Column.of(Text.of("Order Number").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("#4511").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Date").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("April 25, 2023").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Total Amount").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Text.of("$200.00").modifier(new Modifier().style("font-weight: 700; color: #1e293b;"))).modifier(new Modifier().style("margin-right: 40px;")),
                Column.of(Text.of("Status").modifier(new Modifier().style("color: #64748b; font-size: 0.85rem; margin-bottom: 5px;")), Badge.of("CANCELLED").modifier(new Modifier().style("background: #ef444420; color: #ef4444; border-radius: 12px; padding: 4px 8px; font-weight: 600; font-size: 0.75rem;")))
            ).modifier(new Modifier().style("margin-bottom: 20px; align-items: center; border-bottom: 1px solid #e2e8f0; padding-bottom: 20px;")),
            Row.of(
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 80px; border-radius: 8px; margin-right: 20px;")),
                Column.of(
                    Text.of("Black Watch").modifier(new Modifier().style("font-weight: 700; color: #1e293b; margin-bottom: 5px;")),
                    Text.of("Quantity: 2").modifier(new Modifier().style("color: #64748b; font-size: 0.9rem;"))
                ).modifier(new Modifier().style("flex: 1; justify-content: center;")),
                Button.of("View Item").modifier(new Modifier().style("background: white; color: #1e293b; border: 1px solid #cbd5e1; padding: 8px 16px; border-radius: 6px; font-weight: 600; margin-right: 10px;")),
                Button.of("Buy Again").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 8px 16px; border-radius: 6px; font-weight: 600;"))
            ).modifier(new Modifier().style("align-items: center;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        return Column.of(
            Header.of(2, "Order History").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            order1,
            order2
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1000px; padding: 20px;"));
    }
}
