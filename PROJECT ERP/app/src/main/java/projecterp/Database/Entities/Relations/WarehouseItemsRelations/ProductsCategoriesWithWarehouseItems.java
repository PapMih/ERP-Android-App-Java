package projecterp.Database.Entities.Relations.WarehouseItemsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.WarehouseItems;

import java.util.List;

public class ProductsCategoriesWithWarehouseItems {
    @Embedded
    private ProductsCategories category;
    @Relation(
            parentColumn = "categoryName",
            entityColumn = "categoryName"
    )
    private List<WarehouseItems> warehouseItems;

    public ProductsCategories getCategory() {
        return category;
    }

    public void setCategory(ProductsCategories category) {
        this.category = category;
    }

    public List<WarehouseItems> getWarehouseItems() {
        return warehouseItems;
    }

    public void setWarehouseItems(List<WarehouseItems> warehouseItems) {
        this.warehouseItems = warehouseItems;
    }
}
