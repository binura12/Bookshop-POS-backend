package edu.icet.service;

import edu.icet.dto.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void addAdmin(Admin admin);
    List<Admin> getAllActiveAdmins();
    List<Admin> getAllRemovedAdmins();
    void updateAdminById(Admin admin);
    Optional<Admin> findByEmailAndPassword(String email, String password);
    Admin searchAdminByEmail(String email);
    boolean deactivateAdmin(String id);
}
