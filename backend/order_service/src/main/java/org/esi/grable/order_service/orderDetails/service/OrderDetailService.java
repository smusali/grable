package org.esi.grable.order_service.orderDetails.service;

import lombok.extern.slf4j.Slf4j;
import org.esi.grable.order_service.orderDetails.model.OrderDetail;
import org.esi.grable.order_service.orderDetails.repository.OrderDetailRepository;
import org.esi.grable.order_service.orders.model.Order;
import org.esi.grable.order_service.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailRepository.findAll()
                .forEach(orderDetails::add);
        return orderDetails;
    }

    public OrderDetail getOrderDetail(Long id, Long orderId) {
        return orderDetailRepository.findByIdAndOrderId(id, orderId).orElse(null);
    }

    public void addOrderDetail(Long orderId, OrderDetail orderDetail) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderDetail.setOrderId(orderId);
            log.info("Order detail {} is added to the Database", orderDetail.getId());
            orderDetailRepository.save(orderDetail);
        } else {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
    }

    public void updateOrderDetail(Long id, OrderDetail orderDetail, Long orderId) {
        Optional<OrderDetail> existingOrderOptional = orderDetailRepository.findByIdAndOrderId(id, orderId);
        if (existingOrderOptional.isPresent()) {
            OrderDetail existingOrderDetail = OrderDetail.builder()
                    .id(id)
                    .orderId(orderDetail.getOrderId())
                    .menuItemId(orderDetail.getMenuItemId())
                    .quantity(orderDetail.getQuantity())
                    .build();
            orderDetailRepository.save(existingOrderDetail);
            log.info("Order {} is updated in the Database", existingOrderDetail.getId());
        } else {
            String errorMessage = String.format("Order detail with ID %s not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void deleteOrderDetail(Long id, Long orderId) {
        if (orderDetailRepository.findByIdAndOrderId(id, orderId).isPresent()) {
            orderDetailRepository.deleteById(id);
            log.info("Order {} is deleted from the Database", id);
        } else {
            String errorMessage = String.format("Order with ID %d not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
