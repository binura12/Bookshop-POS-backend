package edu.icet.service;

import edu.icet.dto.Supplier;

import java.util.List;

public interface SupplierService {
    void addSupplier(Supplier supplier);
    List<Supplier> getAllActiveSupplier();
    void updateSupplierById(Supplier supplier);
    boolean deactivateSupplier(String id);
}