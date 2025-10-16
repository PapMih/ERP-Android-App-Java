package projecterp.Database.Entities.Relations.PurchasesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Purchases;
import projecterp.Database.Entities.Size;

import java.util.List;

public class SizeWithPurchases {

    @Embedded
    private Size size;
    @Relation(
            parentColumn = "size",
            entityColumn = "size"
    )
    private List<Purchases> purchases;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }
}
