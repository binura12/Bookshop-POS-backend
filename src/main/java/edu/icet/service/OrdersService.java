package edu.icet.service;

import edu.icet.dto.OrderItems;
import edu.icet.dto.Orders;

import java.util.List;

public interface OrdersService {
    String getNextOrderId();
    Orders saveOrder(Orders order);
    List<Orders> getAllValidOrders();
    List<Orders> getAllInvalidOrders();
    void returnOrder(String orderId);
}
