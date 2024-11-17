package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
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
