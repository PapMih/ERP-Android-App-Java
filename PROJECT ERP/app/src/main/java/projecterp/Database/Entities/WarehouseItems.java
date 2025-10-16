package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "warehouse",
        primaryKeys = { "colours", "size", "categoryName" } // There are three primary keys
)
public class WarehouseItems {

    @NonNull
    private int amount;
    @NonNull
    private String colours;
    @NonNull
    private String size;
    @NonNull
    private String categoryName;

    public WarehouseItems() {}

    public WarehouseItems(int amount, @NonNull String colours, @NonNull String size, @NonNull String categoryName) {
        this.amount = amount;
        this.colours = colours;
        this.size = size;
        this.categoryName = categoryName;

        // Checking if amount is >=0
        if (amount < 0) {
            throw new IllegalArgumentException("Η ποσότητα πρέπει να είναι θετική ή μηδέν.");
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getColours() {
        return colours;
    }

    public void setColours(@NonNull String colours) {
        this.colours = colours;
    }

    @NonNull
    public String getSize() {
        return size;
    }

    public void setSize(@NonNull String size) {
        this.size = size;
    }

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }
}
