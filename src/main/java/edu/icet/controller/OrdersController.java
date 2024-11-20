package edu.icet.controller;

import edu.icet.dto.Orders;
import edu.icet.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService service;

    @GetMapping("/next-id")
    public ResponseEntity<String> getNextOrderId() {
        return ResponseEntity.ok(service.getNextOrderId());
    }

    @PostMapping("/save")
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
        return ResponseEntity.ok(service.saveOrder(order));
    }

    @GetMapping("/not-returned")
    public ResponseEntity<List<Orders>> getAllValidOrders() {
        return ResponseEntity.ok(service.getAllValidOrders());
    }

    @GetMapping("/returned")
    public ResponseEntity<List<Orders>> getAllInvalidOrders() {
        return ResponseEntity.ok(service.getAllInvalidOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
