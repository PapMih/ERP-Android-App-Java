package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.StoreLocations;
import projecterp.Database.Entities.Relations.ProductsRelations.StoreLocationsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.StoreLocationsWithSales;
import projecterp.Repositories.StoreLocationsRepository;

public class StoreLocationsViewModel extends AndroidViewModel {

    private final StoreLocationsRepository mRepository;
    private final LiveData<List<StoreLocations>> mAllStoreLocations;

    public StoreLocationsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new StoreLocationsRepository(application);
        mAllStoreLocations = mRepository.getAllStoreLocations();
    }

    // Returns all store locations
    public LiveData<List<StoreLocations>> getAllStoreLocations() {
        return mAllStoreLocations;
    }

    // Inserts one or more store locations
    public void insertStoreLocations(StoreLocations... storeLocations) {
        mRepository.insertStoreLocations(storeLocations);
    }

    // Deletes a specific store location
    public void deleteStoreLocation(StoreLocations storeLocation) {
        mRepository.deleteStoreLocation(storeLocation);
    }

    // Returns store locations with their associated products
    public LiveData<List<StoreLocationsWithProducts>> getAllStoreLocationsWithProducts() {
        return mRepository.getAllStoreLocationsWithProducts();
    }

    // Returns store locations with their associated sales
    public LiveData<List<StoreLocationsWithSales>> getAllStoreLocationsWithSales() {
        return mRepository.getAllStoreLocationsWithSales();
    }

    public LiveData<StoreLocations> findExactStoreLocation(String storeLocation) {
        return mRepository.findExactStoreLocation(storeLocation);
    }
}
