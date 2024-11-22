package edu.icet.controller;

import edu.icet.dto.Admin;
import edu.icet.service.AdminService;
import edu.icet.service.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    final AdminService service;
    private final EmailService emailService;

    @PostMapping("/add-admin")
    public void addAdmin(@RequestBody Admin admin) {
        service.addAdmin(admin);
    }

    @GetMapping("/get-active-admins")
    public List<Admin> getAllActiveAdmins() {
        return service.getAllActiveAdmins();
    }

    @GetMapping("/get-removed-admins")
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
    public void updateAdmin(@RequestBody Admin admin) {
        service.updateAdminById(admin);
    }

    @GetMapping("/check-by-email/{email}")
    public ResponseEntity<?> adminCheck(@PathVariable String email) {
        try {
            Admin admin = service.searchAdminByEmail(email);
            if (admin != null) {
                String otp = emailService.generateOTP();
                emailService.sendOTPEmail(email, otp);
                return ResponseEntity.ok(Map.of(
                    "exists", true,
                    "otp", otp
                ));
            }
            return ResponseEntity.ok(Map.of("exists", false));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@RequestParam String email, @RequestParam String newPassword) {
        boolean updated = service.updatePasswordByEmail(email, newPassword);
        if (updated) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }
}