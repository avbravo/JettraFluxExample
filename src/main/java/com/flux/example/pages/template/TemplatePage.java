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
        
        // Simulating CredentialFlux from context/session (or could be fetched from DB)
        // Usually you'd fetch this from JettraContext or DB based on username
        String userInitial = username.substring(0, 1).toUpperCase();
        
        io.jettra.flux.model.CredentialFlux credential = (io.jettra.flux.model.CredentialFlux) io.jettra.server.core.JettraContext.getCurrent().get(io.jettra.server.core.JettraContext.Scope.SESSION, "credentialFlux");
        String displayName = username;
        String photoHtml = "<div style=\"width:32px; height:32px; border-radius:50%; background-color:#3b82f6; color:white; display:flex; align-items:center; justify-content:center; font-weight:bold; margin-right:8px;\">" + userInitial + "</div>";
        
        if (credential != null) {
            displayName = credential.name() != null && !credential.name().isEmpty() ? credential.name() : username;
            if (credential.photo() != null && !credential.photo().isEmpty()) {
                photoHtml = "<img src=\"" + credential.photo() + "\" style=\"width:32px; height:32px; border-radius:50%; margin-right:8px; object-fit:cover;\" />";
            } else {
                photoHtml = "<div style=\"width:32px; height:32px; border-radius:50%; background-color:#3b82f6; color:white; display:flex; align-items:center; justify-content:center; font-weight:bold; margin-right:8px;\"><i class=\"fas fa-user\"></i></div>";
            }
        }
        
        String js = io.jettra.flux.core.JSBuilder.create()
            .addFunction("toggleSidebar", 
                         "var left = document.querySelector('.espresso-left');\n" +
                         "if (left) left.classList.toggle('open');")
            .addFunction("changeLang", "lang", 
                         "document.cookie = 'jettra_lang=' + lang + '; path=/';\n" +
                         "window.location.reload();")
            .addFunction("toggleProfileMenu", 
                         "var pm = document.getElementById('profile-menu');\n" +
                         "if(pm) pm.style.display = (pm.style.display === 'none') ? 'block' : 'none';")
            .build();

        Widget customCss = Paragraph.of(io.jettra.flux.theme.OceanTheme.Template.CustomCSS + "\n" + js);

        io.jettra.flux.widgets.WidgetLet widgetLetMenu = io.jettra.flux.widgets.WidgetLet.of("UI Components").icon("<i class='" + io.jettra.flux.widgets.Icon.CUBE + "'></i>");
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Button").icon("<i class='" + io.jettra.flux.widgets.Icon.MOUSE_POINTER + "'></i>").url(JettraServer.resolvePath("/button-demo")));
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Card").icon("<i class='" + io.jettra.flux.widgets.Icon.WINDOW_MAXIMIZE + "'></i>").url(JettraServer.resolvePath("/card-demo")));
        widgetLetMenu.add(io.jettra.flux.widgets.WidgetLet.of("Grid & Layout").icon("<i class='fas fa-border-all'></i>").url(JettraServer.resolvePath("/grid-demo")));

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
                    Link.of(JettraServer.resolvePath("/table"), 
                            Icon.of(io.jettra.flux.widgets.Icon.CHART_BAR), 
                            io.jettra.flux.widgets.Text.of(" Table Page")
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
            )
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-left"));

        // User Profile Dropdown
        String profileHtml = "<div style=\"position: relative; display: inline-block; cursor: pointer;\" onclick=\"toggleProfileMenu()\">" +
                             "<div style=\"display: flex; align-items: center;\">" +
                             "  " + photoHtml +
                             "  <span style=\"font-size:14px; font-weight:500;\">" + displayName + "</span> <i class=\"fas fa-caret-down\" style=\"margin-left:5px;\"></i>" +
                             "</div>" +
                             "<div id=\"profile-menu\" style=\"display:none; position:absolute; right:0; top:40px; background:white; box-shadow:0 4px 6px rgba(0,0,0,0.1); border-radius:4px; width:150px; z-index:100; border:1px solid #ddd;\">" +
                             "  <a href=\"" + JettraServer.resolvePath("/login?logout=true") + "\" style=\"display:block; padding:10px; text-decoration:none; color:#333; font-size:14px;\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                             "</div></div>";

        // Language Switcher
        String langHtml = "<select onchange=\"changeLang(this.value)\" style=\"padding: 4px; border-radius: 4px; border: 1px solid #ccc; font-size: 12px;\">" +
                          "  <option value=\"en\">EN</option>" +
                          "  <option value=\"es\">ES</option>" +
                          "</select>";

        // --- Professional Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                io.jettra.flux.widgets.ActionIcon.of(io.jettra.flux.widgets.Icon.BARS + " top-bars-icon", "toggleSidebar()"),
                Header.of(4, "Dashboard").modifier(new io.jettra.flux.core.Modifier().cssClass("top-dashboard-title"))
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-left-section")),
            
            Row.of(
                Icon.of(io.jettra.flux.widgets.Icon.SEARCH),
                Icon.of(io.jettra.flux.widgets.Icon.BELL),
                Paragraph.of(langHtml),
                ThemeChanged.of().current(currentTheme),
                Paragraph.of(profileHtml)
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-right-section").style("gap: 15px; align-items: center;"))
        );

        // --- Center Content from Subclass ---
        Widget centerContent = Column.of(
            customCss,
            buildCenter(exchange, params, currentTheme)
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-center"));

        // --- Footer ---
        Widget footerContent = Footer.of(
            Paragraph.of("© 2026 JettraStack - JettraFlux")
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
