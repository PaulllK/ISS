package com.example.angajati;

import com.example.angajati.controllers.LogInController;
import com.example.angajati.repositories.TasksRepo;
import com.example.angajati.repositories.UsersRepo;
import com.example.angajati.services.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;

public class Main extends Application {

    static SessionFactory sessionFactory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(Main.class.getResource("CSS/dark.css").toExternalForm());

        primaryStage.setTitle("Log in");
        primaryStage.setScene(scene);

        initializeSessionFactory();
        UsersRepo usersRepo = new UsersRepo(sessionFactory);
        TasksRepo tasksRepo = new TasksRepo(sessionFactory);

        Service srv = new Service(usersRepo, tasksRepo);

        LogInController logInController = fxmlLoader.getController();
        logInController.setSrv(srv);

        primaryStage.show();
    }

    static void initializeSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }
}
