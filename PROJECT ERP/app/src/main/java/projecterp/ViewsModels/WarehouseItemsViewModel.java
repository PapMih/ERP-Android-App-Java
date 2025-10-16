package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.WarehouseItems;
import projecterp.Repositories.WarehouseItemsRepository;

public class WarehouseItemsViewModel extends AndroidViewModel {

    private final WarehouseItemsRepository mRepository;
    private final LiveData<List<WarehouseItems>> mAllWarehouseItems;

    public WarehouseItemsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WarehouseItemsRepository(application);
        mAllWarehouseItems = mRepository.getAllWarehouseItems();
    }

    // Returns all warehouse items
    public LiveData<List<WarehouseItems>> getAllWarehouseItems() {
        return mAllWarehouseItems;
    }

    // Inserts one or more warehouse items
    public void insertWarehouseItems(WarehouseItems... items) {
        mRepository.insertWarehouseItems(items);
    }

    // Deletes a specific warehouse item
    public void deleteWarehouseItem(WarehouseItems item) {
        mRepository.deleteWarehouseItem(item);
    }

    // Updates a specific warehouse item
    public void updateWarehouseItems(WarehouseItems item) {
        mRepository.updateWarehouseItems(item);
    }

    // Returns warehouse items filtered by colour
    public LiveData<List<WarehouseItems>> getWarehouseItemsByColour(String colour) {
        return mRepository.getWarehouseItemsByColour(colour);
    }

    // Returns warehouse items filtered by size
    public LiveData<List<WarehouseItems>> getWarehouseItemsBySize(String size) {
        return mRepository.getWarehouseItemsBySize(size);
    }

    // Returns warehouse items filtered by category name
    public LiveData<List<WarehouseItems>> getWarehouseItemsByCategory(String category) {
        return mRepository.getWarehouseItemsByCategory(category);
    }

    // Increases the amount of a specific warehouse item
    public void increaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToAdd) {
        mRepository.increaseWarehouseItemAmount(colour, size, categoryName, amountToAdd);
    }

    // Decreases the amount of a specific warehouse item
    public void decreaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToSubtract) {
        mRepository.decreaseWarehouseItemAmount(colour, size, categoryName, amountToSubtract);
    }

    public LiveData<WarehouseItems> findExactWarehouseItem(String colour, String size, String category) {
        return mRepository.findExactWarehouseItem(colour, size, category);
    }

}
