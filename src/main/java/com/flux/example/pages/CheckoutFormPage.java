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
public class CheckoutFormPage extends TemplatePage {

    @Override
    protected String getTitle() {
        return "Checkout - JettraFlux Pro";
    }

    @Override
    protected Widget buildCenter(HttpExchange exchange, Map<String, String> params, String currentTheme) {
        
        Widget billingForm = Card.of(Column.of(
            Header.of(4, "Billing Address").modifier(new Modifier().style("margin-bottom: 20px; font-weight: 700;")),
            Row.of(
                Column.of(Label.of("First Name").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("fname", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(Label.of("Last Name").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("lname", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1;"))
            ).modifier(new Modifier().style("margin-bottom: 15px; width: 100%;")),
            Column.of(Label.of("Address").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("address", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("margin-bottom: 15px; width: 100%;")),
            Row.of(
                Column.of(Label.of("City").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("city", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(Label.of("Postal Code").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("zip", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1;"))
            ).modifier(new Modifier().style("margin-bottom: 30px; width: 100%;")),
            
            Header.of(4, "Payment Method").modifier(new Modifier().style("margin-bottom: 20px; font-weight: 700;")),
            Row.of(
                Row.of(RadioButton.of("cc", "payment"), Text.of("Credit Card").modifier(new Modifier().style("margin-left: 10px; font-weight: 500;"))).modifier(new Modifier().style("align-items: center; margin-right: 20px; padding: 15px; border: 1px solid #e2e8f0; border-radius: 8px; flex: 1; background: #f8fafc;")),
                Row.of(RadioButton.of("pp", "payment"), Text.of("PayPal").modifier(new Modifier().style("margin-left: 10px; font-weight: 500;"))).modifier(new Modifier().style("align-items: center; padding: 15px; border: 1px solid #e2e8f0; border-radius: 8px; flex: 1; background: #f8fafc;"))
            ).modifier(new Modifier().style("margin-bottom: 20px; width: 100%;")),
            Column.of(Label.of("Card Number").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("ccnum", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("margin-bottom: 15px; width: 100%;")),
            Row.of(
                Column.of(Label.of("Expiration Date").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("exp", "MM/YY").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1; margin-right: 20px;")),
                Column.of(Label.of("CVV").modifier(new Modifier().style("margin-bottom: 5px;")), TextField.of("cvv", "").modifier(new Modifier().style("padding: 10px; border-radius: 6px; border: 1px solid #cbd5e1; width: 100%;"))).modifier(new Modifier().style("flex: 1;"))
            ).modifier(new Modifier().style("margin-bottom: 30px; width: 100%;")),

            Button.of("Place Order").modifier(new Modifier().style("background: #3b82f6; color: white; padding: 12px; border-radius: 6px; font-weight: 600; font-size: 1.1rem; width: 100%; text-align: center;"))
        )).modifier(new Modifier().style("flex: 2; padding: 40px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: white; margin-right: 30px; min-width: 400px;"));

        Widget summary = Card.of(Column.of(
            Header.of(4, "Order Summary").modifier(new Modifier().style("margin-bottom: 20px; font-weight: 700;")),
            Row.of(Text.of("Bamboo Watch").modifier(new Modifier().style("font-weight: 600;")), Text.of("$65.00")).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 10px;")),
            Row.of(Text.of("Black Watch x 2").modifier(new Modifier().style("font-weight: 600;")), Text.of("$144.00")).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 10px;")),
            Row.of(Text.of("Blue Band").modifier(new Modifier().style("font-weight: 600;")), Text.of("$79.00")).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 20px;")),
            Divider.of(),
            Row.of(Text.of("Subtotal"), Text.of("$288.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-top: 15px; margin-bottom: 15px;")),
            Row.of(Text.of("Shipping"), Text.of("$10.00").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 15px;")),
            Row.of(Text.of("Tax"), Text.of("$23.84").modifier(new Modifier().style("font-weight: 600;"))).modifier(new Modifier().style("justify-content: space-between; margin-bottom: 20px;")),
            Divider.of(),
            Row.of(Text.of("Total").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem;")), Text.of("$321.84").modifier(new Modifier().style("font-weight: 700; font-size: 1.2rem; color: #3b82f6;"))).modifier(new Modifier().style("justify-content: space-between; margin-top: 20px;"))
        )).modifier(new Modifier().style("flex: 1; padding: 30px; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05); background: #f8fafc; border: 1px solid #e2e8f0; min-width: 300px; height: max-content;"));

        return Column.of(
            Header.of(2, "Checkout").modifier(new Modifier().style("margin-top: 0; font-weight: 700; margin-bottom: 20px;")),
            Row.of(billingForm, summary).modifier(new Modifier().style("width: 100%; flex-wrap: wrap; align-items: flex-start; gap: 20px;"))
        ).modifier(new Modifier().style("width: 100%; align-items: stretch; max-width: 1200px; padding: 20px;"));
    }
}
