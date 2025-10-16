package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Relations.OutcomesRelations.TypeOfOutcomeWithOutcomes;
import projecterp.Database.Entities.TypeOfOutcome;

import java.util.List;

@Dao
public interface TypeOfOutcomeDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertTypes(TypeOfOutcome... types);

    @Delete
    void deleteType(TypeOfOutcome type);

    @Query("SELECT * FROM type_of_outcome")
    LiveData<List<TypeOfOutcome>> getAllTypes();

    @Transaction
    @Query("SELECT * FROM type_of_outcome")
    LiveData<List<TypeOfOutcomeWithOutcomes>> getTypeOfOutcomeWithOutcomes();

    @Query("SELECT * FROM type_of_outcome WHERE typeOfOutcome = :type")
    LiveData<TypeOfOutcome> findExactTypeOfOutcome(String type);
}
