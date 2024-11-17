package edu.icet.repository;

import edu.icet.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, String> {
    Iterable<Object> findAllByIsValidTrue();
    List<ItemEntity> findByCategoryAndIsValidTrue(String category);
    Optional<ItemEntity> findFirstByOrderByIdDesc();
}
