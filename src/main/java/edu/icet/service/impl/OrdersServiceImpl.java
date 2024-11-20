package edu.icet.service.impl;

import edu.icet.dto.Orders;
import edu.icet.entity.OrderItemsEntity;
import edu.icet.entity.OrdersEntity;
import edu.icet.repository.OrderItemsRepository;
import edu.icet.repository.OrdersRepository;
import edu.icet.service.OrdersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ModelMapper mapper;

    @Override
    public String getNextOrderId() {
        Optional<OrdersEntity> lastOrder = orderRepository.findFirstByOrderByOrderIdDesc();

        if (lastOrder.isEmpty()) {
            return "ORD-0001";
        }
        String lastId = lastOrder.get().getOrderId();
        int lastNumber = Integer.parseInt(lastId.split("-")[1]);
        return String.format("ORD-%04d", lastNumber + 1);
    }

    @Override
    @Transactional
    public Orders saveOrder(Orders order) {
        // Save order
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setOrderId(order.getOrderId());
        orderEntity.setCusName(order.getCusName());
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setOrderTime(LocalTime.now());
        orderEntity.setReturned(false);
        orderRepository.save(orderEntity);

        // Save order items
        order.getOrderItems().forEach(item -> {
            OrderItemsEntity orderItem = new OrderItemsEntity();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setItemId(item.getItemId());
            orderItem.setItemName(item.getItemName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSubTotal(item.getSubTotal());
            orderItem.setTotal(item.getTotal());
            orderItem.setReturned(false);
            orderItemsRepository.save(orderItem);
        });
        return order;
    }

    @Override
    public List<Orders> getAllValidOrders() {
        return (List<Orders>) orderRepository.findAllByIsReturnedFalse();
    }

    @Override
    public List<Orders> getAllInvalidOrders() {
        return (List<Orders>) orderRepository.findAllByIsReturnedTrue();
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}
