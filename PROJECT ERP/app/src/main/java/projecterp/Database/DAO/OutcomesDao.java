package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.Purchases;

import java.util.List;

@Dao
public interface OutcomesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertOutcomes(Outcomes... outcomes);

    @Delete
    void deleteOutcome(Outcomes outcome);

    @Query("SELECT * FROM outcomes")
    LiveData<List<Outcomes>> getAllOutcomes();

    @Query("SELECT SUM(TotalCostInt) FROM outcomes WHERE date BETWEEN :fromDate AND :toDate")
    LiveData<Integer> getTotalOutcomesBetweenDates(String fromDate, String toDate);

    @Query("SELECT * FROM outcomes WHERE typeOfOutcome = :typeOfOutcome AND  date = :date")
    LiveData<Outcomes> findExactOutcome(String typeOfOutcome, String date);

    @Query("SELECT DISTINCT strftime('%Y', date) AS year FROM outcomes ORDER BY year DESC")
    LiveData<List<String>> getYears();

    @Query("SELECT * FROM outcomes WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    LiveData<List<Outcomes>> getOutcomesBetweenDates(String from, String to);

}
