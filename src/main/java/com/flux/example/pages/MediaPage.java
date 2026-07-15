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
public class MediaPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Media - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget carouselCard = Card.of(Column.of(
            Header.of(4, "Carousel").modifier(new Modifier().style("margin-bottom: 20px;")),
            Row.of(
                Button.of("<").modifier(new Modifier().style("border-radius: 50%; padding: 10px; margin-right: 20px;")),
                Image.of("https://primefaces.org/cdn/primeng/images/galleria/galleria1.jpg").modifier(new Modifier().style("width: 400px; border-radius: 12px;")),
                Button.of(">").modifier(new Modifier().style("border-radius: 50%; padding: 10px; margin-left: 20px;"))
            ).modifier(new Modifier().style("align-items: center; justify-content: center; width: 100%;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-bottom: 30px; width: 100%;"));

        Widget imageCard = Card.of(Column.of(
            Header.of(4, "Image").modifier(new Modifier().style("margin-bottom: 20px;")),
            Image.of("https://primefaces.org/cdn/primeng/images/galleria/galleria2.jpg").modifier(new Modifier().style("width: 300px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); cursor: pointer; transition: transform 0.2s;"))
        )).modifier(new Modifier().style("padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; width: 100%;"));

        return Column.of(
            Header.of(2, "Media Components").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            carouselCard,
            imageCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
