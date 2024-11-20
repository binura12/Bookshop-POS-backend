package edu.icet.repository;

import edu.icet.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {
    Optional<OrdersEntity> findFirstByOrderByOrderIdDesc();
    Object findAllByIsReturnedTrue();
    Object findAllByIsReturnedFalse();
}
