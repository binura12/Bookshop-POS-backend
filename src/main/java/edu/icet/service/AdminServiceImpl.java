package edu.icet.service;

import edu.icet.dto.Admin;
import edu.icet.entity.AdminEntity;
import edu.icet.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository repository;

    private final ModelMapper mapper;

    @Override
    public void addAdmin(Admin admin) {
        repository.save(mapper.map(admin, AdminEntity.class));
    }

    @Override
    public List<Admin> getAllAdmins() {
        return List.of();
    }

    @Override
    public void deleteAdmin(Integer id) {

    }

    @Override
    public void updateAdminById(Admin admin) {

    }

    @Override
    public Admin searchAdminById(Integer id) {
        return null;
    }
}
