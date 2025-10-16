package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "colours")
public class Colours {

    public Colours() {
    }

    public Colours(@NonNull String colours) {
        this.colours = colours;
    }

    @NonNull
    public String getColours() {
        return colours;
    }

    public void setColours(@NonNull String colours) {
        this.colours = colours;
    }

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private  String colours;
}
