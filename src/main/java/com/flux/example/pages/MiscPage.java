package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Badge;
import io.jettra.flux.widgets.Chip;
import io.jettra.flux.widgets.Skeleton;
import io.jettra.core.security.widget.PageWidgetAllow;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Map;
import com.flux.example.pages.template.TemplatePage;

@JettraPageSincronized(SyncType.ALL)
@PageWidgetAllow(role = { "ADMIN", "MANAGER", "USER" })
public class MiscPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Misc - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget badges = Card.of(Column.of(
            Header.of(4, "Badges").modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 1rem;")),
            Row.of(
                Badge.of("2").severity("primary"),
                Badge.of("8").severity("success"),
                Badge.of("4").severity("info"),
                Badge.of("12").severity("warning"),
                Badge.of("3").severity("danger")
            ).modifier(new io.jettra.flux.core.Modifier().style("gap: 1rem;"))
        )).modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 2rem;"));

        Widget chips = Card.of(Column.of(
            Header.of(4, "Chips").modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 1rem;")),
            Row.of(
                Chip.of("Action"),
                Chip.of("Comedy"),
                Chip.of("Mystery"),
                Chip.of("Thriller").icon("fas fa-film"),
                Chip.of("Amy Elsner").image("https://primefaces.org/cdn/primeng/images/avatar/amyelsner.png")
            ).modifier(new io.jettra.flux.core.Modifier().style("gap: 1rem; flex-wrap: wrap;"))
        )).modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 2rem;"));

        Widget skeletonComponent = Card.of(Column.of(
            Header.of(4, "Skeleton").modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 1rem;")),
            Row.of(
                Skeleton.circle("4rem"),
                Column.of(
                    Skeleton.of("10rem", "1rem").modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 0.5rem;")),
                    Skeleton.of("8rem", "1rem")
                ).modifier(new io.jettra.flux.core.Modifier().style("margin-left: 1rem;"))
            ).modifier(new io.jettra.flux.core.Modifier().style("align-items: center; margin-bottom: 1rem;")),
            Skeleton.of("100%", "150px")
        )).modifier(new io.jettra.flux.core.Modifier().style("margin-bottom: 2rem;"));

        return Column.of(
            Paragraph.of(io.jettra.flux.theme.DataTableCSS.get() + io.jettra.flux.theme.MiscCSS.get()), // Badge uses DataTableCSS right now
            Header.of(2, "Misc Components").modifier(new io.jettra.flux.core.Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            badges,
            chips,
            skeletonComponent
        ).modifier(new io.jettra.flux.core.Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
