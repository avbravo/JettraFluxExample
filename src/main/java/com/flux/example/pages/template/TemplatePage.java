package com.flux.example.pages.template;

import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Dashboard;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Footer;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Link;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Left;
import io.jettra.flux.widgets.MenuItem;
import io.jettra.flux.widgets.Menu;
import io.jettra.flux.widgets.Icon;
import io.jettra.flux.widgets.ThemeChanged;
import com.flux.example.pages.FluxBaseHandler;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;
import java.util.Map;

public abstract class TemplatePage extends FluxBaseHandler {

    protected abstract Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme);

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        String username = getLoggedUser(exchange);
        if (username == null || username.isEmpty()) {
            try { redirect(exchange, "/login"); } catch (Exception e) {}
            return Column.of();
        }

        Widget customCss = Paragraph.of(io.jettra.flux.theme.OceanTheme.Template.CustomCSS);

        io.jettra.flux.widgets.WidgetLet widgetLetMenu = io.jettra.flux.widgets.WidgetLet.of("UI Components").icon("<i class='" + io.jettra.flux.widgets.Icon.CUBE + "'></i>");
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Button").icon("<i class='" + io.jettra.flux.widgets.Icon.MOUSE_POINTER + "'></i>").url(JettraServer.resolvePath("/button-demo")));
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Card").icon("<i class='" + io.jettra.flux.widgets.Icon.WINDOW_MAXIMIZE + "'></i>").url(JettraServer.resolvePath("/card-demo")));

        // --- Left Sidebar (Atlantis Style) ---
        Widget menu = Left.of(
            io.jettra.flux.widgets.SidebarLogo.of(io.jettra.flux.widgets.Icon.LAYER_GROUP, "Atlantis"),
            
            io.jettra.flux.widgets.SidebarCategory.of("Dashboards"),
            Menu.of(
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/dashboard"), 
                            Icon.of(io.jettra.flux.widgets.Icon.HOME), 
                            io.jettra.flux.widgets.Text.of(" E-Commerce")
                    ).modifier(new io.jettra.flux.core.Modifier().cssClass("active"))
                )
            ),
            
            io.jettra.flux.widgets.SidebarCategory.of("Apps"),
            Menu.of(
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/cms"), 
                            Icon.of(io.jettra.flux.widgets.Icon.TH_LARGE), 
                            io.jettra.flux.widgets.Text.of(" CMS")
                    )
                ),
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/chat"), 
                            Icon.of(io.jettra.flux.widgets.Icon.COMMENTS), 
                            io.jettra.flux.widgets.Text.of(" Chat")
                    )
                )
            ),
            
            io.jettra.flux.widgets.SidebarCategory.of("UI Kit"),
            widgetLetMenu,
            
            io.jettra.flux.widgets.SidebarCategory.of("Example"),
            Menu.of(
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/forms"), 
                            Icon.of(io.jettra.flux.widgets.Icon.EDIT), 
                            io.jettra.flux.widgets.Text.of(" Forms Page")
                    )
                ),
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/button-demo"), 
                            Icon.of(io.jettra.flux.widgets.Icon.MOUSE_POINTER), 
                            io.jettra.flux.widgets.Text.of(" Button Demo")
                    )
                ),
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/card-demo"), 
                            Icon.of(io.jettra.flux.widgets.Icon.WINDOW_MAXIMIZE), 
                            io.jettra.flux.widgets.Text.of(" Card Demo")
                    )
                )
            ),
            
            io.jettra.flux.widgets.SidebarCategory.of("System"),
            Menu.of(
                MenuItem.of(
                    Link.of(JettraServer.resolvePath("/login?logout=true"), 
                            Icon.of(io.jettra.flux.widgets.Icon.SIGN_OUT_ALT), 
                            io.jettra.flux.widgets.Text.of(" Logout")
                    )
                )
            )
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-left"));

        // --- Professional Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                io.jettra.flux.widgets.ActionIcon.of(io.jettra.flux.widgets.Icon.BARS + " top-bars-icon", "toggleSidebar()"),
                Header.of(4, "E-Commerce Dashboard").modifier(new io.jettra.flux.core.Modifier().cssClass("top-dashboard-title"))
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-left-section")),
            
            Row.of(
                Icon.of(io.jettra.flux.widgets.Icon.SEARCH),
                Icon.of(io.jettra.flux.widgets.Icon.BELL),
                Icon.of(io.jettra.flux.widgets.Icon.COMMENT_ALT),
                ThemeChanged.of().current(currentTheme),
                io.jettra.flux.widgets.TopAvatar.of("U"),
                io.jettra.flux.widgets.ActionButton.of(io.jettra.flux.widgets.Icon.CALENDAR, "Today")
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-right-section"))
        );

        // --- Center Content from Subclass ---
        Widget centerContent = Column.of(
            customCss,
            buildCenter(exchange, params, currentTheme)
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-center"));

        // --- Footer ---
        Widget footerContent = Footer.of(
            Paragraph.of("© 2026 JettraStack - Atlantis Clone")
        );

        Widget body = Dashboard.of(
            topBar,
            menu,
            centerContent,
            footerContent
        );

        return Scaffold.of().body(body);
    }
}
