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
public class ProductOverviewPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Product Overview - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget imageSection = Column.of(
            Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 100%; max-width: 400px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); margin-bottom: 20px;")),
            Row.of(
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 80px; height: 80px; border-radius: 8px; cursor: pointer; border: 2px solid #3b82f6;")),
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 80px; height: 80px; border-radius: 8px; cursor: pointer; margin-left: 10px; opacity: 0.6;")),
                Image.of("https://primefaces.org/cdn/primeng/images/demo/product/bamboo-watch.jpg").modifier(new Modifier().style("width: 80px; height: 80px; border-radius: 8px; cursor: pointer; margin-left: 10px; opacity: 0.6;"))
            ).modifier(new Modifier().style("display: flex; gap: 10px;"))
        ).modifier(new Modifier().style("flex: 1; min-width: 300px; align-items: center;"));

        Widget detailsSection = Column.of(
            Header.of(2, "Bamboo Watch").modifier(new Modifier().style("margin-top: 0; font-weight: 700; color: #1e293b; margin-bottom: 10px;")),
            Row.of(
                Badge.of("INSTOCK").modifier(new Modifier().style("background: #22c55e20; color: #22c55e; border-radius: 12px; padding: 4px 8px; font-weight: 600; margin-right: 10px;")),
                Icon.of(Icon.Heroicons.STAR).modifier(new Modifier().style("color: #eab308; width: 20px;")),
                Icon.of(Icon.Heroicons.STAR).modifier(new Modifier().style("color: #eab308; width: 20px;")),
                Icon.of(Icon.Heroicons.STAR).modifier(new Modifier().style("color: #eab308; width: 20px;")),
                Icon.of(Icon.Heroicons.STAR).modifier(new Modifier().style("color: #eab308; width: 20px;")),
                Icon.of(Icon.Heroicons.STAR).modifier(new Modifier().style("color: #cbd5e1; width: 20px;")),
                Text.of("4 Reviews").modifier(new Modifier().style("color: #64748b; margin-left: 10px; font-size: 0.9rem;"))
            ).modifier(new Modifier().style("align-items: center; margin-bottom: 20px;")),
            Header.of(3, "$65.00").modifier(new Modifier().style("margin-top: 0; font-weight: 700; color: #3b82f6; margin-bottom: 20px;")),
            Text.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.").modifier(new Modifier().style("color: #475569; margin-bottom: 30px; line-height: 1.6;")),
            
            Header.of(5, "Color").modifier(new Modifier().style("margin-bottom: 10px;")),
            Row.of(
                Badge.of("").modifier(new Modifier().style("width: 30px; height: 30px; border-radius: 50%; background: #1e293b; cursor: pointer; border: 2px solid white; box-shadow: 0 0 0 2px #3b82f6; margin-right: 10px;")),
                Badge.of("").modifier(new Modifier().style("width: 30px; height: 30px; border-radius: 50%; background: #eab308; cursor: pointer; margin-right: 10px;")),
                Badge.of("").modifier(new Modifier().style("width: 30px; height: 30px; border-radius: 50%; background: #ef4444; cursor: pointer;"))
            ).modifier(new Modifier().style("margin-bottom: 30px;")),

            Row.of(
                Column.of(
                    Label.of("Quantity").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    TextBox.of("1").modifier(new Modifier().style("width: 60px; padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; text-align: center;"))
                ).modifier(new Modifier().style("margin-right: 20px;")),
                Button.of("Add to Cart").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 10px 30px; border-radius: 6px; font-weight: 600; font-size: 1.1rem; flex: 1; margin-top: 25px;"))
            ).modifier(new Modifier().style("align-items: flex-end; width: 100%; max-width: 400px;"))
        ).modifier(new Modifier().style("flex: 2; padding: 0 30px; min-width: 300px;"));

        Widget mainCard = Card.of(Row.of(imageSection, detailsSection).modifier(new Modifier().style("flex-wrap: wrap; gap: 30px;")))
            .modifier(new Modifier().style("width: 100%; padding: 40px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white;"));

        return Column.of(
            Header.of(2, "Product Overview").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            mainCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
