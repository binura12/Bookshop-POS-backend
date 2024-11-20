package edu.icet.service.impl;

import edu.icet.dto.Item;
import edu.icet.dto.StockUpdate;
import edu.icet.entity.ItemEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.repository.ItemRepository;
import edu.icet.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final ModelMapper mapper;

    @Override
    public void addItem(Item item) {
        //generate Id
        String nextId = generateNextItemId();
        item.setId(nextId);

        //set isValid to true
        item.setValid(true);
        repository.save(mapper.map(item, ItemEntity.class));
    }

    private String generateNextItemId() {
        Optional<ItemEntity> lastItem = repository.findFirstByOrderByIdDesc();

        if (lastItem.isEmpty()) {
            return "I0001";
        }
        String lastId = lastItem.get().getId();
        int currentNumber = Integer.parseInt(lastId.substring(1));
        return String.format("I%04d", currentNumber + 1);
    }

    @Override
    public List<Item> getAllActiveItems() {
        List<Item> itemArrayList = new ArrayList<>();
        repository.findAllByIsValidTrue().forEach(itemEntity -> {
            itemArrayList.add(mapper.map(itemEntity, Item.class));
        });
        return itemArrayList;
    }

    @Override
    public boolean deactivateItem(String id) {
        Optional<ItemEntity> optionalItem = repository.findById(id);
        if (optionalItem.isPresent()) {
            ItemEntity itemEntity = optionalItem.get();
            itemEntity.setValid(false);
            repository.save(itemEntity);
            return true;
        }
        return false;
    }

    @Override
    public void updateItemById(Item item) {
        repository.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        return repository.findByCategoryAndIsValidTrue(category)
               .stream()
               .map(entity -> mapper.map(entity, Item.class))
               .collect(Collectors.toList());
    }

    @Override
    public void updateStock(List<StockUpdate> updates) {
        for (StockUpdate update : updates) {
            ItemEntity item = repository.findById(update.getItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            item.setQty(item.getQty() - update.getQuantity());
            repository.save(item);
        }
    }
}
