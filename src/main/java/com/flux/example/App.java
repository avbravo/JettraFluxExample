package com.flux.example;

import io.jettra.rest.server.JettraRestServer;
import io.jettra.server.JettraServer;
import io.jettra.server.config.ConfigInjector;
import io.jettra.server.config.JettraConfigProperty;
import io.jettra.server.discoverer.DiscoveredLoad;
import io.jettra.server.openapi.OpenApiHandler;
import io.jettra.server.openapi.SwaggerUIHandler;
import java.util.List;


/**
 * App!
 *
 */
/**
 * Hello world!
 *
 */
@DiscoveredLoad
public class App {

    @JettraConfigProperty(name = "app.title")
    private String appTitle;
    @JettraConfigProperty(name = "server.port")
    private String port;
    @JettraConfigProperty(name = "server.contextpath")
    private String contextpath;
    public static JettraServer serverInstance;
    public void initUI() {
        ConfigInjector.inject(this);
        IO.println("Iniciando aplicación Web: " + appTitle);
    }

    void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("-console")) {
            io.jettra.server.autentification.SecurityCLI.main(args);
            return;
        }

        App app = new App();
        app.initUI();
        // Configurar la ruta de redirección en ErrorPage, usando contextpath (y el puerto implícitamente por el host)
        io.jettra.wui.complex.ErrorPage.path = "http://localhost:" + app.port + app.contextpath;

        IO.println("Levantando servidor de enrutamiento JettraServer empotrado...");
        JettraServer server = new JettraServer();
        server.setErrorPage("/error");
        server.addHandler("/error", io.jettra.wui.complex.ErrorPage.class);
        server.addHandler("/swagger-ui", io.jettra.wui.complex.SwaggerUIPage.class);

        // Registro de Páginas JettraFlux
        server.addHandler("/", com.flux.example.pages.login.LoginPage.class);
        server.addHandler("/login", com.flux.example.pages.login.LoginPage.class);
        
        // E-Commerce
        server.addHandler("/dashboard", com.flux.example.pages.DashboardPage.class);
        server.addHandler("/product-overview", com.flux.example.pages.ecomerce.ProductOverviewPage.class);
        server.addHandler("/product-list", com.flux.example.pages.ecomerce.ProductListPage.class);
        server.addHandler("/new-product", com.flux.example.pages.ecomerce.NewProductPage.class);
        server.addHandler("/shopping-cart", com.flux.example.pages.ecomerce.ShoppingCartPage.class);
        server.addHandler("/checkout-form", com.flux.example.pages.ecomerce.CheckoutFormPage.class);
        server.addHandler("/order-history", com.flux.example.pages.ecomerce.OrderHistoryPage.class);
        server.addHandler("/order-summary", com.flux.example.pages.ecomerce.OrderSummaryPage.class);

        // Apps
        server.addHandler("/chat", com.flux.example.pages.ChatPage.class);
        server.addHandler("/mail-inbox", com.flux.example.pages.MailInboxPage.class);
        server.addHandler("/tasklist", com.flux.example.pages.TasklistPage.class);
        server.addHandler("/files", com.flux.example.pages.FilesPage.class);
        server.addHandler("/file", com.flux.example.pages.FilePage.class);

        // User Profile
        server.addHandler("/profile-list", com.flux.example.pages.ProfileListPage.class);
        server.addHandler("/profile-basic-information", com.flux.example.pages.ProfileBasicInformationPage.class);

        // UI Kit
        server.addHandler("/forms", com.flux.example.pages.FormsPage.class);
        server.addHandler("/button-demo", com.flux.example.pages.ButtonDemoPage.class);
        server.addHandler("/card-demo", com.flux.example.pages.CardDemoPage.class);
        server.addHandler("/grid-demo", com.flux.example.pages.GridDemoPage.class);
        server.addHandler("/table", com.flux.example.pages.TablePage.class);
        server.addHandler("/dataview", com.flux.example.pages.DataViewPage.class);
        server.addHandler("/input", com.flux.example.pages.InputPage.class);
        server.addHandler("/panel", com.flux.example.pages.PanelPage.class);
        server.addHandler("/message", com.flux.example.pages.MessagePage.class);
        server.addHandler("/misc", com.flux.example.pages.MiscPage.class);
        server.addHandler("/tree", com.flux.example.pages.TreePage.class);
        server.addHandler("/overlay", com.flux.example.pages.OverlayPage.class);
        server.addHandler("/media", com.flux.example.pages.MediaPage.class);
        server.addHandler("/menu", com.flux.example.pages.MenuPage.class);
        server.addHandler("/charts", com.flux.example.pages.ChartsPage.class);
        server.addHandler("/timeline", com.flux.example.pages.TimelinePage.class);

        // Cargamos los controladores descubiertos automáticamente
        List<Class<?>> controllers = new java.util.ArrayList<>(io.jettra.server.discoverer.DiscoveredRegistry.getDiscoveredClasses(App.class));

        // Puedes agregar aquí manualmente las clases que tengan @Discovered(automatic=false)
        // o que no tengan la anotación
        // controllers.add(MiControladorManual.class);
//        controllers.add(com.flux.example.controller.ContenedorMaritimoController.class);
        // Exponer el JSON de OpenAPI
        server.addHandler("/openapi.json", new OpenApiHandler(controllers));

        // Exponer la interfaz Swagger UI
        server.addHandler("/swagger-ui", new SwaggerUIHandler("/openapi.json"));

        // Registrar los controladores descubiertos en JettraRestServer
        JettraRestServer.registerDiscovered(server, App.class);

        // Registro manual para los que no se descubren automáticamente
//        JettraRestServer.register(server, AuthController.class);
        server.start();

    }

}
