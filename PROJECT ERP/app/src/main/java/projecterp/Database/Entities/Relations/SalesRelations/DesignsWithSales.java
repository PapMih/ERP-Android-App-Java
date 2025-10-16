package projecterp.Database.Entities.Relations.SalesRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Sales;

import java.util.List;

public class DesignsWithSales {

    @Embedded
    private Designs design;
    @Relation(
            parentColumn = "design",
            entityColumn = "design"
    )
    private List<Sales> sales;

    public Designs getDesign() {
        return design;
    }

    public void setDesign(Designs design) {
        this.design = design;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
}
