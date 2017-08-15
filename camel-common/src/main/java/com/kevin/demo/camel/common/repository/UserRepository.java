package com.kevin.demo.camel.common.repository;

import com.kevin.demo.camel.common.model.db.User;
import com.kevin.demo.camel.common.utils.Connection;
import org.hibernate.Session;

public class UserRepository {

    public void storeUser(User u) {
        try (Session db = Connection.GetHibernateSessionFactory().openSession()) {
            db.getTransaction().begin();
            db.save(u);
            db.getTransaction().commit();
        }
    }
}
