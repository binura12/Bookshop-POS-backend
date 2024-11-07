package edu.icet.repository;

import edu.icet.entity.CashierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashierRepository extends JpaRepository<CashierEntity, String> {
    Optional<CashierEntity> findByEmailAndPassword(String email, String hashedPassword);
    Optional<CashierEntity> findFirstByOrderByIdDesc();
    Optional<CashierEntity> findByEmail(String email);
    void deleteById(String id);
    Iterable<Object> findAllByIsValidTrue();
    Iterable<Object> findAllByIsValidFalse();
}
