package edu.icet.service.impl;

import edu.icet.dto.Supplier;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.SupplierRepository;
import edu.icet.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;
    private final ModelMapper mapper;

    @Override
    public void addSupplier(Supplier supplier) {
        //generate Id
        String nextId = generateNextSupplierId();
        supplier.setId(nextId);

        //set isValid to true
        supplier.setValid(true);
        repository.save(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public List<Supplier> getAllActiveSupplier() {
        List<Supplier> supplierArrayList = new ArrayList<>();
        repository.findAllByIsValidTrue().forEach(supplierEntity -> {
            supplierArrayList.add(mapper.map(supplierEntity, Supplier.class));
        });
        return supplierArrayList;
    }

    @Override
    public void updateSupplierById(Supplier supplier) {
        repository.save(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public boolean deactivateSupplier(String id) {
        Optional<SupplierEntity> optionalSupplier = repository.findById(id);
        if (optionalSupplier.isPresent()) {
            SupplierEntity supplierEntity = optionalSupplier.get();
            supplierEntity.setValid(false);
            repository.save(supplierEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Supplier> getSuppliersByCategory(String category) {
        return repository.findByProductCategoryAndIsValidTrue(category)
                .stream()
                .map(entity -> mapper.map(entity, Supplier.class))
                .collect(Collectors.toList());
    }

    private String generateNextSupplierId() {
        Optional<SupplierEntity> lastSupplier = repository.findFirstByOrderByIdDesc();

        if (lastSupplier.isEmpty()) {
            return "S0001";
        }
        String lastId = lastSupplier.get().getId();
        int currentNumber = Integer.parseInt(lastId.substring(1));
        return String.format("S%04d", currentNumber + 1);
    }
}
