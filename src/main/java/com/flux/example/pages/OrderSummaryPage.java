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
public class OrderSummaryPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Order Summary - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget header = Column.of(
            Icon.of(Icon.Heroicons.CHECK_CIRCLE).modifier(new Modifier().style("width: 64px; height: 64px; color: #22c55e; margin-bottom: 20px;")),
            Header.of(2, "Thank you for your order!").modifier(new Modifier().style("margin-top: 0; font-weight: 700; color: #1e293b; margin-bottom: 10px;")),
            Text.of("We've sent a confirmation email to john.doe@example.com").modifier(new Modifier().style("color: #64748b; margin-bottom: 30px; font-size: 1.1rem;"))
        ).modifier(new Modifier().style("align-items: center; text-align: center; width: 100%; border-bottom: 1px solid #e2e8f0; padding-bottom: 30px; margin-bottom: 30px;"));

        Widget orderDetails = Row.of(
            Column.of(
                Header.of(5, "Order Details").modifier(new Modifier().style("margin-bottom: 15px; font-weight: 600;")),
                Text.of("Order Number: #4512").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;")),
                Text.of("Date: May 12, 2023").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;")),
                Text.of("Payment Method: Visa ending in 1234").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;"))
            ).modifier(new Modifier().style("flex: 1;")),
            Column.of(
                Header.of(5, "Shipping Address").modifier(new Modifier().style("margin-bottom: 15px; font-weight: 600;")),
                Text.of("John Doe").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;")),
                Text.of("123 Main Street").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;")),
                Text.of("New York, NY 10001").modifier(new Modifier().style("color: #475569; margin-bottom: 5px;"))
            ).modifier(new Modifier().style("flex: 1;"))
        ).modifier(new Modifier().style("width: 100%; margin-bottom: 30px; border-bottom: 1px solid #e2e8f0; padding-bottom: 30px;"));

        Widget itemsList = Column.of(
            Row.of(
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 60px; border-radius: 8px; margin-right: 20px;")),
                Column.of(
                    Text.of("Bamboo Watch").modifier(new Modifier().style("font-weight: 700; color: #1e293b; margin-bottom: 5px;")),
                    Text.of("Quantity: 1").modifier(new Modifier().style("color: #64748b; font-size: 0.9rem;"))
                ).modifier(new Modifier().style("flex: 1;")),
                Text.of("$65.00").modifier(new Modifier().style("font-weight: 600; color: #1e293b;"))
            ).modifier(new Modifier().style("align-items: center; width: 100%; margin-bottom: 15px;")),
            Row.of(
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 60px; border-radius: 8px; margin-right: 20px;")),
                Column.of(
                    Text.of("Black Watch").modifier(new Modifier().style("font-weight: 700; color: #1e293b; margin-bottom: 5px;")),
                    Text.of("Quantity: 2").modifier(new Modifier().style("color: #64748b; font-size: 0.9rem;"))
                ).modifier(new Modifier().style("flex: 1;")),
                Text.of("$144.00").modifier(new Modifier().style("font-weight: 600; color: #1e293b;"))
            ).modifier(new Modifier().style("align-items: center; width: 100%; margin-bottom: 20px;"))
        ).modifier(new Modifier().style("width: 100%; border-bottom: 1px solid #e2e8f0; padding-bottom: 20px; margin-bottom: 20px;"));

        Widget summaryTotals = Column.of(
            Row.of(Text.of("Subtotal").modifier(new Modifier().style("color: #475569;")), Text.of("$209.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 10px;")),
            Row.of(Text.of("Shipping").modifier(new Modifier().style("color: #475569;")), Text.of("$10.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 10px;")),
            Row.of(Text.of("Tax").modifier(new Modifier().style("color: #475569;")), Text.of("$15.33").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 20px;")),
            Row.of(Text.of("Total").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem;")), Text.of("$234.33").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem; color: #3b82f6;"))).modifier(new Modifier().style("justify-content: space-between;"))
        ).modifier(new Modifier().style("width: 100%; max-width: 400px; align-self: flex-end;"));

        Widget card = Card.of(Column.of(header, orderDetails, itemsList, summaryTotals))
            .modifier(new Modifier().style("padding: 50px; border-radius: 12px; box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1); background: white; width: 100%;"));

        return Column.of(
            card,
            Button.of("Continue Shopping").modifier(new Modifier().style("background: white; color: #3b82f6; border: 1px solid #3b82f6; padding: 12px 24px; border-radius: 6px; font-weight: 600; margin-top: 30px; align-self: center;"))
        ).modifier(new Modifier().style("width: 100%; align-items: center; max-width: 800px; padding: 20px; margin: 0 auto;"));
    }
}
