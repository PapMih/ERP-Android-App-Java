package projecterp.Database.Entities.Relations.SalesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Sales;

import java.util.List;

public class ProductsCategoriesWithSales {

    @Embedded
    private ProductsCategories category;
    @Relation(
            parentColumn = "categoryName",
            entityColumn = "categoryName"
    )
    private List<Sales> sales;

    public ProductsCategories getCategory() {
        return category;
    }

    public void setCategory(ProductsCategories category) {
        this.category = category;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
