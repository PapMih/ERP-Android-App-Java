package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Dao;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Relations.ProductsRelations.ProductsCategoriesWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ProductsCategoriesWithPurchaces;
import projecterp.Database.Entities.Relations.SalesRelations.ProductsCategoriesWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ColoursWithWarehouseItems;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ProductsCategoriesWithWarehouseItems;
import projecterp.Database.Entities.StoreLocations;

import java.util.List;

@Dao
public interface ProductsCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertProductsCategories(ProductsCategories... categoryName);

    @Delete
    void deleteProductsCategories(ProductsCategories categoryName);

    @Query("SELECT * FROM product_categories")
    LiveData<List<ProductsCategories>> getProductsCategories();

    @Transaction
    @Query("SELECT * FROM product_categories")
    LiveData<List<ProductsCategoriesWithWarehouseItems>> getProductsCategoriesWithWarehouseItems();

    @Transaction
    @Query("SELECT * FROM product_categories")
    LiveData<List<ProductsCategoriesWithProducts>> getProductsCategoriesWithProducts();

    @Transaction
    @Query("SELECT * FROM product_categories")
    LiveData<List<ProductsCategoriesWithPurchaces>> getProductsCategoriesWithPurchases();

    @Transaction
    @Query("SELECT * FROM product_categories")
    LiveData<List<ProductsCategoriesWithSales>> getProductsCategoriesWithSales();

    @Query("SELECT * FROM product_categories WHERE categoryName = :productCategory")
    LiveData<ProductsCategories> findExactProductCategory(String productCategory);


}
