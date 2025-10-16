package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "type_of_outcome")
public class TypeOfOutcome {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private  String typeOfOutcome;

    public TypeOfOutcome() { }

    public TypeOfOutcome(@NonNull String typeOfOutcome) {
        this.typeOfOutcome = typeOfOutcome;
    }

    @NonNull
    public String getTypeOfOutcome() {
        return typeOfOutcome;
    }

    public void setTypeOfOutcome(@NonNull String typeOfOutcome) {
        this.typeOfOutcome = typeOfOutcome;
    }
}
