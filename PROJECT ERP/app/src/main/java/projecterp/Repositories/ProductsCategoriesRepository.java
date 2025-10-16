package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.ProductsCategoriesDao;
import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Relations.ProductsRelations.ProductsCategoriesWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ProductsCategoriesWithPurchaces;
import projecterp.Database.Entities.Relations.SalesRelations.ProductsCategoriesWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ProductsCategoriesWithWarehouseItems;
import projecterp.Database.Entities.StoreLocations;

public class ProductsCategoriesRepository {

    private final ProductsCategoriesDao mProductsCategoriesDao;
    private final LiveData<List<ProductsCategories>> mAllProductCategories;

    public ProductsCategoriesRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mProductsCategoriesDao = db.productsCategoriesDao();
        mAllProductCategories = mProductsCategoriesDao.getProductsCategories();
    }

    // Returns all product categories
    public LiveData<List<ProductsCategories>> getAllProductCategories() {
        return mAllProductCategories;
    }

    // Inserts one or more product categories
    public void insertProductsCategories(ProductsCategories... categories) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsCategoriesDao.insertProductsCategories(categories);
        });
    }

    // Deletes a specific product category
    public void deleteProductsCategories(ProductsCategories category) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsCategoriesDao.deleteProductsCategories(category);
        });
    }

    // Returns product categories with their associated warehouse items
    public LiveData<List<ProductsCategoriesWithWarehouseItems>> getAllProductCategoriesWithWarehouseItems() {
        return mProductsCategoriesDao.getProductsCategoriesWithWarehouseItems();
    }

    // Returns product categories with their associated products
    public LiveData<List<ProductsCategoriesWithProducts>> getAllProductCategoriesWithProducts() {
        return mProductsCategoriesDao.getProductsCategoriesWithProducts();
    }

    // Returns product categories with their associated purchases
    public LiveData<List<ProductsCategoriesWithPurchaces>> getAllProductCategoriesWithPurchases() {
        return mProductsCategoriesDao.getProductsCategoriesWithPurchases();
    }

    // Returns product categories with their associated sales
    public LiveData<List<ProductsCategoriesWithSales>> getAllProductCategoriesWithSales() {
        return mProductsCategoriesDao.getProductsCategoriesWithSales();
    }

    public LiveData<ProductsCategories> findExactProductCategory(String productCategory) {
        return mProductsCategoriesDao.findExactProductCategory(productCategory);
    }
}
