package com.flux.example.pages;

import io.jettra.flux.widgets.Icon;

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
        
        WidgetLet fileMenu = WidgetLet.of("File").icon(Icon.FILE);
        fileMenu.add(WidgetLet.of("New").icon(Icon.PLUS).add(WidgetLet.of("Bookmark").icon(Icon.BOOKMARK)).add(WidgetLet.of("Video").icon(Icon.VIDEO)));
        fileMenu.add(WidgetLet.of("Delete").icon(Icon.TRASH));
        fileMenu.add(WidgetLet.of("Export").icon(Icon.FILE_EXPORT));

        WidgetLet editMenu = WidgetLet.of("Edit").icon(Icon.EDIT);
        editMenu.add(WidgetLet.of("Left").icon(Icon.ALIGN_LEFT));
        editMenu.add(WidgetLet.of("Right").icon(Icon.ALIGN_RIGHT));
        editMenu.add(WidgetLet.of("Center").icon(Icon.ALIGN_CENTER));
        editMenu.add(WidgetLet.of("Justify").icon(Icon.ALIGN_JUSTIFY));

        WidgetLet usersMenu = WidgetLet.of("Users").icon(Icon.USER);
        usersMenu.add(WidgetLet.of("New").icon(Icon.USER_PLUS));
        usersMenu.add(WidgetLet.of("Delete").icon(Icon.USER_MINUS));
        usersMenu.add(WidgetLet.of("Search").icon(Icon.USERS).add(WidgetLet.of("Filter").icon(Icon.FILTER)).add(WidgetLet.of("List").icon(Icon.BARS)));

        WidgetLet eventsMenu = WidgetLet.of("Events").icon(Icon.CALENDAR);
        eventsMenu.add(WidgetLet.of("Edit").icon(Icon.EDIT));
        eventsMenu.add(WidgetLet.of("Archieve").icon(Icon.CALENDAR_TIMES));

        WidgetLet optionsMenu = WidgetLet.of("Options").icon(Icon.COG);
        optionsMenu.add(WidgetLet.of("Save").icon(Icon.SAVE));
        optionsMenu.add(WidgetLet.of("Update").icon(Icon.SYNC));

        Widget menubarCard = Card.of(Column.of(
            Header.of(5, "Menubar").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            Menubar.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu)
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); width: 100%;"));

        Widget breadcrumbCard = Card.of(Column.of(
            Header.of(5, "Breadcrumb").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            Breadcrumb.of("Computer", "Notebook", "Accessories", "Backpacks", "Item")
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); width: 100%;"));

        Widget stepsCard = Card.of(Column.of(
            Header.of(5, "Steps").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            Steps.of("Personal", "Seat", "Payment", "Confirmation").activeIndex(1)
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); width: 100%;"));
        
        Widget tieredMenuCard = Card.of(Column.of(
            Header.of(5, "Tiered Menu").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            TieredMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu)
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); flex: 1; min-width: 300px;"));
        
        Widget plainMenuCard = Card.of(Column.of(
            Header.of(5, "Plain Menu").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            PlainMenu.of(WidgetLet.of("New").icon(Icon.PLUS), WidgetLet.of("Update").icon(Icon.SYNC), WidgetLet.of("Delete").icon(Icon.TRASH), WidgetLet.of("Home").icon(Icon.HOME).url("/dashboard"))
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); flex: 1; min-width: 300px;"));
        
        Widget overlayMenuCard = Card.of(Column.of(
            Header.of(5, "Overlay Menu").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            OverlayMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu)
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); flex: 1; min-width: 300px;"));
        
        Widget megaMenuCard = Card.of(Column.of(
            Header.of(5, "MegaMenu").modifier(new Modifier().style("margin-top: 0; margin-bottom: 1.5rem; color: var(--text-color); font-weight: 600;")),
            MegaMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu)
        )).modifier(new Modifier().style("padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: var(--surface-color); width: 100%;"));
        
        Widget contextMenu = ContextMenu.of(fileMenu, editMenu, usersMenu, eventsMenu, optionsMenu);

        return Column.of(
            contextMenu,
            Header.of(2, "Menus").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px; color: var(--text-color);")),
            menubarCard,
            breadcrumbCard,
            stepsCard,
            Row.of(tieredMenuCard, plainMenuCard, overlayMenuCard).modifier(new Modifier().style("gap: 20px; align-items: stretch; flex-wrap: wrap; display: flex; width: 100%; margin-bottom: 20px;")),
            megaMenuCard
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px; gap: 20px; display: flex; flex-direction: column;"));
    }
}
