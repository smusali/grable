package org.esi.grable.order_service.orderDetails.repository;

import org.esi.grable.order_service.orderDetails.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

    Optional<OrderDetail> findByIdAndOrderId(Long id, Long menuId);

}
