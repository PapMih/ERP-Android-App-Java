package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.StoreLocationsDao;
import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.StoreLocations;
import projecterp.Database.Entities.Relations.ProductsRelations.StoreLocationsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.StoreLocationsWithSales;

public class StoreLocationsRepository {

    private final StoreLocationsDao mStoreLocationsDao;
    private final LiveData<List<StoreLocations>> mAllStoreLocations;

    public StoreLocationsRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mStoreLocationsDao = db.storeLocationsDao();
        mAllStoreLocations = mStoreLocationsDao.getAllStoreLocations();
    }

    // Returns all store locations
    public LiveData<List<StoreLocations>> getAllStoreLocations() {
        return mAllStoreLocations;
    }

    // Inserts one or more store locations
    public void insertStoreLocations(StoreLocations... storeLocations) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mStoreLocationsDao.insertStoreLocations(storeLocations);
        });
    }

    // Deletes a specific store location
    public void deleteStoreLocation(StoreLocations storeLocation) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mStoreLocationsDao.deleteStoreLocation(storeLocation);
        });
    }

    // Returns store locations with their associated products
    public LiveData<List<StoreLocationsWithProducts>> getAllStoreLocationsWithProducts() {
        return mStoreLocationsDao.getStoreLocationsWithProducts();
    }

    // Returns store locations with their associated sales
    public LiveData<List<StoreLocationsWithSales>> getAllStoreLocationsWithSales() {
        return mStoreLocationsDao.getStoreLocationsWithSales();
    }

    public LiveData<StoreLocations> findExactStoreLocation(String storeLocation) {
        return mStoreLocationsDao.findExactStoreLocation(storeLocation);
    }
}
