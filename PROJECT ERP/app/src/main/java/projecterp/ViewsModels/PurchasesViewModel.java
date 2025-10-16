package projecterp.ViewsModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import projecterp.Database.Entities.Purchases;
import projecterp.Repositories.PurchasesRepository;

public class PurchasesViewModel extends AndroidViewModel {

    private final PurchasesRepository mRepository;
    private final LiveData<List<Purchases>> mAllPurchases;

    public PurchasesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PurchasesRepository(application);
        mAllPurchases = mRepository.getAllPurchases();
    }

    // Returns all purchases
    public LiveData<List<Purchases>> getAllPurchases() {
        return mAllPurchases;
    }

    // Inserts one or more purchases
    public void insertPurchase(Purchases... purchases) {
        mRepository.insertPurchase(purchases);
    }

    // Deletes a specific purchase
    public void deletePurchase(Purchases purchase) {
        mRepository.deletePurchase(purchase);
    }

    // Returns total purchase cost between two dates
    public LiveData<List<String>> getYears() {
        return mRepository.getYears();
    }

    public LiveData<List<Purchases>> getPurchasesBetweenDates(String from, String to) {
        return mRepository.getPurchasesBetweenDates(from, to);
    }

    public LiveData<Purchases> findExactPurchase(String colour, String size, String category, String date) {
        return mRepository.findExactPurchase(colour, size, category, date);
    }


}
