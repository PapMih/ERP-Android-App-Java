package projecterp.Database.Entities.Relations.ProductsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Products;

import java.util.List;

public class DesignsWithProducts {

    @Embedded
    private Designs design;
    @Relation(
            parentColumn = "design",
            entityColumn = "design"
    )
    private List<Products> product;

    public Designs getDesign() {
        return design;
    }

    public void setDesign(Designs design) {
        this.design = design;
    }

    public List<Products> getProduct() {
        return product;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }
}
