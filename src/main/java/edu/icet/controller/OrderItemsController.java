package edu.icet.controller;

import edu.icet.dto.OrderItems;
import edu.icet.dto.Orders;
import edu.icet.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@RequiredArgsConstructor
public class OrderItemsController {

    private final OrderItemsService service;

    @GetMapping("/order-items/{id}")
    public ResponseEntity<List<OrderItems>> getOrderItems(@PathVariable String id) {
        return ResponseEntity.ok(service.getOrderItems(id));
    }
}
