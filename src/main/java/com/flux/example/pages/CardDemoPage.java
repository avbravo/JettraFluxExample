package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.widgets.Card;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.Label;
import io.jettra.flux.widgets.Paragraph;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Map;

@JettraPageSincronized(SyncType.ALL)
public class CardDemoPage extends FluxBaseHandler {

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        Widget content = Card.of(
            io.jettra.flux.widgets.Column.of(
                Label.of("Título del Card").modifier(new io.jettra.flux.core.Modifier().cssClass("bold-label")),
                Paragraph.of("Este es el contenido de un Card creado de manera programática en Java. No hay HTML duro aquí.")
            )
        ).modifier(new io.jettra.flux.core.Modifier().padding(15).cssClass("card-shadow"));
        
        return Scaffold.of()
            .topBar(Top.of(io.jettra.flux.widgets.Header.of(3, "Catálogo - Card")))
            .body(content);
    }
}
