package edu.icet.controller;

import edu.icet.dto.Admin;
import edu.icet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    final AdminService service;

    @PostMapping ("/add-admin")
    public void addAdmin(@RequestBody Admin admin) {
        service.addAdmin(admin);
    }

    @GetMapping ("/get-active-admins")
    public List<Admin> getAllActiveAdmins() {
        return service.getAllActiveAdmins();
    }

    @GetMapping ("/get-removed-admins")
    public List<Admin> getAllRemovedAdmins() {
        return service.getAllRemovedAdmins();
    }

    @PostMapping("/search-admin")
    public ResponseEntity<Admin> login(@RequestParam String email, @RequestParam String password) {
        Optional<Admin> adminEntity = service.findByEmailAndPassword(email, password);
        return adminEntity
                .filter(Admin::isValid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search-by-email/{email}")
    public Admin getAdminByEmail(@PathVariable String email) {
        return service.searchAdminByEmail(email);
    }

    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<Boolean> deleteAdminById(@PathVariable String id) {
        boolean deactivated = service.deactivateAdmin(id);
        return ResponseEntity.ok(deactivated);
    }

    @PutMapping("/update-admin")
    public void updateAdmin(@RequestBody Admin admin){
        service.updateAdminById(admin);
    }
}
