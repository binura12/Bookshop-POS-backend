package edu.icet.controller;

import edu.icet.dto.Cashier;
import edu.icet.service.CashierService;
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
@RequestMapping("/cashier")
@RequiredArgsConstructor
@CrossOrigin
public class CashierController {

    final CashierService service;
    private final EmailService emailService;

    @PostMapping ("/add-cashier")
    public void addCashier(@RequestBody Cashier cashier) {
        service.addCashier(cashier);
    }

    @GetMapping("/all-active-cashiers")
    public List<Cashier> getAllActiveCashiers() {
        return service.getAllActiveCashiers();
    }

    @GetMapping("/all-removed-cashiers")
    public List<Cashier> getAllRemovedCashiers() {
        return service.getAllRemovedCashiers();
    }

    @PostMapping("/search-cashier")
    public ResponseEntity<Cashier> login(@RequestParam String email, @RequestParam String password) {
        Optional<Cashier> cashierEntity = service.findByEmailAndPassword(email, password);
        return cashierEntity
                .filter(Cashier::isValid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search-by-email/{email}")
    public Cashier getCashierByEmail(@PathVariable String email) {
        return service.searchCashierByEmail(email);
    }

    @DeleteMapping("/delete-cashier/{id}")
    public ResponseEntity<Boolean> deleteCashierById(@PathVariable String id) {
        boolean deactivated = service.deactivateCashier(id);
        return ResponseEntity.ok(deactivated);
    }

    @PutMapping("/update-cashier")
    public void updateCashier(@RequestBody Cashier cashier){
        service.updateCashierById(cashier);
    }

    @GetMapping("/check-by-email/{email}")
    public ResponseEntity<?> cashierCheck(@PathVariable String email) {
        try {
            Cashier cashier = service.searchCashierByEmail(email);
            if (cashier != null) {
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