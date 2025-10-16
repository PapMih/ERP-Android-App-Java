package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.TypeOfOutcomeDao;
import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.TypeOfOutcome;
import projecterp.Database.Entities.Relations.OutcomesRelations.TypeOfOutcomeWithOutcomes;

public class TypeOfOutcomeRepository {

    private final TypeOfOutcomeDao mTypeOfOutcomeDao;
    private final LiveData<List<TypeOfOutcome>> mAllTypes;

    public TypeOfOutcomeRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mTypeOfOutcomeDao = db.typeOfOutcomeDao();
        mAllTypes = mTypeOfOutcomeDao.getAllTypes();
    }

    // Returns all types of outcomes
    public LiveData<List<TypeOfOutcome>> getAllTypes() {
        return mAllTypes;
    }

    // Inserts one or more outcome types
    public void insertTypes(TypeOfOutcome... types) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mTypeOfOutcomeDao.insertTypes(types);
        });
    }

    // Deletes a specific outcome type
    public void deleteType(TypeOfOutcome type) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mTypeOfOutcomeDao.deleteType(type);
        });
    }

    // Returns types of outcomes with their associated outcomes
    public LiveData<List<TypeOfOutcomeWithOutcomes>> getAllTypesWithOutcomes() {
        return mTypeOfOutcomeDao.getTypeOfOutcomeWithOutcomes();
    }

    public LiveData<TypeOfOutcome> findExactTypeOfOutcome(String type) {
        return mTypeOfOutcomeDao.findExactTypeOfOutcome(type);
    }
}
