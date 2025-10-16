package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "outcomes",
        primaryKeys = { "date", "typeOfOutcome" } // There are two primary keys
)
public class Outcomes {
    @NonNull
    private int totalCostInt;
    @NonNull
    private String date;
    @NonNull
    private String typeOfOutcome;

    public Outcomes() {}

    public Outcomes(int totalCostInt, @NonNull String typeOfOutcome) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Create date formatter
        Date today = new Date(); // Get today's date and time

        this.date = sdf.format(today); // Format the date into a String
        this.totalCostInt = totalCostInt;
        this.typeOfOutcome = typeOfOutcome;
    }

    public int getTotalCostInt() {
        return totalCostInt;
    }

    public void setTotalCostInt(int totalCostInt) {
        this.totalCostInt = totalCostInt;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getTypeOfOutcome() {
        return typeOfOutcome;
    }

    public void setTypeOfOutcome(@NonNull String typeOfOutcome) {
        this.typeOfOutcome = typeOfOutcome;
    }
}
