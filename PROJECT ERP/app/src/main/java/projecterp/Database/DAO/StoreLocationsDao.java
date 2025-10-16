package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.Relations.ProductsRelations.StoreLocationsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.StoreLocationsWithSales;
import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.StoreLocations;

import java.util.List;

@Dao
public interface StoreLocationsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertStoreLocations(StoreLocations... storeLocations);

    @Delete
    void deleteStoreLocation(StoreLocations storeLocation);

    @Query("SELECT * FROM store_locations")
    LiveData<List<StoreLocations>> getAllStoreLocations();

    @Transaction
    @Query("SELECT * FROM store_locations")
    LiveData<List<StoreLocationsWithProducts>> getStoreLocationsWithProducts();

    @Transaction
    @Query("SELECT * FROM store_locations")
    LiveData<List<StoreLocationsWithSales>> getStoreLocationsWithSales();

    @Query("SELECT * FROM store_locations WHERE storeLocation = :StoreLocations")
    LiveData<StoreLocations> findExactStoreLocation(String StoreLocations);
}
