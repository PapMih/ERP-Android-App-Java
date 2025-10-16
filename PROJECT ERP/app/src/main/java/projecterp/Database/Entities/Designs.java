package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "designs")
public class Designs {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private  String design;
    public Designs(@NonNull String designs) {
        this.design = designs;
    }

    @NonNull
    public String getDesign() {
        return design;
    }

    public void setDesign(@NonNull String design) {
        this.design = design;
    }

    public Designs() {}
}
