package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.ColoursDao;
import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Relations.ProductsRelations.ColoursWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ColoursWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.ColoursWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ColoursWithWarehouseItems;

public class ColoursRepository {

    private final ColoursDao mColoursDao;
    private final LiveData<List<Colours>> mAllColours;

    public ColoursRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mColoursDao = db.coloursDao();
        mAllColours = mColoursDao.getAllColours();
    }

    // Returns all colours
    public LiveData<List<Colours>> getAllColours() {
        return mAllColours;
    }

    // Inserts a new colour
    public void insertColour(Colours colour) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mColoursDao.insertColours(colour);
        });
    }

    // Deletes a colour
    public void deleteColour(Colours colour) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mColoursDao.deleteColour(colour);
        });
    }

    // Returns colours with their associated warehouse items
    public LiveData<List<ColoursWithWarehouseItems>> getAllColoursWithWarehouseItems() {
        return mColoursDao.getColoursWithWarehouseItems();
    }

    // Returns colours with their associated products
    public LiveData<List<ColoursWithProducts>> getAllColoursWithProducts() {
        return mColoursDao.getColoursWithProducts();
    }

    // Returns colours with their associated purchases
    public LiveData<List<ColoursWithPurchases>> getAllColoursWithPurchases() {
        return mColoursDao.getColoursWithPurchases();
    }

    // Returns colours with their associated sales
    public LiveData<List<ColoursWithSales>> getAllColoursWithSales() {
        return mColoursDao.getColoursWithSales();
    }

    public LiveData<Colours> findExactColour(String colourName) {
        return mColoursDao.findExactColour(colourName);
    }
}
