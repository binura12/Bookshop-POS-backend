package edu.icet.service.impl;

import edu.icet.dto.OrderItems;
import edu.icet.repository.OrderItemsRepository;
import edu.icet.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemsService {

    private final OrderItemsRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<OrderItems> getOrderItems(String orderId) {
        return repository.findByOrderIdAndIsReturnedFalse(orderId)
                .stream()
                .map(orderItemsEntity -> mapper.map(orderItemsEntity, OrderItems.class))
                .collect(Collectors.toList());
    }
}
