package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Relations.ProductsRelations.ColoursWithProducts;
import projecterp.Database.Entities.Relations.PurchasesRelations.ColoursWithPurchases;
import projecterp.Database.Entities.Relations.SalesRelations.ColoursWithSales;
import projecterp.Database.Entities.Relations.WarehouseItemsRelations.ColoursWithWarehouseItems;
import projecterp.Repositories.ColoursRepository;

public class ColoursViewModel extends AndroidViewModel {

    private final ColoursRepository mRepository;
    private final LiveData<List<Colours>> mAllColours;

    public ColoursViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ColoursRepository(application);
        mAllColours = mRepository.getAllColours();
    }

    // Expose the list of all colours
    public LiveData<List<Colours>> getAllColours() {
        return mAllColours;
    }

    // Insert a colour
    public void insertColour(Colours colour) {
        mRepository.insertColour(colour);
    }

    // Delete a colour
    public void deleteColour(Colours colour) {
        mRepository.deleteColour(colour);
    }

    // Get colours with their warehouse items
    public LiveData<List<ColoursWithWarehouseItems>> getAllColoursWithWarehouseItems() {
        return mRepository.getAllColoursWithWarehouseItems();
    }

    // Get colours with their products
    public LiveData<List<ColoursWithProducts>> getAllColoursWithProducts() {
        return mRepository.getAllColoursWithProducts();
    }

    // Get colours with their purchases
    public LiveData<List<ColoursWithPurchases>> getAllColoursWithPurchases() {
        return mRepository.getAllColoursWithPurchases();
    }

    // Get colours with their sales
    public LiveData<List<ColoursWithSales>> getAllColoursWithSales() {
        return mRepository.getAllColoursWithSales();
    }

    public LiveData<Colours> findExactColour(String colourName) {
        return mRepository.findExactColour(colourName);
    }
}
