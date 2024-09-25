package org.example;

public class Register {
    private String email;
    private String password;

    public Register() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
