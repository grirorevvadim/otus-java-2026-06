package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.server.WebServer;

public class WebServerDemo {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final int WEB_SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        new MigrationsExecutorFlyway(
                        configuration.getProperty("hibernate.connection.url"),
                        configuration.getProperty("hibernate.connection.username"),
                        configuration.getProperty("hibernate.connection.password"))
                .executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var dbServiceClient = new DbServiceClientImpl(
                new TransactionManagerHibernate(sessionFactory),
                new DataTemplateHibernate<>(Client.class),
                new MyCache<>());

        var login = System.getenv().getOrDefault("ADMIN_LOGIN", "admin");
        var password = System.getenv().getOrDefault("ADMIN_PASSWORD", "admin");

        var webServer = new WebServer(WEB_SERVER_PORT, dbServiceClient, login, password);
        webServer.start();
        System.out.printf("%nСервер Jetty запущен: http://localhost:%d/%n", WEB_SERVER_PORT);
        System.out.println("Остановить: Ctrl+C");
        webServer.join();
    }
}
