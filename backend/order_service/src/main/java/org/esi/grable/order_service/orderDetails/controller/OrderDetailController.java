package org.esi.grable.order_service.orderDetails.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.esi.grable.order_service.orderDetails.model.OrderDetail;
import org.esi.grable.order_service.orderDetails.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders/{orderId}/orderDetails")
@Tag(name = "OrderDetail", description = "CRUD Operations for OrderDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public List<OrderDetail> getAllOrderDetails(@PathVariable Long orderId) {
        return orderDetailService.getAllOrderDetails().stream()
                .filter(orderDetail -> Objects.equals(orderDetail.getOrderId(), orderId))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDetail getOrderDetailById(@PathVariable Long orderId, @PathVariable Long id) {
        return orderDetailService.getOrderDetail(orderId, id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrderDetails(@PathVariable Long orderId, @RequestBody OrderDetail orderDetail) {
        orderDetailService.addOrderDetail(orderId, orderDetail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable Long orderId, @PathVariable Long id, @RequestBody OrderDetail orderDetail) {
        orderDetailService.updateOrderDetail(id, orderDetail, orderId);
        return ResponseEntity.ok().body("Order detail updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable Long orderId, @PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id, orderId);
        return ResponseEntity.ok().body("Order detail deleted successfully.");
    }

}
