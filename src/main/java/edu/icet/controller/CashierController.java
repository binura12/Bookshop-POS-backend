package edu.icet.controller;

import edu.icet.dto.Cashier;
import edu.icet.service.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cashier")
@RequiredArgsConstructor
@CrossOrigin
public class CashierController {

    final CashierService service;

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
}