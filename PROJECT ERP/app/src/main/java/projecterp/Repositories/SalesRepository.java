package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.SalesDao;
import projecterp.Database.Entities.Sales;

public class SalesRepository {

    private final SalesDao mSalesDao;
    private final LiveData<List<Sales>> mAllSales;

    public SalesRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mSalesDao = db.salesDao();
        mAllSales = mSalesDao.getAllSales();
    }

    // Returns all sales
    public LiveData<List<Sales>> getAllSales() {
        return mAllSales;
    }

    // Inserts one or more sales
    public void insertSales(Sales... sales) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mSalesDao.insertSales(sales);
        });
    }

    // Deletes a specific sale
    public void deleteSale(Sales sale) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mSalesDao.deleteSale(sale);
        });
    }

    // Returns total income between two dates
    public LiveData<Double> getTotalSalesBetweenDates(String fromDate, String toDate) {
        return mSalesDao.getTotalSalesBetweenDates(fromDate, toDate);
    }

    public LiveData<List<Integer>> getSaleYears() {
        return mSalesDao.getSaleYears();
    }

    public LiveData<List<Sales>> getSalesBetweenDates(String from, String to) {
        return mSalesDao.getSalesBetweenDates(from, to);
    }
}
