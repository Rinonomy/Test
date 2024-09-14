package org.example;

public class SeccessRegister {
    private Integer id;
    private String token;

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public SeccessRegister() {
    }

    public SeccessRegister(Integer id, String token) {
        this.id = id;
        this.token = token;
    }
}
