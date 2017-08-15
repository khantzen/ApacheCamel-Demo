package com.kevin.demo.camel.common.repository;

import com.kevin.demo.camel.common.model.db.LogMessage;
import com.kevin.demo.camel.common.utils.Connection;
import org.hibernate.Session;

public class LogRepository {

    public void storeLog(LogMessage log) {
        try (Session db = Connection.GetHibernateSessionFactory().openSession()) {
            db.getTransaction().begin();
            db.save(log);
            db.getTransaction().commit();
        }
    }
}
