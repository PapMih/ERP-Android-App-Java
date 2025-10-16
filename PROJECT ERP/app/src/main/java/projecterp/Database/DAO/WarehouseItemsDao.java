package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import projecterp.Database.Entities.WarehouseItems;

import java.util.List;

@Dao
public interface WarehouseItemsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT) // Abprt in orden not to have any
    void insertWarehouseItems(WarehouseItems... items);

    @Update
    void updateWarehouseItem(WarehouseItems item);

    @Delete
    void deleteWarehouseItem(WarehouseItems item);

    @Query("SELECT * FROM warehouse")
    LiveData<List<WarehouseItems>> getAllWarehouseItems();

    @Query("SELECT * FROM warehouse WHERE colours = :colour")
    LiveData<List<WarehouseItems>> getWarehouseItemsByColour(String colour); // Returns Warehouse Items of this colour

    @Query("SELECT * FROM warehouse WHERE size = :size")
    LiveData<List<WarehouseItems>> getWarehouseItemsBySize(String size); // Returns Warehouse Items of this size

    @Query("SELECT * FROM warehouse WHERE categoryName = :category")
    LiveData<List<WarehouseItems>> getWarehouseItemsByCategory(String category); // Returns Warehouse Items of this category

    @Query("UPDATE warehouse SET amount = amount + :amountToAdd WHERE colours = :colour AND size = :size AND categoryName = :categoryName")
    void increaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToAdd);

    @Query("UPDATE warehouse SET amount = amount - :amountToSubtract WHERE colours = :colour AND size = :size AND categoryName = :categoryName")
    void decreaseWarehouseItemAmount(String colour, String size, String categoryName, int amountToSubtract);

    @Query("SELECT * FROM warehouse WHERE colours = :colour AND size = :size AND categoryName = :category")
    LiveData<WarehouseItems> findWarehouseItem(String colour, String size, String category);






}
