package projecterp.Database.Entities.Relations.ProductsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.ProductsCategories;

import java.util.List;

public class ProductsCategoriesWithProducts {

    @Embedded
    private ProductsCategories category;
    @Relation(
            parentColumn = "categoryName",
            entityColumn = "categoryName"
    )
    private List<Products> product;

    public ProductsCategories getCategory() {
        return category;
    }

    public void setCategory(ProductsCategories category) {
        this.category = category;
    }

    public List<Products> getProduct() {
        return product;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }
}
