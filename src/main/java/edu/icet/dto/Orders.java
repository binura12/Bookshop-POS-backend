package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private String orderId;
    private String cusName;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private LocalDate returnDate;
    private List<OrderItems> orderItems;
    private boolean isReturned;
}
