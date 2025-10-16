package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import projecterp.Database.Entities.Sales;

import java.sql.Date;
import java.util.List;

@Dao
public interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSales(Sales... sales);

    @Delete
    void deleteSale(Sales sale);

    @Query("SELECT * FROM sales")
    LiveData<List<Sales>> getAllSales();

    @Query("SELECT SUM(totalIncome) FROM sales WHERE date BETWEEN :fromDate AND :toDate")
    LiveData<Double> getTotalSalesBetweenDates(String fromDate, String toDate);

    @Query("SELECT DISTINCT CAST(substr(date, 1, 4) AS INTEGER) AS year FROM sales ORDER BY year DESC")
    LiveData<List<Integer>> getSaleYears();

    @Query("SELECT * FROM sales WHERE date BETWEEN :fromDate AND :toDate ORDER BY date DESC")
    LiveData<List<Sales>> getSalesBetweenDates(String fromDate, String toDate);


}
