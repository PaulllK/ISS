package com.example.angajati.services;

import com.example.angajati.controllers.BossController;
import com.example.angajati.controllers.EmployeesController;
import com.example.angajati.domain.DTOs.UserDTO;
import com.example.angajati.domain.Task;
import com.example.angajati.domain.User;
import com.example.angajati.repositories.TasksRepo;
import com.example.angajati.repositories.UsersRepo;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private List<UserDTO> presentEmployees = new ArrayList<>();
    private UsersRepo usersRepo;
    private TasksRepo tasksRepo;
    private BossController bossController;
    private List<EmployeesController> employeesControllers = new ArrayList<>();

    public Service(UsersRepo usersRepo, TasksRepo tasksRepo) {
        this.usersRepo = usersRepo;
        this.tasksRepo = tasksRepo;
    }

    public TasksRepo getTasksRepo() {
        return tasksRepo;
    }

    public void setTasksRepo(TasksRepo tasksRepo) {
        this.tasksRepo = tasksRepo;
    }

    public UsersRepo getUsersRepo() {
        return usersRepo;
    }

    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public BossController getBossController() {
        return bossController;
    }

    public void setBossController(BossController bossController) {
        this.bossController = bossController;
    }

    public List<EmployeesController> getEmployeesControllers() {
        return employeesControllers;
    }

    public void setEmployeesControllers(List<EmployeesController> employeesControllers) {
        this.employeesControllers = employeesControllers;
    }

    public void addEmployeeController(EmployeesController employeesController) {
        employeesControllers.add(employeesController);
    }

    public User findUserByData(String username, String password) {
        return usersRepo.findByData(username, password);
    }

    public void notifyBossOfPresence(User user, String time) {
        UserDTO employee = new UserDTO(user.getUsername(), time);
        presentEmployees.add(employee);
        bossController.presencesChanged(presentEmployees);
    }

    public void notifyBossOfEmployeeLoggingOf(User user) {
        // username is unique
        presentEmployees.removeIf(employee -> employee.getUsername().equals(user.getUsername()));
        bossController.presencesChanged(presentEmployees);
    }

    public void notifyEmployeeOfTask(UserDTO selectedEmployee, Task task) {
        EmployeesController selectedEmployeeController = null;

        for (int i = 0; i < employeesControllers.size(); i++) {
            if (employeesControllers.get(i).getUser().getUsername().equals(selectedEmployee.getUsername())) {
                selectedEmployeeController = employeesControllers.get(i);
                break;
            }
        }

        selectedEmployeeController.taskReceived(task);
    }
}
