package org.esi.grable.order_service.orders.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esi.grable.order_service.orders.model.Order;
import org.esi.grable.order_service.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "CRUD Operations for Orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrders(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        orderService.updateOrder(id, order);
        return ResponseEntity.ok().body("Order updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().body("Order deleted successfully.");
    }

}
