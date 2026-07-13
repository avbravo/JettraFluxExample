package com.facli.server.pages;

import io.jettra.flux.widgets.ChartsLine;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Dashboard;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Footer;
import io.jettra.flux.widgets.Datatable;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Link;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.Left;
import io.jettra.flux.widgets.MenuItem;
import io.jettra.flux.widgets.Menu;
import io.jettra.flux.widgets.CharsPie;
import io.jettra.flux.widgets.Center;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.server.JettraServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import io.jettra.flux.widgets.ThemeChanged;

public class DashboardPage extends FluxBaseHandler {

    @Override
    protected String getTitle() {
        return "Dashboard - JettraFlux";
    }

    @Override
    protected boolean onGet(HttpExchange exchange, Map<String, String> params) throws IOException {
        String user = getLoggedUser(exchange);
        if (user == null || user.isEmpty()) {
            redirect(exchange, "/login");
            return true;
        }
        return false;
    }

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        String username = getLoggedUser(exchange);
        if (username == null) {
            username = "Guest";
        }

        // --- Menu (Left) ---
        Widget menu = Left.of(
            Header.of(3, "Navegación"),
            Menu.of(
                MenuItem.of(Link.of(JettraServer.resolvePath("/dashboard"), "Dashboard Principal")),
                MenuItem.of(Link.of(JettraServer.resolvePath("/forms"), "Ejemplos Formularios")),
                MenuItem.of(Link.of(JettraServer.resolvePath("/login?logout=true"), "Cerrar Sesión"))
            )
        );

        // --- Top Bar ---
        Widget topBar = Top.of(
            Row.of(
                Header.of(2, "JettraFlux Admin"),
                ThemeChanged.of().current(currentTheme),
                Paragraph.of("Usuario: " + username)
            ).modifier(new io.jettra.flux.core.Modifier().cssClass("top-row"))
        );

        // --- Charts and Dashboard Info ---
        Widget pieChart = Card.of(
            Column.of(
                Header.of(4, "Estadísticas Rápidas"),
                CharsPie.of(Paragraph.of("[Gráfico Pastel Placeholder]"))
            )
        );

        Widget lineChart = Card.of(
            Column.of(
                Header.of(4, "Tendencias de Uso"),
                ChartsLine.of(Paragraph.of("[Gráfico Línea Placeholder]"))
            )
        );

        // --- Datatable ---
        List<String> headers = Arrays.asList("ID", "Nombre", "Rol", "Estado");
        List<List<String>> rows = Arrays.asList(
            Arrays.asList("1", "Admin User", "Administrador", "Activo"),
            Arrays.asList("2", "Demo User", "Usuario", "Inactivo"),
            Arrays.asList("3", "AV Bravo", "Desarrollador", "Activo")
        );
        Widget dataTable = Card.of(
            Column.of(
                Header.of(4, "Usuarios Recientes"),
                Datatable.of(headers, rows)
            )
        );

        // --- Center Content ---
        Widget centerContent = Center.of(
            Column.of(
                Header.of(1, "Panel de Control Principal"),
                Row.of(pieChart, lineChart),
                dataTable
            )
        );

        // --- Footer ---
        Widget footerContent = Footer.of(
            Paragraph.of("© 2026 JettraStack Foundation - Powered by JettraFlux")
        );

        // Build main layout: Row(Left, Column(Top, Center, Footer))
        // or Dashboard widget wrapping everything
        Widget body = Dashboard.of(Row.of(menu,
                Column.of(
                    topBar,
                    centerContent,
                    footerContent
                ).modifier(new io.jettra.flux.core.Modifier().width("80%"))
            ).modifier(new io.jettra.flux.core.Modifier().width("100%"))
        );

        return Scaffold.of().body(body);
    }
}
