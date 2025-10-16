package projecterp.Database.Entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_categories")
public class ProductsCategories {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private  String categoryName;

    public ProductsCategories(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductsCategories() {}

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }



}
