package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.TypeOfOutcome;
import projecterp.Database.Entities.Relations.OutcomesRelations.TypeOfOutcomeWithOutcomes;
import projecterp.Repositories.TypeOfOutcomeRepository;

public class TypeOfOutcomeViewModel extends AndroidViewModel {

    private final TypeOfOutcomeRepository mRepository;
    private final LiveData<List<TypeOfOutcome>> mAllTypes;

    public TypeOfOutcomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TypeOfOutcomeRepository(application);
        mAllTypes = mRepository.getAllTypes();
    }

    // Returns all types of outcomes
    public LiveData<List<TypeOfOutcome>> getAllTypes() {
        return mAllTypes;
    }

    // Inserts one or more types of outcomes
    public void insertTypes(TypeOfOutcome... types) {
        mRepository.insertTypes(types);
    }

    // Deletes a specific type of outcome
    public void deleteType(TypeOfOutcome type) {
        mRepository.deleteType(type);
    }

    // Returns all types with their associated outcomes
    public LiveData<List<TypeOfOutcomeWithOutcomes>> getAllTypesWithOutcomes() {
        return mRepository.getAllTypesWithOutcomes();
    }

    public LiveData<TypeOfOutcome> findExactTypeOfOutcome(String type) {
        return mRepository.findExactTypeOfOutcome(type);
    }

}
