package edu.icet.controller;

import edu.icet.dto.Supplier;
import edu.icet.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
@CrossOrigin
public class SupplierController {

    final SupplierService service;

    @PostMapping ("/add-supplier")
    public void addSupplier(@RequestBody Supplier supplier) {
        service.addSupplier(supplier);
    }

    @GetMapping("/all-active-suppliers")
    public List<Supplier> getAllActiveSuppliers() {
        return service.getAllActiveSupplier();
    }

    @DeleteMapping("/delete-supplier/{id}")
    public ResponseEntity<Boolean> deleteSupplierById(@PathVariable String id) {
        boolean deactivated = service.deactivateSupplier(id);
        return ResponseEntity.ok(deactivated);
    }

    @PutMapping("/update-supplier")
    public void updateSupplier(@RequestBody Supplier supplier){
        service.updateSupplierById(supplier);
    }

    @GetMapping("/suppliers-by-category/{category}")
    public List<Supplier> getSuppliersByCategory(@PathVariable String category) {
        return service.getSuppliersByCategory(category);
    }
}