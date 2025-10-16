package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.WarehouseItemsDao;
import projecterp.Database.Entities.WarehouseItems;

public class WarehouseItemsRepository {

    private final WarehouseItemsDao mWarehouseItemsDao;
    private final LiveData<List<WarehouseItems>> mAllWarehouseItems;

    public WarehouseItemsRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mWarehouseItemsDao = db.warehouseItemsDao();
        mAllWarehouseItems = mWarehouseItemsDao.getAllWarehouseItems();
    }

    // Returns all warehouse items
    public LiveData<List<WarehouseItems>> getAllWarehouseItems() {
        return mAllWarehouseItems;
    }

    // Inserts one or more warehouse items
    public void insertWarehouseItems(WarehouseItems... items) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mWarehouseItemsDao.insertWarehouseItems(items);
        });
    }

    // Updates a specific warehouse item
    public void updateWarehouseItems(WarehouseItems item) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mWarehouseItemsDao.updateWarehouseItem(item);
        });
    }

    // Deletes a specific warehouse item
    public void deleteWarehouseItem(WarehouseItems item) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mWarehouseItemsDao.deleteWarehouseItem(item);
        });
    }

    // Returns warehouse items filtered by colour
    public LiveData<List<WarehouseItems>> getWarehouseItemsByColour(String colour) {
        return mWarehouseItemsDao.getWarehouseItemsByColour(colour);
    }

    // Returns warehouse items filtered by size
    public LiveData<List<WarehouseItems>> getWarehouseItemsBySize(String size) {
        return mWarehouseItemsDao.getWarehouseItemsBySize(size);
    }

    // Returns warehouse items filtered by category name
    public LiveData<List<WarehouseItems>> getWarehouseItemsByCategory(String category) {
        return mWarehouseItemsDao.getWarehouseItemsByCategory(category);
    }

    // Increases the amount of a specific warehouse item
    public void increaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToAdd) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mWarehouseItemsDao.increaseWarehouseItemAmount(colour, size, categoryName, amountToAdd);
        });
    }

    // Decreases the amount of a specific warehouse item
    public void decreaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToSubtract) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mWarehouseItemsDao.decreaseWarehouseItemAmount(colour, size, categoryName, amountToSubtract);
        });
    }

    //Finds a warehouse item
    public LiveData<WarehouseItems> findExactWarehouseItem(String colour, String size, String category) {
        return mWarehouseItemsDao.findWarehouseItem(colour, size, category);
    }

}
