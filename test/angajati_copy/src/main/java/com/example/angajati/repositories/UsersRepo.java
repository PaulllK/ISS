package com.example.angajati.repositories;

import com.example.angajati.domain.Task;
import com.example.angajati.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsersRepo {

    SessionFactory sessionFactory;

    public UsersRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findByData(String username, String password) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String queryString = "from User WHERE username = :username AND password = :password";
                Query query = session.createQuery(queryString);
                query.setParameter("username", username);
                query.setParameter("password", password);

                User user = (User) query.list().get(0);

                tx.commit();

                return user;
            } catch (RuntimeException ex) {
                System.err.println("SELECT error " + ex);
                if (tx != null)
                    tx.rollback();

                return null;
            }
        }
    }
}
