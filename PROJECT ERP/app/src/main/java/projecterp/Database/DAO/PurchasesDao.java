package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Purchases;

import java.util.List;

@Dao
public interface PurchasesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertPurchase(Purchases... purchases);

    @Delete
    void deletePurchase(Purchases purchase);

    @Query("SELECT * FROM purchases")
    LiveData<List<Purchases>> getAllPurchases();

    @Query("SELECT SUM(TotalCostInt) FROM purchases WHERE date BETWEEN :fromDate AND :toDate")
    LiveData<Integer> getTotalPurchasesBetweenDates(String fromDate, String toDate);

    @Query("SELECT * FROM purchases WHERE categoryName = :category AND  size = :size AND  colour = :colour AND  date = :date")
    LiveData<Purchases> findExactPurchase( String colour, String size, String category, String date);


    @Query("SELECT DISTINCT strftime('%Y', date) AS year FROM purchases ORDER BY year DESC")
    LiveData<List<String>> getYears();

    @Query("SELECT * FROM purchases WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    LiveData<List<Purchases>> getPurchasesBetweenDates(String from, String to);


}
