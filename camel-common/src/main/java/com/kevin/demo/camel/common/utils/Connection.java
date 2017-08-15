package com.kevin.demo.camel.common.utils;

import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.model.db.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Connection  {
    private static SessionFactory sf;

    public static SessionFactory GetHibernateSessionFactory() {

        if (sf == null) {

            try {
                ConfigManager config = ConfigManager.getInstance();

                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .applySettings(config.getProperties()).build();
                return new MetadataSources(registry)
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(LogMessage.class)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Throwable cause) {
                System.err.println("Initial SessionFactory creation failed: " + cause);
                throw new ExceptionInInitializerError(cause);
            }

        }


        return sf;
    }
}
