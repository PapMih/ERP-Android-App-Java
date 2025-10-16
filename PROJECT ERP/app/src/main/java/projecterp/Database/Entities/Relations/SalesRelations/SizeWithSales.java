package projecterp.Database.Entities.Relations.SalesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Sales;
import projecterp.Database.Entities.Size;

import java.util.List;

public class SizeWithSales {

    @Embedded
    private Size size;
    @Relation(
            parentColumn = "size",
            entityColumn = "size"
    )
    private List<Sales> sales;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
