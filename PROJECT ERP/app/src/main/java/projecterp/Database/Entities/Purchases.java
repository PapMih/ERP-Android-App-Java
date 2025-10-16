package projecterp.Database.Entities;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Date;
import java.util.Locale;

@Entity(tableName = "purchases",
        primaryKeys = { "date", "size", "categoryName", "colour" } // There are three primary keys)
)
public class Purchases {
    @NonNull
    private String date;
    @NonNull
    private String categoryName;
    @NonNull
    private String size;
    @NonNull
    private String colour;
    @NonNull
    private int amount;
    @NonNull
    private int totalCostInt;

    public Purchases() { }

    public Purchases(@NonNull String categoryName, @NonNull String size, @NonNull String colour, int amount, int totalCostInt) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Create date formatter
        Date today = new Date(); // Get today's date and time

        this.date = sdf.format(today); // Format the date into a String
        this.categoryName = categoryName;
        this.size = size;
        this.colour = colour;
        this.amount = amount;
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
    public String getColour() {
        return colour;
    }

    public void setColour(@NonNull String colour) {
        this.colour = colour;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalCostInt() {
        return totalCostInt;
    }

    public void setTotalCostInt(int totalCostInt) {
        this.totalCostInt = totalCostInt;
    }
}
