package com.example.angajati.controllers;

import com.example.angajati.Main;
import com.example.angajati.domain.User;
import com.example.angajati.services.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    private Service srv;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void setSrv(Service srv) {
        this.srv = srv;
    }

    public void logUserIn(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        try {
            User user = srv.findUserByData(username, password);

            usernameTextField.setText("");
            passwordTextField.setText("");

            if (user.getIsBoss() == 1)
                startBossSession(user);
            else
                startEmployeeSession(user);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void startBossSession(User user) {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader;

            fxmlLoader = new FXMLLoader(Main.class.getResource("views/boss-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("CSS/dark.css").toExternalForm());

            stage.setTitle(user.getUsername());
            stage.setScene(scene);

            BossController bossController = fxmlLoader.getController();
            bossController.setData(user, srv);

            stage.show();
        } catch (IOException exception) {
            System.out.println("Session start error!");
        }
    }

    private void startEmployeeSession(User user) {
        try {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader;

            fxmlLoader = new FXMLLoader(Main.class.getResource("views/employee-view.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("CSS/dark.css").toExternalForm());

            stage.setTitle(user.getUsername());
            stage.setScene(scene);

            EmployeesController employeesController = fxmlLoader.getController();
            employeesController.setData(stage, user, srv);

            stage.show();
        } catch (IOException exception) {
            System.out.println("Session start error!");
        }
    }
}
