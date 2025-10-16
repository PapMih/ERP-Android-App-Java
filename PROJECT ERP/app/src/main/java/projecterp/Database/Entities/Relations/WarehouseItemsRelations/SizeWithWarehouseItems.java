package projecterp.Database.Entities.Relations.WarehouseItemsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.WarehouseItems;

import java.util.List;

public class SizeWithWarehouseItems {

    @Embedded
    private Size size;

    @Relation(
            parentColumn = "size",
            entityColumn = "size"
    )
    private List<WarehouseItems> warehouseItems;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<WarehouseItems> getWarehouseItems() {
        return warehouseItems;
    }

    public void setWarehouseItems(List<WarehouseItems> warehouseItems) {
        this.warehouseItems = warehouseItems;
    }
}
