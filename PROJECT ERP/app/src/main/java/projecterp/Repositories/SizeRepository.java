package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.SizeDao;
import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.Relations.ProductsRelations.SizeWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.SizeWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.SizeWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.SizeWithWarehouseItems;

public class SizeRepository {

    private final SizeDao mSizeDao;
    private final LiveData<List<Size>> mAllSizes;

    public SizeRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mSizeDao = db.sizeDao();
        mAllSizes = mSizeDao.getAllSizes();
    }

    // Returns all sizes
    public LiveData<List<Size>> getAllSizes() {
        return mAllSizes;
    }

    // Inserts one or more sizes
    public void insertSizes(Size... sizes) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mSizeDao.insertSizes(sizes);
        });
    }

    // Deletes a specific size
    public void deleteSize(Size size) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mSizeDao.deleteSize(size);
        });
    }

    // Returns sizes with their associated warehouse items
    public LiveData<List<SizeWithWarehouseItems>> getAllSizesWithWarehouseItems() {
        return mSizeDao.getSizesWithWarehouseItems();
    }

    // Returns sizes with their associated products
    public LiveData<List<SizeWithProducts>> getAllSizesWithProducts() {
        return mSizeDao.getSizesWithProducts();
    }

    // Returns sizes with their associated purchases
    public LiveData<List<SizeWithPurchases>> getAllSizesWithPurchases() {
        return mSizeDao.getSizesWithPurchases();
    }

    // Returns sizes with their associated sales
    public LiveData<List<SizeWithSales>> getAllSizesWithSales() {
        return mSizeDao.getSizesWithSales();
    }

    public LiveData<Size> findExactSize(String sizeName) {
        return mSizeDao.findExactSize(sizeName);
    }
}
