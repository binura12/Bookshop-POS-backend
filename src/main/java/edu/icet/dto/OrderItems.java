package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItems {
    private String itemId;
    private String itemName;
    private Integer quantity;
    private Double subTotal;
    private Double total;
    private boolean isReturned;
}
