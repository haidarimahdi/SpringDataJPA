package com.example.SpringDataJPA.dto;

public class ProjectBasicInfoDTO {
    private final Integer id;
    private final String name;

    public ProjectBasicInfoDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }

}
