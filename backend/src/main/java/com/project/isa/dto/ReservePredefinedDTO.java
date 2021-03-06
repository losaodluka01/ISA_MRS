package com.project.isa.dto;

public class ReservePredefinedDTO {
    private Long id;
    private int version;
    private String username;


    public ReservePredefinedDTO() {
    }

    public ReservePredefinedDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
