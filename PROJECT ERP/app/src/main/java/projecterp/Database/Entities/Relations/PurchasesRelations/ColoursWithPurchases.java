package projecterp.Database.Entities.Relations.PurchasesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Purchases;

import java.util.List;

public class ColoursWithPurchases {

    @Embedded
    private Colours colour;

    @Relation(
            parentColumn = "colours",
            entityColumn = "colour"
    )
    private List<Purchases> purchases;

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public List<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }
}
