package edu.icet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    @Column(length = 10)
    private String id;
    private String fullName;
    private String company;
    private String email;
    private String phoneNumber;
    private String productCategory;
    private String address;
    private boolean isValid;
}
