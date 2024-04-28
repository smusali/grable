package org.esi.grable.order_service.orders.service;

import lombok.extern.slf4j.Slf4j;
import org.esi.grable.order_service.orders.model.Order;
import org.esi.grable.order_service.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll()
                .forEach(orders::add);
        return orders;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void addOrder(Order order) {
        order.setTimestamp(Instant.now());
        orderRepository.save(order);
        log.info("Order {} is added to the Database", order.getId());
    }

    public void updateOrder(Long id, Order order) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = Order.builder()
                    .id(id)
                    .status(order.getStatus())
                    .restaurantId(order.getRestaurantId())
                    .tableId(order.getTableId())
                    .dinerId(order.getDinerId())
                    .timestamp(Instant.now())
                    .build();
            orderRepository.save(existingOrder);
            log.info("Order {} is updated in the Database", existingOrder.getId());
        } else {
            String errorMessage = String.format("Order with ID %s not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void deleteOrder(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
            log.info("Order {} is deleted from the Database", id);
        } else {
            String errorMessage = String.format("Order with ID %d not found", id);
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
