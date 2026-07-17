package com.flux.example.pages.template;


import com.flux.example.pages.FluxBaseHandler;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;

import io.jettra.flux.widgets.ActionIcon;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Dashboard;
import io.jettra.flux.widgets.Footer;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Icon;
import io.jettra.flux.widgets.Left;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.SidebarCategory;
import io.jettra.flux.widgets.SidebarLogo;
import io.jettra.flux.widgets.ThemeChanged;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.WidgetLet;
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
            .addFunction("restoreMenus",
                         "document.querySelectorAll('.widgetlet-children').forEach(function(c) {\n" +
                         "  var key = c.getAttribute('data-exp-key');\n" +
                         "  if(key && localStorage.getItem(key) === 'open') {\n" +
                         "    c.style.display = 'block';\n" +
                         "    var iconId = c.id.replace('_children', '_icon');\n" +
                         "    var icon = document.getElementById(iconId);\n" +
                         "    if(icon) icon.className = 'fas fa-chevron-down';\n" +
                         "  }\n" +
                         "});")
            .build();

        // Ensure restoreMenus is called after DOM updates
        js += "\n<script>\n" +
              "document.addEventListener('DOMContentLoaded', restoreMenus);\n" +
              "if (typeof window.MutationObserver !== 'undefined') {\n" +
              "  var observer = new MutationObserver(function(mutations) {\n" +
              "    restoreMenus();\n" +
              "  });\n" +
              "  observer.observe(document.body, { childList: true, subtree: true });\n" +
              "}\n" +
              "setTimeout(restoreMenus, 100);\n" + // Fallback
              "</script>";

        Widget customCss = Paragraph.of(io.jettra.flux.theme.OceanTheme.Template.CustomCSS + "\n" + js);

         WidgetLet ecommMenu =  WidgetLet.of("E-Commerce").icon( Icon.HOME);
        ecommMenu.add(WidgetLet.of("Dashboard").icon(Icon.CHART_LINE).url(JettraServer.resolvePath("/dashboard")));
        ecommMenu.add( WidgetLet.of("Product Overview").icon(Icon.SEARCH).url(JettraServer.resolvePath("/product-overview")));
        ecommMenu.add( WidgetLet.of("Product List").icon(Icon.LIST).url(JettraServer.resolvePath("/product-list")));
        ecommMenu.add( WidgetLet.of("New Product").icon(Icon.PLUS).url(JettraServer.resolvePath("/new-product")));
        ecommMenu.add( WidgetLet.of("Shopping Cart").icon(Icon.SHOPPING_CART).url(JettraServer.resolvePath("/shopping-cart")));
        ecommMenu.add( WidgetLet.of("Checkout Form").icon(Icon.CHECK).url(JettraServer.resolvePath("/checkout-form")));
        ecommMenu.add( WidgetLet.of("Order History").icon(Icon.HISTORY).url(JettraServer.resolvePath("/order-history")));
        ecommMenu.add( WidgetLet.of("Order Summary").icon(Icon.RECEIPT).url(JettraServer.resolvePath("/order-summary")));
