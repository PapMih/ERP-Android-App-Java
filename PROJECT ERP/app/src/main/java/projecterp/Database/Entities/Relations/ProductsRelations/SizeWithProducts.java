package projecterp.Database.Entities.Relations.ProductsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.Size;

import java.util.List;

public class SizeWithProducts {

    @Embedded
    private Size size;
    @Relation(
            parentColumn = "size",
            entityColumn = "size"
    )
    private List<Products> product;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<Products> getProduct() {
        return product;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }
}
