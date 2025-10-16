package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import projecterp.Database.Entities.Products;

import java.util.List;

@Dao
public interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertProducts(Products... products);

    @Delete
    void deleteProduct(Products product);

    @Query("SELECT * FROM products")
    LiveData<List<Products>> getAllProducts();

    @Query("SELECT * FROM products WHERE colours = :colour")
    LiveData<List<Products>> getProductsByColour(String colour); // Returns products of this colour

    @Query("SELECT * FROM products WHERE categoryName = :category")
    LiveData<List<Products>> getProductsByCategory(String category); // Returns products of this category

    @Query("SELECT * FROM products WHERE size = :size")
    LiveData<List<Products>> getProductsBySize(String size); // Returns products of this size

    @Query("SELECT * FROM products WHERE design = :design")
    LiveData<List<Products>> getProductsByDesign(String design); // Returns products of this design

    @Query("SELECT * FROM products WHERE storeLocation = :store")
    LiveData<List<Products>> getProductsByStoreLocation(String store); // Returns store location of this design

    @Query("UPDATE products SET amount = amount + :amountToAdd WHERE colours = :colour AND size = :size AND categoryName = :category AND design = :design AND storeLocation = :store")
    void increaseProductAmount(String colour, String size, String category, String design, String store, int amountToAdd);

    @Query("UPDATE products SET amount = amount - :amountToSubtract WHERE colours = :colour AND size = :size AND categoryName = :category AND design = :design AND storeLocation = :store")
    void decreaseProductAmount(String colour, String size, String category, String design, String store, int amountToSubtract);

    @Query("SELECT * FROM products WHERE colours = :colour AND size = :size AND categoryName = :category AND design = :design AND storeLocation = :store")
    LiveData<Products> findProduct(String colour, String size, String category, String design, String store);


}
