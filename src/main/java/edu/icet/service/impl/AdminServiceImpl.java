package edu.icet.service.impl;

import edu.icet.dto.Admin;
import edu.icet.entity.AdminEntity;
import edu.icet.repository.AdminRepository;
import edu.icet.service.AdminService;
import edu.icet.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private final ModelMapper mapper;
    private final EncryptionUtil encryptionUtil;

    @Override
    public void addAdmin(Admin admin) {
        try {
            //generate Id
            String nextId = generateNextAdminId();
            admin.setId(nextId);

            //set current date
            admin.setAccCreatedDate(LocalDate.now());

            //set isValid to true
            admin.setValid(true);

            //password encrypt
            admin.setPassword(encryptionUtil.md5Hash(admin.getPassword()));
            repository.save(mapper.map(admin, AdminEntity.class));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateAdminById(Admin admin) {
        try {
            Optional<AdminEntity> existingAdmin = repository.findById(admin.getId());
            if (existingAdmin.isPresent()){
                AdminEntity adminEntity = existingAdmin.get();

                if (!adminEntity.getPassword().equals(admin.getPassword())) {
                    admin.setPassword(encryptionUtil.md5Hash(admin.getPassword()));
                }
                repository.save(mapper.map(admin, AdminEntity.class));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextAdminId() {
        Optional<AdminEntity> lastAdmin = repository.findFirstByOrderByIdDesc();

        if (lastAdmin.isEmpty()){
            return "A0001";
        }
        String lastId = lastAdmin.get().getId();
        int currentNumber = Integer.parseInt(lastId.substring(1));
        return String.format("A%04d", currentNumber + 1);
    }

    @Override
    public List<Admin> getAllActiveAdmins() {
        List<Admin> adminArrayList = new ArrayList<>();
        repository.findAllByIsValidTrue().forEach(adminEntity -> {
            adminArrayList.add(mapper.map(adminEntity, Admin.class));
        });
        return adminArrayList;
    }

    @Override
    public List<Admin> getAllRemovedAdmins() {
        List<Admin> adminArrayList = new ArrayList<>();
        repository.findAllByIsValidFalse().forEach(adminEntity -> {
            adminArrayList.add(mapper.map(adminEntity, Admin.class));
        });
        return adminArrayList;
    }

    @Override
    public Optional<Admin> findByEmailAndPassword(String email, String password) {
        try {
            String hashedPassword = encryptionUtil.md5Hash(password);
            Optional<AdminEntity> adminEntity = repository.findByEmailAndPassword(email, hashedPassword);
            return adminEntity.map(entity -> mapper.map(entity, Admin.class));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin searchAdminByEmail(String email) {
        return mapper.map(repository.findByEmail(email), Admin.class);
    }

    @Override
    public boolean deactivateAdmin(String id) {
        Optional<AdminEntity> optionalAdmin = repository.findById(id);
        if (optionalAdmin.isPresent()){
            AdminEntity adminEntity = optionalAdmin.get();
            adminEntity.setValid(false);
            repository.save(adminEntity);
            return true;
        }
        return false;
    }
}
