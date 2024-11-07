package edu.icet.service;

import edu.icet.dto.Cashier;

import java.util.List;
import java.util.Optional;

public interface CashierService {
    void addCashier(Cashier cashier);
    List<Cashier> getAllActiveCashiers();
    List<Cashier> getAllRemovedCashiers();
    void updateCashierById(Cashier cashier);
    Optional<Cashier> findByEmailAndPassword(String email, String password);
    Cashier searchCashierByEmail(String email);
    boolean deactivateCashier(String id);
}
