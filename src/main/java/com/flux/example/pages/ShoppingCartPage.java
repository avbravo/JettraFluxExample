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
public class ShoppingCartPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Shopping Cart - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget cartItems = Column.of(
            buildCartItem("Bamboo Watch", "Accessories", "$65.00", "1"),
            Divider.of(),
            buildCartItem("Black Watch", "Accessories", "$72.00", "2"),
            Divider.of(),
            buildCartItem("Blue Band", "Fitness", "$79.00", "1")
        ).modifier(new Modifier().style("flex: 2; padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-right: 30px; min-width: 400px;"));

        Widget summary = Card.of(Column.of(
            Header.of(4, "Order Summary").modifier(new Modifier().style("margin-bottom: 20px; font-weight: 700;")),
            Row.of(Text.of("Subtotal"), Text.of("$288.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 15px;")),
            Row.of(Text.of("Shipping"), Text.of("$10.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 15px;")),
            Row.of(Text.of("Tax"), Text.of("$23.84").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 20px;")),
            Divider.of(),
            Row.of(Text.of("Total").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem;")), Text.of("$321.84").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem; color: #3b82f6;"))).modifier(new Modifier().style("justify-content: space-between; margin-top: 20px; margin-bottom: 30px;")),
            Button.of("Checkout").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 12px; border-radius: 6px; font-weight: 600; font-size: 1.1rem; width: 100%; text-align: center; display: block;"))
        )).modifier(new Modifier().style("flex: 1; padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: #f8fafc; border: 1px solid #e2e8f0; min-width: 300px;"));

        return Column.of(
            Header.of(2, "Shopping Cart").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            Row.of(cartItems, summary).modifier(new Modifier().style("width: 100%; flex-wrap: wrap; align-items: flex-start; gap: 20px;"))
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }

    private Widget buildCartItem(String name, String category, String price, String qty) {
        return Row.of(
            Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 100px; border-radius: 8px; margin-right: 20px;")),
            Column.of(
                Text.of(name).modifier(new Modifier().style("font-weight: 700; font-size: 1.1rem; color: #1e293b; margin-bottom: 5px;")),
                Text.of(category).modifier(new Modifier().style("color: #64748b; font-size: 0.9rem; margin-bottom: 10px;")),
                Row.of(
                    TextBox.of(qty).modifier(new Modifier().style("width: 50px; padding: 5px; text-align: center; border: 1px solid #cbd5e1; border-radius: 4px; margin-right: 20px;")),
                    Button.of("Remove").modifier(new Modifier().style("background: transparent; color: #ef4444; padding: 0; text-decoration: underline;"))
                ).modifier(new Modifier().style("align-items: center;"))
            ).modifier(new Modifier().style("flex: 1;")),
            Text.of(price).modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem; color: #1e293b;"))
        ).modifier(new Modifier().style("width: 100%; align-items: center; padding: 15px 0;"));
    }
}
