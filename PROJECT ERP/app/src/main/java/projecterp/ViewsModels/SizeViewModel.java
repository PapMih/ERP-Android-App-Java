package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.Relations.ProductsRelations.SizeWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.SizeWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.SizeWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.SizeWithWarehouseItems;
import projecterp.Repositories.SizeRepository;

public class SizeViewModel extends AndroidViewModel {

    private final SizeRepository mRepository;
    private final LiveData<List<Size>> mAllSizes;

    public SizeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SizeRepository(application);
        mAllSizes = mRepository.getAllSizes();
    }

    // Returns all sizes
    public LiveData<List<Size>> getAllSizes() {
        return mAllSizes;
    }

    // Inserts one or more sizes
    public void insertSizes(Size... sizes) {
        mRepository.insertSizes(sizes);
    }

    // Deletes a specific size
    public void deleteSize(Size size) {
        mRepository.deleteSize(size);
    }

    // Returns sizes with their associated warehouse items
    public LiveData<List<SizeWithWarehouseItems>> getAllSizesWithWarehouseItems() {
        return mRepository.getAllSizesWithWarehouseItems();
    }

    // Returns sizes with their associated products
    public LiveData<List<SizeWithProducts>> getAllSizesWithProducts() {
        return mRepository.getAllSizesWithProducts();
    }

    // Returns sizes with their associated purchases
    public LiveData<List<SizeWithPurchases>> getAllSizesWithPurchases() {
        return mRepository.getAllSizesWithPurchases();
    }

    // Returns sizes with their associated sales
    public LiveData<List<SizeWithSales>> getAllSizesWithSales() {
        return mRepository.getAllSizesWithSales();
    }

    public LiveData<Size> findExactSize(String sizeName) {
        return mRepository.findExactSize(sizeName);
    }
}
