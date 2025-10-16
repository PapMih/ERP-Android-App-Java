package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.Purchases;
import projecterp.Repositories.OutcomesRepository;

public class OutcomesViewModel extends AndroidViewModel {

    private final OutcomesRepository mRepository;
    private final LiveData<List<Outcomes>> mAllOutcomes;

    public OutcomesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new OutcomesRepository(application);
        mAllOutcomes = mRepository.getAllOutcomes();
    }

    // Returns all outcomes
    public LiveData<List<Outcomes>> getAllOutcomes() {
        return mAllOutcomes;
    }

    // Inserts one or more outcomes
    public void insertOutcomes(Outcomes... outcomes) {
        mRepository.insertOutcomes(outcomes);
    }

    // Deletes a specific outcome
    public void deleteOutcome(Outcomes outcome) {
        mRepository.deleteOutcome(outcome);
    }

    // Returns total outcome cost between two dates
    public LiveData<Integer> getTotalOutcomesBetweenDates(String fromDate, String toDate) {
        return mRepository.getTotalOutcomesBetweenDates(fromDate, toDate);
    }

    public LiveData<Outcomes> findExactOutcome(String typeOfOutcome, String date) {
        return mRepository.findExactOutcome(typeOfOutcome, date);
    }

    public LiveData<List<String>> getYears() {
        return mRepository.getYears();
    }

    public LiveData<List<Outcomes>> getOutcomesBetweenDates(String from, String to) {
        return mRepository.getOutcomesBetweenDates(from, to);
    }
}
