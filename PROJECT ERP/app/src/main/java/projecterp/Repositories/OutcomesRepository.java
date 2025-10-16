package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.OutcomesDao;
import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.Purchases;

public class OutcomesRepository {

    private final OutcomesDao mOutcomesDao;
    private final LiveData<List<Outcomes>> mAllOutcomes;

    public OutcomesRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mOutcomesDao = db.outcomesDao();
        mAllOutcomes = mOutcomesDao.getAllOutcomes();
    }

    // Returns all outcomes
    public LiveData<List<Outcomes>> getAllOutcomes() {
        return mAllOutcomes;
    }

    // Inserts one or more outcomes
    public void insertOutcomes(Outcomes... outcomes) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mOutcomesDao.insertOutcomes(outcomes);
        });
    }

    // Deletes a specific outcome
    public void deleteOutcome(Outcomes outcome) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mOutcomesDao.deleteOutcome(outcome);
        });
    }

    // Returns total cost of outcomes between two dates
    public LiveData<Integer> getTotalOutcomesBetweenDates(String fromDate, String toDate) {
        return mOutcomesDao.getTotalOutcomesBetweenDates(fromDate, toDate);
    }

    public LiveData<Outcomes> findExactOutcome(String typeOfOutcome, String date) {
        return mOutcomesDao.findExactOutcome(typeOfOutcome, date);
    }

    public LiveData<List<String>> getYears() {
        return mOutcomesDao.getYears();
    }

    public LiveData<List<Outcomes>> getOutcomesBetweenDates(String from, String to) {
        return mOutcomesDao.getOutcomesBetweenDates(from, to);
    }
}
