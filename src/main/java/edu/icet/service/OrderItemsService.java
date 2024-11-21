package edu.icet.service;

import edu.icet.dto.OrderItems;

import java.util.List;

public interface OrderItemsService {
    List<OrderItems> getOrderItems(String orderId);
}
