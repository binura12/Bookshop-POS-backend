package edu.icet.service.impl;

import edu.icet.dto.OrderItems;
import edu.icet.dto.Orders;
import edu.icet.entity.ItemEntity;
import edu.icet.entity.OrderItemsEntity;
import edu.icet.entity.OrdersEntity;
import edu.icet.repository.ItemRepository;
import edu.icet.repository.OrderItemsRepository;
import edu.icet.repository.OrdersRepository;
import edu.icet.service.OrdersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    public final ItemRepository itemRepository;
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
        orderEntity.setOrderTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
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
        List<OrdersEntity> orders = orderRepository.findAllByIsReturnedFalse();
        return orders.stream()
                .map(entity -> mapper.map(entity, Orders.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> getAllInvalidOrders() {
        List<OrdersEntity> orders = orderRepository.findAllByIsReturnedTrue();
        return orders.stream()
                .map(entity -> mapper.map(entity, Orders.class))
                .collect(Collectors.toList());
    }

    @Override
    public void returnOrder(String orderId) {
        // Update order status
        OrdersEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setReturned(true);
        orderRepository.save(order);

        // Get order items and update their status
        List<OrderItemsEntity> orderItems = orderItemsRepository.findByOrderIdAndIsReturnedFalse(orderId);
        for (OrderItemsEntity item : orderItems) {
            // Update order item status
            item.setReturned(true);
            orderItemsRepository.save(item);

            // Restock the item
            ItemEntity itemEntity = itemRepository.findById(item.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            itemEntity.setQty(itemEntity.getQty() + item.getQuantity());
            itemRepository.save(itemEntity);
        }
    }
}
