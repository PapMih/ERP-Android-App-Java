package projecterp.Database.Entities.Relations.WarehouseItemsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.WarehouseItems;

import java.util.List;

public class ColoursWithWarehouseItems {

    @Embedded
    private Colours colour;

    @Relation(
            parentColumn = "colours",
            entityColumn = "colours"
    )
    private List<WarehouseItems> warehouseItems;

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public List<WarehouseItems> getWarehouseItems() {
        return warehouseItems;
    }

    public void setWarehouseItems(List<WarehouseItems> warehouseItems) {
        this.warehouseItems = warehouseItems;
    }
}
