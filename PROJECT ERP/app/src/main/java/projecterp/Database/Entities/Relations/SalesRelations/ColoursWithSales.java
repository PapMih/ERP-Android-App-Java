package projecterp.Database.Entities.Relations.SalesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Sales;

import java.util.List;

public class ColoursWithSales {

    @Embedded
    private Colours colour;

    @Relation(
            parentColumn = "colours",
            entityColumn = "colours"
    )
    private List<Sales> sales;

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }

}
