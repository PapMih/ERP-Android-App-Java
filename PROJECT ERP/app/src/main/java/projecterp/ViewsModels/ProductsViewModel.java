package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import projecterp.Database.ACDatabase;
import projecterp.Database.Entities.Products;
import projecterp.Repositories.ProductsRepository;

public class ProductsViewModel extends AndroidViewModel {

    private final ProductsRepository mRepository;
    private final LiveData<List<Products>> mAllProducts;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductsRepository(application);
        mAllProducts = mRepository.getAllProducts();
    }

    // Returns all products
    public LiveData<List<Products>> getAllProducts() {
        return mAllProducts;
    }

    // Inserts one or more products
    public void insertProducts(Products... products) {
        mRepository.insertProducts(products);
    }

    // Deletes a specific product
    public void deleteProduct(Products product) {
        mRepository.deleteProduct(product);
    }

    // Filters
    public LiveData<List<Products>> getProductsByColour(String colour) {
        return mRepository.getProductsByColour(colour);
    }

    public LiveData<List<Products>> getProductsByCategory(String category) {
        return mRepository.getProductsByCategory(category);
    }

    public LiveData<List<Products>> getProductsBySize(String size) {
        return mRepository.getProductsBySize(size);
    }

    public LiveData<List<Products>> getProductsByDesign(String design) {
        return mRepository.getProductsByDesign(design);
    }

    public LiveData<List<Products>> getProductsByStoreLocation(String store) {
        return mRepository.getProductsByStoreLocation(store);
    }

    // Quantity adjustment
    public void increaseProductAmount(String colour, String size, String category, String design, String store, int amountToAdd) {
        mRepository.increaseProductAmount(colour, size, category, design, store, amountToAdd);
    }

    public void decreaseProductAmount(String colour, String size, String category, String design, String store, int amountToSubtract) {
        mRepository.decreaseProductAmount(colour, size, category, design, store, amountToSubtract);
    }

    public LiveData<Products> findExactProduct(String colour, String size, String category, String design, String store) {
        return mRepository.findExactProduct(colour, size, category, design, store);
    }


}
