package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Relations.ProductsRelations.ColoursWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ColoursWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.ColoursWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ColoursWithWarehouseItems;

import java.util.List;

@Dao
public interface ColoursDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertColours(Colours... colours);

    @Delete
    void deleteColour(Colours colour);

    @Query("SELECT * FROM colours")
    LiveData<List<Colours>> getAllColours();

    @Transaction
    @Query("SELECT * FROM colours")
    LiveData<List<ColoursWithWarehouseItems>> getColoursWithWarehouseItems();

    @Transaction
    @Query("SELECT * FROM colours")
    LiveData<List<ColoursWithProducts>> getColoursWithProducts();

    @Transaction
    @Query("SELECT * FROM colours")
    LiveData<List<ColoursWithPurchases>> getColoursWithPurchases();

    @Transaction
    @Query("SELECT * FROM colours")
    LiveData<List<ColoursWithSales>> getColoursWithSales();

    @Query("SELECT * FROM colours WHERE colours = :colourName")
    LiveData<Colours> findExactColour(String colourName);


}
