package edu.icet.repository;

import edu.icet.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Long> {
    List<OrderItemsEntity> findByOrderIdAndIsReturnedTrue(String orderId);
}
