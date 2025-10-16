package projecterp.Database.Entities.Relations.SalesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Sales;
import projecterp.Database.Entities.StoreLocations;

import java.util.List;

public class StoreLocationsWithSales {

    @Embedded
    private StoreLocations storeLocation;
    @Relation(
            parentColumn = "storeLocation",
            entityColumn = "storeLocation"
    )
    private List<Sales> sales;

    public StoreLocations getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocations storeLocation) {
        this.storeLocation = storeLocation;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
