package projecterp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Relations.ProductsRelations.DesignsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.DesignsWithSales;
import projecterp.Repositories.DesignsRepository;

public class DesignsViewModel extends AndroidViewModel {

    private final DesignsRepository mRepository;
    private final LiveData<List<Designs>> mAllDesigns;

    public DesignsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DesignsRepository(application);
        mAllDesigns = mRepository.getAllDesigns();
    }

    // Returns all designs
    public LiveData<List<Designs>> getAllDesigns() {
        return mAllDesigns;
    }

    // Inserts one or more designs
    public void insertDesigns(Designs... designs) {
        mRepository.insertDesigns(designs);
    }

    // Deletes a specific design
    public void deleteDesign(Designs design) {
        mRepository.deleteDesign(design);
    }

    // Returns designs with their associated products
    public LiveData<List<DesignsWithProducts>> getAllDesignsWithProducts() {
        return mRepository.getAllDesignsWithProducts();
    }

    // Returns designs with their associated sales
    public LiveData<List<DesignsWithSales>> getAllDesignsWithSales() {
        return mRepository.getAllDesignsWithSales();
    }

    public LiveData<Designs> findExactDesign(String designName) {
        return mRepository.findExactDesign(designName);
    }
}
