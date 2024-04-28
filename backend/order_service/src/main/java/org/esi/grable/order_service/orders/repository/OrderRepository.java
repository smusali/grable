package org.esi.grable.order_service.orders.repository;

import org.esi.grable.order_service.orders.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
