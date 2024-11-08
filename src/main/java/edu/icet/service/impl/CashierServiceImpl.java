package edu.icet.service.impl;

import edu.icet.dto.Cashier;
import edu.icet.entity.CashierEntity;
import edu.icet.repository.CashierRepository;
import edu.icet.service.CashierService;
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
public class CashierServiceImpl implements CashierService {

    private final CashierRepository repository;
    private final ModelMapper mapper;
    private final EncryptionUtil encryptionUtil;

    @Override
    public void addCashier(Cashier cashier) {
        try {
            //generate Id
            String nextId = generateNextCashierId();
            cashier.setId(nextId);

            //set Assigned date
            cashier.setAssignedDate(LocalDate.now());

            //set isValid to true
            cashier.setValid(true);

            //password encrypt
            cashier.setPassword(encryptionUtil.md5Hash(cashier.getPassword()));
            repository.save(mapper.map(cashier, CashierEntity.class));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cashier> getAllActiveCashiers() {
        List<Cashier> cashierArrayList = new ArrayList<>();
        repository.findAllByIsValidTrue().forEach(cashierEntity -> {
            cashierArrayList.add(mapper.map(cashierEntity, Cashier.class));
        });
        return cashierArrayList;
    }

    @Override
    public List<Cashier> getAllRemovedCashiers() {
        List<Cashier> cashierArrayList = new ArrayList<>();
        repository.findAllByIsValidFalse().forEach(cashierEntity -> {
            cashierArrayList.add(mapper.map(cashierEntity, Cashier.class));
        });
        return cashierArrayList;
    }

    @Override
    public void updateCashierById(Cashier cashier) {
        try {
            cashier.setPassword(encryptionUtil.md5Hash(cashier.getPassword()));
            repository.save(mapper.map(cashier, CashierEntity.class));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cashier> findByEmailAndPassword(String email, String password) {
        try {
            String hashedPassword = encryptionUtil.md5Hash(password);
            Optional<CashierEntity> cashierEntity = repository.findByEmailAndPassword(email, hashedPassword);
            return cashierEntity.map(entity -> mapper.map(entity, Cashier.class));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cashier searchCashierByEmail(String email) {
        return mapper.map(repository.findByEmail(email), Cashier.class);
    }

    @Override
    public boolean deactivateCashier(String id) {
        Optional<CashierEntity> optionalCashier = repository.findById(id);
        if (optionalCashier.isPresent()) {
            CashierEntity cashierEntity = optionalCashier.get();
            cashierEntity.setResignedDate(LocalDate.now());
            cashierEntity.setValid(false);
            repository.save(cashierEntity);
            return true;
        }
        return false;
    }

    private String generateNextCashierId() {
        Optional<CashierEntity> lastCashier = repository.findFirstByOrderByIdDesc();

        if (lastCashier.isEmpty()) {
            return "C0001";
        }
        String lastId = lastCashier.get().getId();
        int currentNumber = Integer.parseInt(lastId.substring(1));
        return String.format("C%04d", currentNumber + 1);
    }
}