// Example
         WidgetLet exampleMenu =  WidgetLet.of("Example").icon( Icon.COG);
       exampleMenu.add( WidgetLet.of("Person").icon(Icon.CHART_LINE).url(JettraServer.resolvePath("/person")));

        // Apps
         WidgetLet appsMenu =  WidgetLet.of("Apps").icon( Icon.TH_LARGE);
        appsMenu.add( WidgetLet.of("Chat").icon( Icon.COMMENTS).url(JettraServer.resolvePath("/chat")));
        appsMenu.add( WidgetLet.of("Mail Inbox").icon( Icon.ENVELOPE).url(JettraServer.resolvePath("/mail-inbox")));
        appsMenu.add( WidgetLet.of("Task List").icon( Icon.CHECK).url(JettraServer.resolvePath("/tasklist")));
        appsMenu.add( WidgetLet.of("Files").icon(Icon.FOLDER).url(JettraServer.resolvePath("/files")));
        appsMenu.add( WidgetLet.of("File").icon(Icon.FILE).url(JettraServer.resolvePath("/file")));

         WidgetLet userManagementMenu =  WidgetLet.of("User Management").icon( Icon.USER);
        userManagementMenu.add( WidgetLet.of("Profile List").icon(Icon.USERS).url(JettraServer.resolvePath("/profile-list")));
        userManagementMenu.add( WidgetLet.of("Basic Information").icon( Icon.INFO_CIRCLE).url(JettraServer.resolvePath("/profile-basic-information")));

         WidgetLet uiKitMenu =  WidgetLet.of("UI Components").icon( Icon.LAYER_GROUP);
        uiKitMenu.add( WidgetLet.of("Input").icon(Icon.EDIT).url(JettraServer.resolvePath("/input")));
        uiKitMenu.add( WidgetLet.of("Forms").icon(Icon.ALIGN_JUSTIFY).url(JettraServer.resolvePath("/forms")));
        uiKitMenu.add( WidgetLet.of("Icon").icon(Icon.ALIGN_JUSTIFY).url(JettraServer.resolvePath("/icon")));
        uiKitMenu.add( WidgetLet.of("Button Demo").icon( Icon.MOUSE_POINTER).url(JettraServer.resolvePath("/button-demo")));
        uiKitMenu.add( WidgetLet.of("Table").icon( Icon.CHART_BAR).url(JettraServer.resolvePath("/table")));
        uiKitMenu.add( WidgetLet.of("DataView").icon( Icon.LIST).url(JettraServer.resolvePath("/dataview")));
        uiKitMenu.add( WidgetLet.of("Tree").icon(Icon.SITEMAP).url(JettraServer.resolvePath("/tree")));
        uiKitMenu.add( WidgetLet.of("Panel").icon( Icon.TH_LARGE).url(JettraServer.resolvePath("/panel")));
        uiKitMenu.add( WidgetLet.of("Overlay").icon(Icon.CLONE).url(JettraServer.resolvePath("/overlay")));
        uiKitMenu.add( WidgetLet.of("Media").icon(Icon.IMAGE).url(JettraServer.resolvePath("/media")));
        uiKitMenu.add( WidgetLet.of("Menu").icon(Icon.BARS).url(JettraServer.resolvePath("/menu")));
        uiKitMenu.add( WidgetLet.of("Message").icon( Icon.COMMENTS).url(JettraServer.resolvePath("/message")));
        uiKitMenu.add( WidgetLet.of("Charts").icon(Icon.CHART_PIE).url(JettraServer.resolvePath("/charts")));
        uiKitMenu.add( WidgetLet.of("Timeline").icon(Icon.CALENDAR_ALT).url(JettraServer.resolvePath("/timeline")));
        uiKitMenu.add( WidgetLet.of("Misc").icon( Icon.CUBE).url(JettraServer.resolvePath("/misc")));
        
         WidgetLet uiLayoutMenu =  WidgetLet.of("Layout & Grid").icon( Icon.WINDOW_MAXIMIZE);
        uiLayoutMenu.add( WidgetLet.of("Card Demo").icon( Icon.WINDOW_MAXIMIZE).url(JettraServer.resolvePath("/card-demo")));
        uiLayoutMenu.add( WidgetLet.of("Grid Layout").icon(Icon.BORDER_ALL).url(JettraServer.resolvePath("/grid-demo")));
        
         WidgetLet rootUiKitMenu =  WidgetLet.of("UI Kit").icon( Icon.LAYER_GROUP);
        rootUiKitMenu.add(uiKitMenu);
        rootUiKitMenu.add(uiLayoutMenu);

        // --- Left Sidebar (Ocean Style) ---
        Widget menu = Left.of(
             SidebarLogo.of( Icon.LAYER_GROUP, "Ocean"),
             SidebarCategory.of("Navigation"),
            ecommMenu,
            exampleMenu,
            appsMenu,
            userManagementMenu,
            rootUiKitMenu
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
                 ActionIcon.of( Icon.BARS + " top-bars-icon", "toggleSidebar()"),
                Header.of(4, "Dashboard").modifier(new io.jettra.flux.core.Modifier().cssClass("top-dashboard-title"))
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-left-section")),
            
            Row.of(
                Icon.of( Icon.SEARCH),
                Icon.of( Icon.BELL),
                Paragraph.of(langHtml),
                ThemeChanged.of().current(currentTheme),
                Paragraph.of(profileHtml)
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-right-section").style("gap: 15px; align-items: center;"))
        );

        // --- Center Content from Subclass ---
        Widget centerContent = Column.of(
            customCss,
            buildCenter(exchange, params, currentTheme)
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-center espresso-center"));

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
