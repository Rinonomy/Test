package org.example;

public class UserTimeResponse extends UserTime {
    private String updatedAt;
    public UserTimeResponse() {
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public UserTimeResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }
}
