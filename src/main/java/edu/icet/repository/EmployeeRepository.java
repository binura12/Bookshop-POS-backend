package edu.icet.repository;

import edu.icet.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    Optional<EmployeeEntity> findFirstByOrderByIdDesc();
    void deleteById(String id);
    Iterable<Object> findAllByIsValidTrue();
    Iterable<Object> findAllByIsValidFalse();
}
