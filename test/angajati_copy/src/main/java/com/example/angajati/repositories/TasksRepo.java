package com.example.angajati.repositories;

import org.hibernate.SessionFactory;

public class TasksRepo {
    SessionFactory sessionFactory;

    public TasksRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
