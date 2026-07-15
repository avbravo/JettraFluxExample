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
public class MenuPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Menu - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        WidgetLet fileMenu = WidgetLet.of("File").icon("fas fa-file");
        fileMenu.add(WidgetLet.of("New").icon("fas fa-plus").add(WidgetLet.of("Bookmark").icon("fas fa-bookmark")).add(WidgetLet.of("Video").icon("fas fa-video")));
        fileMenu.add(WidgetLet.of("Delete").icon("fas fa-trash"));
        fileMenu.add(WidgetLet.of("Export").icon("fas fa-file-export"));

        WidgetLet editMenu = WidgetLet.of("Edit").icon("fas fa-edit");
        editMenu.add(WidgetLet.of("Left").icon("fas fa-align-left"));
        editMenu.add(WidgetLet.of("Right").icon("fas fa-align-right"));
        editMenu.add(WidgetLet.of("Center").icon("fas fa-align-center"));
        editMenu.add(WidgetLet.of("Justify").icon("fas fa-align-justify"));

        WidgetLet usersMenu = WidgetLet.of("Users").icon("fas fa-user");
        usersMenu.add(WidgetLet.of("New").icon("fas fa-user-plus"));
        usersMenu.add(WidgetLet.of("Delete").icon("fas fa-user-minus"));
        usersMenu.add(WidgetLet.of("Search").icon("fas fa-users").add(WidgetLet.of("Filter").icon("fas fa-filter")).add(WidgetLet.of("List").icon("fas fa-bars")));

        WidgetLet eventsMenu = WidgetLet.of("Events").icon("fas fa-calendar");
        eventsMenu.add(WidgetLet.of("Edit").icon("fas fa-edit"));
        eventsMenu.add(WidgetLet.of("Archieve").icon("fas fa-calendar-times"));

        WidgetLet optionsMenu = WidgetLet.of("Options").icon("fas fa-cog");
        optionsMenu.add(WidgetLet.of("Save").icon("fas fa-save"));
        optionsMenu.add(WidgetLet.of("Update").icon("fas fa-sync"));

        Widget menubarCard = Panel.of("Menubar", Menubar.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu));
        Widget breadcrumbCard = Panel.of("Breadcrumb", Breadcrumb.of("Computer", "Notebook", "Accessories", "Backpacks", "Item"));
        Widget stepsCard = Panel.of("Steps", Steps.of("Personal", "Seat", "Payment", "Confirmation").activeIndex(1));
        
        Widget tieredMenuCard = Panel.of("Tiered Menu", TieredMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu));
        Widget plainMenuCard = Panel.of("Plain Menu", PlainMenu.of(WidgetLet.of("New").icon("fas fa-plus"), WidgetLet.of("Update").icon("fas fa-sync"), WidgetLet.of("Delete").icon("fas fa-trash"), WidgetLet.of("Home").icon("fas fa-home").url("/dashboard")));
        Widget overlayMenuCard = Panel.of("Overlay Menu", OverlayMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu));
        
        Widget megaMenuCard = Panel.of("MegaMenu", MegaMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu));
        Widget contextMenu = ContextMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu);

        return Column.of(
            contextMenu,
            Header.of(2, "Menus").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px; color: var(--text-color);")),
            menubarCard,
            breadcrumbCard,
            stepsCard,
            Row.of(tieredMenuCard, plainMenuCard, overlayMenuCard).modifier(new Modifier().style("gap: 20px; align-items: flex-start; flex-wrap: wrap;")),
            megaMenuCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px; gap: 20px;"));
    }
}
