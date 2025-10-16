package projecterp.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "sales")

public class Sales {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String date;
    @NonNull
    private String categoryName;
    @NonNull
    private String size;
    @NonNull
    private String colours;
    @NonNull
    private int amount;
    @NonNull
    private double totalIncome;
    @NonNull
    private String storeLocation;
    @NonNull
    private String comment;
    @NonNull
    private String design;


    public Sales() { }

    public Sales(@NonNull String categoryName, @NonNull String size, @NonNull String colours, int amount, double totalIncome,
                 @NonNull String storeLocation, @NonNull String comment, @NonNull String design) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Create date formatter
        Date today = new Date(); // Get today's date and time

        this.date = sdf.format(today); // Format the date into a String
        this.categoryName = categoryName;
        this.size = size;
        this.colours = colours;
        this.amount = amount;
        this.totalIncome = totalIncome;
        this.storeLocation = storeLocation;
        this.comment = comment;
        this.design = design;
    }

    public int getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
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
    public String getColours() {
        return colours;
    }

    public void setColours(@NonNull String colours) {
        this.colours = colours;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @NonNull
    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(@NonNull String storeLocation) {
        this.storeLocation = storeLocation;
    }

    @NonNull
    public String getComment() {
        return comment;
    }

    public void setComment(@NonNull String comment) {
        this.comment = comment;
    }

    @NonNull
    public String getDesign() {
        return design;
    }

    public void setDesign(@NonNull String design) {
        this.design = design;
    }
}
