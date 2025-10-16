package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.ProductsDao;
import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.StoreLocations;

public class ProductsRepository {

    private final ProductsDao mProductsDao;
    private final LiveData<List<Products>> mAllProducts;

    public ProductsRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mProductsDao = db.productsDao();
        mAllProducts = mProductsDao.getAllProducts();
    }

    // Returns all products
    public LiveData<List<Products>> getAllProducts() {
        return mAllProducts;
    }

    // Inserts one or more products
    public void insertProducts(Products... products) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsDao.insertProducts(products);
        });
    }

    // Deletes a specific product
    public void deleteProduct(Products product) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsDao.deleteProduct(product);
        });
    }

    // Returns products filtered by colour
    public LiveData<List<Products>> getProductsByColour(String colour) {
        return mProductsDao.getProductsByColour(colour);
    }

    // Returns products filtered by category
    public LiveData<List<Products>> getProductsByCategory(String category) {
        return mProductsDao.getProductsByCategory(category);
    }

    // Returns products filtered by size
    public LiveData<List<Products>> getProductsBySize(String size) {
        return mProductsDao.getProductsBySize(size);
    }

    // Returns products filtered by design
    public LiveData<List<Products>> getProductsByDesign(String design) {
        return mProductsDao.getProductsByDesign(design);
    }

    // Returns products filtered by store location
    public LiveData<List<Products>> getProductsByStoreLocation(String store) {
        return mProductsDao.getProductsByStoreLocation(store);
    }

    // Increases the amount of a specific product
    public void increaseProductAmount(String colour, String size, String category, String design, String store, int amountToAdd) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsDao.increaseProductAmount(colour, size, category, design, store, amountToAdd);
        });
    }

    // Decreases the amount of a specific product
    public void decreaseProductAmount(String colour, String size, String category, String design, String store, int amountToSubtract) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mProductsDao.decreaseProductAmount(colour, size, category, design, store, amountToSubtract);
        });
    }

    //Finds if a product already exists
    public LiveData<Products> findExactProduct(String colour, String size, String category, String design, String storeLocation) {
        return mProductsDao.findProduct(colour, size, category, design, storeLocation);
    }
}
