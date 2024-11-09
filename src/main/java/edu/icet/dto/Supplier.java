package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    private String id;
    private String fullName;
    private String company;
    private String email;
    private String phoneNumber;
    private String productCategory;
    private String address;
    private boolean isValid;
}
