package com.example.angajati.controllers;

import com.example.angajati.domain.DTOs.UserDTO;
import com.example.angajati.domain.Task;
import com.example.angajati.domain.User;
import com.example.angajati.services.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeesController {
    private Stage currentStage;
    private User user;
    private Service srv;

    @FXML
    private TextField timeTextField;

    @FXML
    private VBox employeesVBox;
    @FXML
    private HBox presenceForm;

    private ObservableList<Task> modelTasks = FXCollections.observableArrayList();

    @FXML
    private TableView tableViewTasks;
    @FXML
    private TableColumn tableColumnTaskDescription;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setData(Stage currentStage, User user, Service srv) {
        this.currentStage = currentStage;
        this.user = user;
        this.srv = srv;
        srv.addEmployeeController(this);
        currentStage.setOnCloseRequest(event -> {removePresence();});
    }

    @FXML
    public void initialize () {
        tableColumnTaskDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));

        tableViewTasks.setItems(modelTasks);
    }

    public void sendPresence(ActionEvent actionEvent) {
        String time = timeTextField.getText();
        timeTextField.setText("");
        employeesVBox.getChildren().remove(presenceForm);
        srv.notifyBossOfPresence(user, time);
    }

    private void removePresence() {
        srv.notifyBossOfEmployeeLoggingOf(user);
    }

    public void taskReceived(Task task) {
        modelTasks.add(task);
    }
}
