package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "size")
public class Size {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private  String size;
    public Size(@NonNull String size) {
        this.size = size;
    }

    public Size() {}

    @NonNull
    public String getSize() {
        return size;
    }

    public void setSize(@NonNull String size) {
        this.size = size;
    }


}
