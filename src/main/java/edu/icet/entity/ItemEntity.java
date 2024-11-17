package edu.icet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @Column(length = 10)
    public String id;
    public String imagePath;
    public String itemName;
    public String itemDesc;
    public Double price;
    public Integer qty;
    public String category;
    public String supplierId;
    public String supplierName;
    public boolean isValid;
}
