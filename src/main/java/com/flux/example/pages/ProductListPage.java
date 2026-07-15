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
public class ProductListPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Product List - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget filters = Card.of(Column.of(
            Header.of(5, "Categories").modifier(new Modifier().style("margin-bottom: 15px; color: var(--text-color);")),
            Row.of(Checkbox.of("acc"), Text.of("Accessories").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 10px;")),
            Row.of(Checkbox.of("fit"), Text.of("Fitness").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 10px;")),
            Row.of(Checkbox.of("clo"), Text.of("Clothing").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 20px;")),
            
            Header.of(5, "Price Range").modifier(new Modifier().style("margin-bottom: 15px; color: var(--text-color);")),
            Row.of(RadioButton.of("p1", "price"), Text.of("$0 - $50").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 10px;")),
            Row.of(RadioButton.of("p2", "price"), Text.of("$50 - $100").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 10px;")),
            Row.of(RadioButton.of("p3", "price"), Text.of("$100+").modifier(new Modifier().style("margin-left: 10px; color: var(--text-color);"))).modifier(new Modifier().style("margin-bottom: 20px;"))
        )).modifier(new Modifier().style("width: 250px; padding: 20px; border-radius: 12px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); background: var(--surface-color); border: 1px solid var(--surface-alt); margin-right: 30px;"));

        Widget grid = Row.of(
            buildProduct("Bamboo Watch", "$65.00", "Accessories", "INSTOCK"),
            buildProduct("Black Watch", "$72.00", "Accessories", "INSTOCK"),
            buildProduct("Blue Band", "$79.00", "Fitness", "LOWSTOCK"),
            buildProduct("Blue T-Shirt", "$29.00", "Clothing", "INSTOCK"),
            buildProduct("Bracelet", "$15.00", "Accessories", "INSTOCK"),
            buildProduct("Brown Purse", "$120.00", "Accessories", "OUTOFSTOCK")
        ).modifier(new Modifier().style("display: flex; flex-wrap: wrap; gap: 20px; flex: 1; align-content: flex-start;"));

        Widget mainRow = Row.of(filters, grid).modifier(new Modifier().style("align-items: flex-start; width: 100%;"));

        return Column.of(
            Row.of(
                Header.of(2, "Product List").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px; color: var(--text-color);")),
                TextBox.of("Search products...").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid var(--surface-alt); background: var(--surface-color); color: var(--text-color); width: 300px;"))
            ).modifier(new Modifier().style("justify-content: space-between; align-items: center; width: 100%; margin-bottom: 20px;")),
            mainRow
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }

    private Widget buildProduct(String name, String price, String category, String status) {
        String badgeColor = status.equals("INSTOCK") ? "#22c55e" : status.equals("LOWSTOCK") ? "#eab308" : "#ef4444";
        return Card.of(Column.of(
            Badge.of(status).modifier(new Modifier().style("background: " + badgeColor + "20; color: " + badgeColor + "; border-radius: 12px; padding: 4px 8px; font-weight: 600; font-size: 0.75rem; align-self: flex-end; margin-bottom: 10px;")),
            Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 150px; align-self: center; margin-bottom: 15px; border-radius: 8px;")),
            Text.of(category).modifier(new Modifier().style("color: var(--text-color-secondary); font-size: 0.85rem; margin-bottom: 5px;")),
            Text.of(name).modifier(new Modifier().style("font-weight: 700; color: var(--text-color); font-size: 1.1rem; margin-bottom: 15px;")),
            Row.of(
                Text.of(price).modifier(new Modifier().style("font-weight: 700; color: var(--primary-color); font-size: 1.25rem;")),
                Button.of("+").modifier(new Modifier().style("background: var(--surface-alt); color: var(--text-color); border-radius: 50%; width: 36px; height: 36px; display: flex; align-items: center; justify-content: center; font-weight: bold; border:none; cursor:pointer;"))
            ).modifier(new Modifier().style("justify-content: space-between; align-items: center; width: 100%;"))
        )).modifier(new Modifier().style("width: calc(33.333% - 14px); min-width: 200px; padding: 20px; border-radius: 12px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); background: var(--surface-color); border: 1px solid var(--surface-alt); cursor: pointer; transition: transform 0.2s, box-shadow 0.2s;"));
    }
}
