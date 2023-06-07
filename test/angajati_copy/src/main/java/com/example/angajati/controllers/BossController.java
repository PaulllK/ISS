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
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BossController {

    private User user;
    private Service srv;

    private ObservableList<UserDTO> modelCharityCasesAndSums = FXCollections.observableArrayList();

    @FXML
    private TableView<UserDTO> tableViewEmployees;
    @FXML
    private TableColumn<UserDTO, String> tableColumnUsername;
    @FXML
    private TableColumn<UserDTO, String> tableColumnTime;

    @FXML
    private TextArea taskTextArea;

    public void setData(User user, Service srv) {
        this.user = user;
        this.srv = srv;
        srv.setBossController(this);
    }

    @FXML
    public void initialize() {
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("username"));
        tableColumnTime.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("time"));

        tableViewEmployees.setItems(modelCharityCasesAndSums);
    }


    public void presencesChanged(List<UserDTO> presentEmployees) {
        modelCharityCasesAndSums.setAll(presentEmployees);
    }

    public void sendTask(ActionEvent actionEvent) {
        UserDTO selectedEmployee = tableViewEmployees.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            Task task = new Task(taskTextArea.getText());

            taskTextArea.setText("");

            srv.notifyEmployeeOfTask(selectedEmployee, task);
        }
    }
}
