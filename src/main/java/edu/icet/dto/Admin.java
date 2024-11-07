package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    private String id;
    private String imagePath;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private Integer age;
    private String phoneNumber;
    private String address;
    private LocalDate accCreatedDate;
    private boolean isValid;
}
