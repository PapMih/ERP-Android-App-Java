package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.PurchasesDao;
import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.Purchases;

public class PurchasesRepository {

    private final PurchasesDao mPurchasesDao;
    private final LiveData<List<Purchases>> mAllPurchases;

    public PurchasesRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mPurchasesDao = db.purchasesDao();
        mAllPurchases = mPurchasesDao.getAllPurchases();
    }

    // Returns all purchases
    public LiveData<List<Purchases>> getAllPurchases() {
        return mAllPurchases;
    }

    // Inserts one or more purchases
    public void insertPurchase(Purchases... purchases) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mPurchasesDao.insertPurchase(purchases);
        });
    }

    // Deletes a specific purchase
    public void deletePurchase(Purchases purchase) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mPurchasesDao.deletePurchase(purchase);
        });
    }

    // Returns total cost of purchases between two dates
    public LiveData<Integer> getTotalPurchasesBetweenDates(String fromDate, String toDate) {
        return mPurchasesDao.getTotalPurchasesBetweenDates(fromDate, toDate);
    }

    public LiveData<Purchases> findExactPurchase(String colour, String size, String category, String date) {
        return mPurchasesDao.findExactPurchase(colour, size, category, date);
    }

    public LiveData<List<String>> getYears() {
        return mPurchasesDao.getYears();
    }

    // list of purchases within date range
    public LiveData<List<Purchases>> getPurchasesBetweenDates(String from, String to) {
        return mPurchasesDao.getPurchasesBetweenDates(from, to);
    }
}
