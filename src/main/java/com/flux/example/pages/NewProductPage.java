package com.flux.example.pages;

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
public class NewProductPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "New Product - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget form = Card.of(Column.of(
            Row.of(
                Column.of(
                    Label.of("Product Name").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    TextField.of("name", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))
                ).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(
                    Label.of("SKU").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    TextField.of("sku", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))
                ).modifier(new Modifier().style("flex: 1;"))
            ).modifier(new Modifier().style("width: 100%; margin-bottom: 20px;")),
            
            Column.of(
                Label.of("Description").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                TextArea.create().name("desc").rows(4).modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%; font-family: inherit;"))
            ).modifier(new Modifier().style("width: 100%; margin-bottom: 20px;")),

            Row.of(
                Column.of(
                    Label.of("Price").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    TextField.of("price", "0.00").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))
                ).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(
                    Label.of("Category").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    // Using basic TextField as select for simulation
                    TextField.of("cat", "Select Category").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))
                ).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(
                    Label.of("Status").modifier(new Modifier().style("margin-bottom: 5px; font-weight: 600;")),
                    Row.of(ToggleSwitch.of(), Text.of("Active").modifier(new Modifier().style("margin-left: 10px;"))).modifier(new Modifier().style("align-items: center; margin-top: 10px;"))
                ).modifier(new Modifier().style("flex: 1;"))
            ).modifier(new Modifier().style("width: 100%; margin-bottom: 30px;")),
            
            Header.of(5, "Product Images").modifier(new Modifier().style("margin-bottom: 10px;")),
            Column.of(
                Icon.of(Icon.Heroicons.CLOUD_UPLOAD).modifier(new Modifier().style("width: 48px; height: 48px; color: #64748b; margin-bottom: 15px;")),
                Text.of("Drag and drop images here").modifier(new Modifier().style("color: #64748b;"))
            ).modifier(new Modifier().style("border: 2px dashed #cbd5e1; border-radius: 8px; padding: 40px; align-items: center; justify-content: center; background: #f8fafc; cursor: pointer; width: 100%; margin-bottom: 30px;")),
            
            Row.of(
                Button.of("Cancel").modifier(new Modifier().style("background: white; color: #1e293b; border: 1px solid #cbd5e1; padding: 10px 20px; border-radius: 6px; margin-right: 15px; font-weight: 600;")),
                Button.of("Save Product").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 10px 20px; border-radius: 6px; font-weight: 600;"))
            ).modifier(new Modifier().style("justify-content: flex-end; width: 100%;"))

        )).modifier(new Modifier().style("width: 100%; padding: 40px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white;"));

        return Column.of(
            Header.of(2, "Create New Product").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            form
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1000px; padding: 20px;"));
    }
}
