package ru.otus.server;

import jakarta.servlet.DispatcherType;
import java.util.EnumSet;
import org.eclipse.jetty.ee10.servlet.FilterHolder;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import ru.otus.crm.service.DBServiceClient;

public class WebServer {

    private final Server server;

    public WebServer(int port, DBServiceClient dbServiceClient, String adminLogin, String adminPassword) {
        server = new Server(port);

        var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new StaticResourceServlet()), "/static/*");
        context.addServlet(new ServletHolder(new LoginServlet(adminLogin, adminPassword)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        context.addServlet(new ServletHolder(new RootServlet()), "/");
        context.addServlet(new ServletHolder(new AdminClientsServlet(dbServiceClient)), "/admin/clients");

        context.addFilter(new FilterHolder(new AuthFilter()), "/admin/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(context);
    }

    public void start() throws Exception {
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
