package edu.icet.service;

import edu.icet.dto.Admin;

import java.util.List;

public interface AdminService {
    void addAdmin(Admin admin);
    List<Admin> getAllAdmins();
    void deleteAdmin(Integer id);
    void updateAdminById(Admin admin);
    Admin searchAdminById(Integer id);
}
