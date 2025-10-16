package projecterp.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import projecterp.Database.ACDatabase;
import projecterp.Database.DAO.DesignsDao;
import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Relations.ProductsRelations.DesignsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.DesignsWithSales;

public class DesignsRepository {

    private final DesignsDao mDesignsDao;
    private final LiveData<List<Designs>> mAllDesigns;

    public DesignsRepository(Application application) {
        ACDatabase db = ACDatabase.getDatabase(application);
        mDesignsDao = db.designsDao();
        mAllDesigns = mDesignsDao.getAllDesigns();
    }

    // Returns all designs
    public LiveData<List<Designs>> getAllDesigns() {
        return mAllDesigns;
    }

    // Inserts one or more designs
    public void insertDesigns(Designs... designs) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mDesignsDao.insertDesigns(designs);
        });
    }

    // Deletes a specific design
    public void deleteDesign(Designs design) {
        ACDatabase.databaseACExecutor.execute(() -> {
            mDesignsDao.deleteDesign(design);
        });
    }

    // Returns designs with their associated products
    public LiveData<List<DesignsWithProducts>> getAllDesignsWithProducts() {
        return mDesignsDao.getDesignsWithProducts();
    }

    // Returns designs with their associated sales
    public LiveData<List<DesignsWithSales>> getAllDesignsWithSales() {
        return mDesignsDao.getDesignsWithSales();
    }

    public LiveData<Designs> findExactDesign(String designName) {
        return mDesignsDao.findExactDesign(designName);
    }
}
