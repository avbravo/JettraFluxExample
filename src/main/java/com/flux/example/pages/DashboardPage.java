package com.flux.example.pages;

import io.jettra.flux.widgets.ChartsLine;
import io.jettra.flux.widgets.Row;
import io.jettra.flux.widgets.Column;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Datatable;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.flux.widgets.Header;
import io.jettra.flux.widgets.CharsPie;
import io.jettra.flux.widgets.Center;
import com.flux.example.pages.template.TemplatePage;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DashboardPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Dashboard - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
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
        return Center.of(
            Column.of(
                Header.of(1, "Panel de Control Principal"),
                Row.of(pieChart, lineChart),
                dataTable
            )
        );
    }
}
