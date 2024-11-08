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
@Table(name = "cashier")
public class CashierEntity {
    @Id
    @Column(length = 10)
    private String id;
    private String imagePath;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private Integer age;
    private String phoneNumber;
    private String address;
    private LocalDate assignedDate;
    private LocalDate resignedDate;
    private Integer orderCount;
    private boolean isValid;
}