package com.example.angajati.domain.DTOs;

public class UserDTO {
    private String username;
    private String time;

    public UserDTO(String username, String time) {
        this.username = username;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
