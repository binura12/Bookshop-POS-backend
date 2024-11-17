package edu.icet.repository;

import edu.icet.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {
    List<SupplierEntity> findByProductCategoryAndIsValidTrue(String category);
    Optional<SupplierEntity> findFirstByOrderByIdDesc();
    void deleteById(String id);
    Iterable<Object> findAllByIsValidTrue();
}
