package com.example.incidentmanagement.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String pinCode;
    private String city;
    private String country;
    private String password;
}