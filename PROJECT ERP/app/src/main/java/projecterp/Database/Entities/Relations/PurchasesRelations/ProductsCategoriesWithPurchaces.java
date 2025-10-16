package projecterp.Database.Entities.Relations.PurchasesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Purchases;

import java.util.List;

public class ProductsCategoriesWithPurchaces {

    @Embedded
    private ProductsCategories category;
    @Relation(
            parentColumn = "categoryName",
            entityColumn = "categoryName"
    )
    private List<Purchases> purchases;

    public ProductsCategories getCategory() {
        return category;
    }

    public void setCategory(ProductsCategories category) {
        this.category = category;
    }

    public List<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }
}
