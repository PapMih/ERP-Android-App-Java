package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Relations.ProductsRelations.ProductsCategoriesWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ProductsCategoriesWithPurchaces;
import projecterp.Database.Entities.Relations.SalesRelations.ProductsCategoriesWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ProductsCategoriesWithWarehouseItems;
import projecterp.Repositories.ProductsCategoriesRepository;

public class ProductsCategoriesViewModel extends AndroidViewModel {

    private final ProductsCategoriesRepository mRepository;
    private final LiveData<List<ProductsCategories>> mAllProductCategories;

    public ProductsCategoriesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductsCategoriesRepository(application);
        mAllProductCategories = mRepository.getAllProductCategories();
    }

    // Returns all product categories
    public LiveData<List<ProductsCategories>> getAllProductCategories() {
        return mAllProductCategories;
    }

    // Inserts one or more product categories
    public void insertProductsCategories(ProductsCategories... categories) {
        mRepository.insertProductsCategories(categories);
    }

    // Deletes a specific product category
    public void deleteProductsCategories(ProductsCategories category) {
        mRepository.deleteProductsCategories(category);
    }

    // Returns product categories with their associated warehouse items
    public LiveData<List<ProductsCategoriesWithWarehouseItems>> getAllProductCategoriesWithWarehouseItems() {
        return mRepository.getAllProductCategoriesWithWarehouseItems();
    }

    // Returns product categories with their associated products
    public LiveData<List<ProductsCategoriesWithProducts>> getAllProductCategoriesWithProducts() {
        return mRepository.getAllProductCategoriesWithProducts();
    }

    // Returns product categories with their associated purchases
    public LiveData<List<ProductsCategoriesWithPurchaces>> getAllProductCategoriesWithPurchases() {
        return mRepository.getAllProductCategoriesWithPurchases();
    }

    // Returns product categories with their associated sales
    public LiveData<List<ProductsCategoriesWithSales>> getAllProductCategoriesWithSales() {
        return mRepository.getAllProductCategoriesWithSales();
    }

    public LiveData<ProductsCategories> findExactProductCategory(String productCategory) {
        return mRepository.findExactProductCategory(productCategory);
    }
}
