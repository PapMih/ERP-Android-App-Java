package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "products",
        primaryKeys = { "colours", "size", "categoryName", "design", "storeLocation" } // There are five primary keys
)
public class Products {
    @NonNull
    private int amount;
    @NonNull
    private String categoryName;
    @NonNull
    private String size;
    @NonNull
    private String design;
    @NonNull
    private String colours;
    @NonNull
    private String storeLocation;

    public Products() { }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    @NonNull
    public String getSize() {
        return size;
    }

    public void setSize(@NonNull String size) {
        this.size = size;
    }

    @NonNull
    public String getDesign() {
        return design;
    }

    public void setDesign(@NonNull String design) {
        this.design = design;
    }

    @NonNull
    public String getColours() {
        return colours;
    }

    public void setColours(@NonNull String colours) {
        this.colours = colours;
    }

    @NonNull
    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(@NonNull String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Products(int amount, @NonNull String categoryName, @NonNull String size, @NonNull String design, @NonNull String colours, @NonNull String storeLocation) {
        // Checking if amount is >=0
        if (amount < 0) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι θετική ή μηδέν.");
        }

        this.amount = amount;
        this.categoryName = categoryName;
        this.size = size;
        this.design = design;
        this.colours = colours;
        this.storeLocation = storeLocation;


    }
}
