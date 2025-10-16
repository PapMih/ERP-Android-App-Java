package projecterp.Database.Entities.Relations.ProductsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.StoreLocations;

import java.util.List;

public class StoreLocationsWithProducts {

    @Embedded
    private StoreLocations storeLocation;
    @Relation(
            parentColumn = "storeLocation",
            entityColumn = "storeLocation"
    )
    private List<Products> product;

    public StoreLocations getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocations storeLocation) {
        this.storeLocation = storeLocation;
    }

    public List<Products> getProduct() {
        return product;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }
}
