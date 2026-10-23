package ru.otus.core.repository;

import java.util.Arrays;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public final class HibernateUtils {

    private HibernateUtils() {}

    public static SessionFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
        StandardServiceRegistry serviceRegistry = createServiceRegistry(configuration);
        try {
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

            Metadata metadata = metadataSources.getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (RuntimeException e) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
            throw e;
        }
    }

    private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
    }
}
