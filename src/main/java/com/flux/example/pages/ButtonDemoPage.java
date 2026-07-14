package com.flux.example.pages;

import com.sun.net.httpserver.HttpExchange;
import io.jettra.flux.core.Widget;
import io.jettra.flux.widgets.Button;
import io.jettra.flux.widgets.Scaffold;
import io.jettra.flux.widgets.Top;
import io.jettra.flux.widgets.Label;
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;
import java.util.Map;
import com.flux.example.pages.template.TemplatePage;

@JettraPageSincronized(SyncType.ALL)
public class ButtonDemoPage extends FluxBaseHandler {

    @Override
    protected Widget buildUI(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        Widget content = Label.of("Demostración de Botón").modifier(new io.jettra.flux.core.Modifier().cssClass("demo-title"));
        Widget btn = Button.of(io.jettra.flux.widgets.Text.of("Click Me"))
            .modifier(new io.jettra.flux.core.Modifier().padding(10));
        ((Button)btn).onClick(obj -> {}); // To fulfill action but Widget.action doesn't exist, we use onClick, wait Widget has onClick taking Consumer, or we just omit action.
        
        return Scaffold.of()
            .topBar(Top.of(io.jettra.flux.widgets.Header.of(3, "Catálogo - Button")))
            .body(io.jettra.flux.widgets.Column.of(content, btn));
    }
}
