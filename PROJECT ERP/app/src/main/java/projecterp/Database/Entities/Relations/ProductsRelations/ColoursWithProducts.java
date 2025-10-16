package projecterp.Database.Entities.Relations.ProductsRelations;

import androidx.room.Embedded;
import androidx.room.Relation;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Products;

import java.util.List;

public class ColoursWithProducts {

    @Embedded
    private Colours colour;

    @Relation(
            parentColumn = "colours",
            entityColumn = "colours"
    )
    private List<Products> products;

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
