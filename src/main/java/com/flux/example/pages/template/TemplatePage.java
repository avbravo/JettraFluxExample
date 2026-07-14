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

        Widget customCss = Paragraph.of("<style>\n"
            + "/* Atlantis Layout Adjustments */\n"
            + ".espresso-left { border-right: none !important; box-shadow: 2px 0 10px rgba(0,0,0,0.2) !important; z-index: 100; }\n"
            + ".espresso-top { border-bottom: 1px solid rgba(128,128,128,0.1); justify-content: space-between; }\n"
            + ".top-left-section { display: flex; align-items: center; gap: 20px; }\n"
            + ".top-right-section { display: flex; align-items: center; gap: 15px; color: var(--on-surface-color); }\n"
            + ".top-right-section i { font-size: 1.2rem; cursor: pointer; transition: color 0.2s; }\n"
            + ".top-right-section i:hover { color: var(--primary-color); }\n"
            + ".top-btn-today { background-color: #6366F1; color: white; border: none; padding: 8px 16px; border-radius: 6px; font-weight: 600; cursor: pointer; display: flex; align-items: center; gap: 8px; }\n"
            + ".top-avatar { width: 32px; height: 32px; border-radius: 50%; background-color: #ccc; display: flex; justify-content: center; align-items: center; font-weight: bold; color: #333; }\n"
            + ".sidebar-logo { font-size: 1.5rem; font-weight: 700; color: var(--on-surface-color); padding: 10px 15px; margin-bottom: 20px; display: flex; align-items: center; gap: 10px; }\n"
            + ".sidebar-category { margin-top: 20px; font-size: 0.75rem; text-transform: uppercase; letter-spacing: 1px; color: #64748b; margin-bottom: 10px; padding-left: 15px; font-weight: 600; }\n"
            + ".professional-left a, .professional-left p { color: var(--on-surface-color); opacity: 0.8; text-decoration: none; display: flex; align-items: center; padding: 10px 15px; border-radius: 8px; transition: all 0.2s ease; margin-bottom: 4px; cursor: pointer; font-weight: 500; font-size: 0.95rem; }\n"
            + ".professional-left a:hover, .professional-left p:hover { background-color: rgba(128,128,128,0.1); opacity: 1; }\n"
            + ".professional-left a.active { background-color: var(--primary-color); color: var(--on-primary-color) !important; opacity: 1; }\n"
            + ".professional-left a.active i { color: var(--on-primary-color) !important; }\n"
            + ".professional-left i { margin-right: 12px; font-size: 1.1rem; width: 20px; text-align: center; color: #94a3b8; }\n"
            + "/* Card Styling */\n"
            + ".espresso-card { background: var(--surface-color); border-radius: 12px; padding: 20px; border: 1px solid rgba(128, 128, 128, 0.1); }\n"
            + ".professional-center { height: 100%; display: flex; flex-direction: column; gap: 1.5rem; }\n"
            + ".top-bars-icon { font-size: 1.2rem; cursor: pointer; color: var(--on-surface-color); opacity: 0.7; margin-right: 15px; }\n"
            + ".top-dashboard-title { margin: 0; font-weight: 600; font-size: 1.1rem; }\n"
            + "</style>\n"
            + "<script>\n"
            + "function toggleSidebar() {\n"
            + "  var sidebar = document.querySelector('.espresso-left');\n"
            + "  if(sidebar) sidebar.classList.toggle('open');\n"
            + "}\n"
            + "</script>");

        io.jettra.flux.widgets.WidgetLet widgetLetMenu = io.jettra.flux.widgets.WidgetLet.of("UI Components").icon("<i class='fas fa-cube'></i>");
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Button").icon("<i class='fas fa-mouse-pointer'></i>").url(JettraServer.resolvePath("/button-demo")));
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Card").icon("<i class='fas fa-window-maximize'></i>").url(JettraServer.resolvePath("/card-demo")));

        // --- Left Sidebar (Atlantis Style) ---
        Widget menu = Left.of(
            Paragraph.of("<div class='sidebar-logo'><i class='fas fa-layer-group'></i> Atlantis</div>"),
            
            Paragraph.of("<div class='sidebar-category'>Dashboards</div>"),
            Menu.of(
                MenuItem.of(
                    Icon.of("fas fa-home"),
                    Link.of(JettraServer.resolvePath("/dashboard"), " E-Commerce").modifier(new io.jettra.flux.core.Modifier().cssClass("active"))
                )
            ),
            
            Paragraph.of("<div class='sidebar-category'>Apps</div>"),
            Menu.of(
                MenuItem.of(
                    Icon.of("fas fa-th-large"),
                    Link.of(JettraServer.resolvePath("/cms"), " CMS")
                ),
                MenuItem.of(
                    Icon.of("fas fa-comments"),
                    Link.of(JettraServer.resolvePath("/chat"), " Chat")
                )
            ),
            
            Paragraph.of("<div class='sidebar-category'>UI Kit</div>"),
            widgetLetMenu,
            
            Paragraph.of("<div class='sidebar-category'>System</div>"),
            Menu.of(
                MenuItem.of(
                    Icon.of("fas fa-sign-out-alt"),
                    Link.of(JettraServer.resolvePath("/login?logout=true"), " Logout")
                )
            )
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-left"));

        // --- Professional Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                Paragraph.of("<i class='fas fa-bars top-bars-icon' onclick='toggleSidebar()'></i>"),
                Header.of(4, "E-Commerce Dashboard").modifier(new io.jettra.flux.core.Modifier().cssClass("top-dashboard-title"))
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-left-section")),
            
            Row.of(
                Icon.of("fas fa-search"),
                Icon.of("fas fa-bell"),
                Icon.of("fas fa-comment-alt"),
                ThemeChanged.of().current(currentTheme),
                Paragraph.of("<div class='top-avatar'>U</div>"),
                Paragraph.of("<button class='top-btn-today'><i class='fas fa-calendar'></i> Today</button>")
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
