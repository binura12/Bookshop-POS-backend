package edu.icet.repository;

import edu.icet.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {
    Optional<AdminEntity> findByEmailAndPassword(String email, String hashedPassword);
    Optional<AdminEntity> findFirstByOrderByIdDesc();
    Optional<AdminEntity> findByEmail(String email);
    void deleteById(String id);
    Iterable<Object> findAllByIsValidTrue();
    Iterable<Object> findAllByIsValidFalse();
}
