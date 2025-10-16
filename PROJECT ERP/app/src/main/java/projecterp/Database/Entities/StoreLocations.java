package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_locations")
public class StoreLocations {

    @PrimaryKey
    @NonNull
    private String storeLocation;
    public StoreLocations() {}

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public StoreLocations(String storeLocation) {
        this.storeLocation = storeLocation;
    }
}
