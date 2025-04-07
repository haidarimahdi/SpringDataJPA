package com.example.SpringDataJPA.dto;

/**
 * Data Transfer Object (DTO) for Person entity.
 * This class is used to transfer data between the service layer and the controller layer.
 * It contains the person's first name and last name.
 */
public class PersonDTO {

    private String firstName;
    private String lastName;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
