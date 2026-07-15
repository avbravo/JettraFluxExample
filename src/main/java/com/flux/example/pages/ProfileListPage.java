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
public class ProfileListPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Profile List - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget grid = Row.of(
            buildProfileCard("Amy Elsner", "Software Engineer", "San Francisco, USA", "https://primefaces.org/cdn/primeng/images/avatar/amyelsner.png"),
            buildProfileCard("Anna Fali", "Product Manager", "London, UK", "https://primefaces.org/cdn/primeng/images/avatar/annafali.png"),
            buildProfileCard("Asiya Javayant", "UX Designer", "Berlin, Germany", "https://primefaces.org/cdn/primeng/images/avatar/asiyajavayant.png"),
            buildProfileCard("Bernardo Dominic", "Data Scientist", "New York, USA", "https://primefaces.org/cdn/primeng/images/avatar/bernardodominic.png"),
            buildProfileCard("Elwin Sharvill", "Marketing", "Paris, France", "https://primefaces.org/cdn/primeng/images/avatar/elwinsharvill.png"),
            buildProfileCard("Ioni Bowcher", "CEO", "Sydney, Australia", "https://primefaces.org/cdn/primeng/images/avatar/ionibowcher.png")
        ).modifier(new Modifier().style("display: flex; flex-wrap: wrap; gap: 20px; width: 100%; align-content: flex-start;"));

        return Column.of(
            Row.of(
                Header.of(2, "Team Profiles").modifier(new Modifier().style("margin-top: 0; font-weight: 700;")),
                Button.of("Add New Profile").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 10px 20px; border-radius: 6px; font-weight: 600;"))
            ).modifier(new Modifier().style("justify-content: space-between; align-items: center; width: 100%; margin-bottom: 30px;")),
            grid
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }

    private Widget buildProfileCard(String name, String role, String location, String avatarUrl) {
        return Card.of(Column.of(
            Row.of(
                Avatar.of(avatarUrl).shape("circle").modifier(new Modifier().style("width: 64px; height: 64px; margin-right: 20px;")),
                Column.of(
                    Text.of(name).modifier(new Modifier().style("font-weight: 700; color: #1e293b; font-size: 1.1rem; margin-bottom: 5px;")),
                    Text.of(role).modifier(new Modifier().style("color: #64748b; font-size: 0.9rem; margin-bottom: 5px;")),
                    Row.of(
                        Icon.of(Icon.Heroicons.LOCATION_MARKER).modifier(new Modifier().style("width: 14px; height: 14px; color: #94a3b8; margin-right: 5px;")),
                        Text.of(location).modifier(new Modifier().style("color: #94a3b8; font-size: 0.85rem;"))
                    ).modifier(new Modifier().style("align-items: center;"))
                ).modifier(new Modifier().style("flex: 1; justify-content: center;"))
            ).modifier(new Modifier().style("align-items: center; width: 100%; margin-bottom: 20px;")),
            Row.of(
                Button.of("Message").modifier(new Modifier().style("background: white; color: #3b82f6; border: 1px solid #3b82f6; padding: 8px 16px; border-radius: 6px; font-weight: 600; flex: 1; margin-right: 10px;")),
                Button.of("Profile").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 8px 16px; border-radius: 6px; font-weight: 600; flex: 1;"))
            ).modifier(new Modifier().style("width: 100%;"))
        )).modifier(new Modifier().style("width: calc(33.333% - 14px); min-width: 300px; padding: 25px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; border: 1px solid #f1f5f9;"));
    }
}
