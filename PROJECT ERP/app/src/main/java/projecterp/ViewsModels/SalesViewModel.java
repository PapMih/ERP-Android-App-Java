package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

import projecterp.Database.Entities.Sales;
import projecterp.Repositories.SalesRepository;

public class SalesViewModel extends AndroidViewModel {

    private final SalesRepository mRepository;
    private final LiveData<List<Sales>> mAllSales;

    public SalesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SalesRepository(application);
        mAllSales = mRepository.getAllSales();
    }

    // Returns all sales
    public LiveData<List<Sales>> getAllSales() {
        return mAllSales;
    }

    // Inserts one or more sales
    public void insertSales(Sales... sales) {
        mRepository.insertSales(sales);
    }

    // Deletes a specific sale
    public void deleteSale(Sales sale) {
        mRepository.deleteSale(sale);
    }

    // Returns total sales income between two dates
    public LiveData<Double> getTotalSalesBetweenDates(String fromDate, String toDate) {
        return mRepository.getTotalSalesBetweenDates(fromDate, toDate);
    }

    public LiveData<List<Integer>> getYears() {
        return mRepository.getSaleYears();
    }

    public LiveData<List<Sales>> getSalesBetweenDates(String from, String to) {
        return mRepository.getSalesBetweenDates(from, to);
    }

}
