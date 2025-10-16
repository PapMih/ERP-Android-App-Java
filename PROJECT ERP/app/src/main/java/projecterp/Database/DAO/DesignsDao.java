package projecterp.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Relations.ProductsRelations.ColoursWithProducts;
import projecterp.Database.Entities.Relations.ProductsRelations.DesignsWithProducts;
import projecterp.Database.Entities.Relations.SalesRelations.DesignsWithSales;

import java.util.List;

@Dao
public interface DesignsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertDesigns(Designs... designs);

    @Delete
    void deleteDesign(Designs design);

    @Query("SELECT * FROM designs")
    LiveData<List<Designs>> getAllDesigns();

    @Transaction
    @Query("SELECT * FROM designs")
    LiveData<List<DesignsWithProducts>> getDesignsWithProducts();

    @Transaction
    @Query("SELECT * FROM designs")
    LiveData<List<DesignsWithSales>> getDesignsWithSales();

    @Query("SELECT * FROM designs WHERE design = :designName")
    LiveData<Designs> findExactDesign(String designName);



}
