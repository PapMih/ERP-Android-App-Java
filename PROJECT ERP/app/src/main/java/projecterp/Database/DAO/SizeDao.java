package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import projecterp.Database.Entities.Relations.ProductsRelations.SizeWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.SizeWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.SizeWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ColoursWithWarehouseItems;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.SizeWithWarehouseItems;
import projecterp.Database.Entities.Size;

import java.util.List;

@Dao
public interface SizeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSizes(Size... sizes);

    @Delete
    void deleteSize(Size size);

    @Query("SELECT * FROM size")
    LiveData<List<Size>> getAllSizes();

    @Transaction
    @Query("SELECT * FROM size")
    LiveData<List<SizeWithWarehouseItems>> getSizesWithWarehouseItems();

    @Transaction
    @Query("SELECT * FROM size")
    LiveData<List<SizeWithProducts>> getSizesWithProducts();

    @Transaction
    @Query("SELECT * FROM size")
    LiveData<List<SizeWithPurchases>> getSizesWithPurchases();

    @Transaction
    @Query("SELECT * FROM size")
    LiveData<List<SizeWithSales>> getSizesWithSales();

    @Query("SELECT * FROM size WHERE size = :sizeName")
    LiveData<Size> findExactSize(String sizeName);
}
