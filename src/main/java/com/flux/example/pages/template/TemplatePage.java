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
            + "body { margin: 0; padding: 0; font-family: 'Inter', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }\n"
            + ".top-row.professional-top { display: flex; flex-direction: row; align-items: center; justify-content: space-between; padding: 15px 30px; background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); color: white; width: 100%; box-sizing: border-box; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }\n"
            + ".top-row.professional-top > div { display: flex; align-items: center; gap: 15px; }\n"
            + ".professional-top h2 { margin: 0; font-size: 1.5rem; font-weight: 600; }\n"
            + ".professional-top p { margin: 0; font-size: 1rem; opacity: 0.9; }\n"
            + ".professional-left { background-color: #2c3e50; color: #ecf0f1; min-height: calc(100vh - 70px); padding: 25px 15px; min-width: 260px; box-shadow: 2px 0 10px rgba(0,0,0,0.05); }\n"
            + ".professional-left h3 { margin-top: 0; font-size: 0.9rem; text-transform: uppercase; letter-spacing: 1.5px; color: #7f8c8d; margin-bottom: 15px; padding-left: 10px; }\n"
            + ".professional-left a, .professional-left p { color: #bdc3c7; text-decoration: none; display: flex; align-items: center; padding: 12px 15px; border-radius: 8px; transition: all 0.3s ease; margin-bottom: 5px; cursor: pointer; }\n"
            + ".professional-left a:hover, .professional-left p:hover { background-color: #34495e; color: #ffffff; transform: translateX(5px); }\n"
            + ".professional-left i { margin-right: 15px; font-size: 1.2rem; width: 25px; text-align: center; }\n"
            + ".professional-center { padding: 30px; flex: 1; overflow-y: auto; box-sizing: border-box; width: 100%; }\n"
            + ".espresso-dashboard { display: flex; flex-direction: row; flex-wrap: wrap; }\n"
            + "/* Card Styling */\n"
            + ".espresso-card { background: white; border-radius: 12px; box-shadow: 0 5px 15px rgba(0,0,0,0.03); transition: transform 0.3s ease, box-shadow 0.3s ease; padding: 20px; border: 1px solid rgba(0,0,0,0.05); }\n"
            + ".espresso-card:hover { transform: translateY(-3px); box-shadow: 0 8px 25px rgba(0,0,0,0.08); }\n"
            + "/* Responsive */\n"
            + "@media (max-width: 768px) {\n"
            + "  .espresso-dashboard { flex-direction: column !important; }\n"
            + "  .professional-left { min-height: auto; min-width: 100%; padding: 15px; }\n"
            + "  .top-row.professional-top { flex-direction: column; text-align: center; gap: 15px; }\n"
            + "  .professional-center { padding: 15px; }\n"
            + "}\n"
            + "</style>");

        // --- Left Menu with Icons and Submenus ---
        Widget menu = Left.of(
            Header.of(3, "Navegación"),
            Menu.of(
                MenuItem.of(
                    Icon.of("fas fa-tachometer-alt"),
                    Link.of(JettraServer.resolvePath("/dashboard"), " Dashboard")
                ),
                MenuItem.of(
                    Icon.of("fas fa-wpforms"),
                    Link.of(JettraServer.resolvePath("/forms"), " Formularios")
                ),
                MenuItem.of(
                    Icon.of("fas fa-cogs"),
                    Paragraph.of(" Configuración"),
                    Menu.of(
                        MenuItem.of(
                            Icon.of("fas fa-user-shield"),
                            Link.of(JettraServer.resolvePath("/roles"), " Roles")
                        ),
                        MenuItem.of(
                            Icon.of("fas fa-users"),
                            Link.of(JettraServer.resolvePath("/users"), " Usuarios")
                        )
                    )
                ),
                MenuItem.of(
                    Icon.of("fas fa-sign-out-alt"),
                    Link.of(JettraServer.resolvePath("/login?logout=true"), " Cerrar Sesión")
                )
            )
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-left"));

        // --- Professional Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                Row.of(
                    Icon.of("fas fa-cubes"),
                    Header.of(2, " JettraFlux Pro Admin")
                ),
                Row.of(
                    ThemeChanged.of().current(currentTheme),
                    Paragraph.of("Usuario: " + username)
                )
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-row professional-top"))
        );

        // --- Center Content from Subclass ---
        Widget centerContent = Column.of(
            customCss,
            buildCenter(exchange, params, currentTheme)
        ).modifier(new io.jettra.flux.core.Modifier().cssClass("professional-center"));

        // --- Footer ---
        Widget footerContent = Footer.of(
            Paragraph.of("© 2026 JettraStack Foundation - Powered by JettraFlux")
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
