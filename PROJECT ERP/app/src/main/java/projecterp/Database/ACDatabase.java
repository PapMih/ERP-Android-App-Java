package projecterp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import projecterp.Database.DAO.ColoursDao;
import projecterp.Database.DAO.DesignsDao;
import projecterp.Database.DAO.OutcomesDao;
import projecterp.Database.DAO.ProductsCategoriesDao;
import projecterp.Database.DAO.ProductsDao;
import projecterp.Database.DAO.PurchasesDao;
import projecterp.Database.DAO.SalesDao;
import projecterp.Database.DAO.SizeDao;
import projecterp.Database.DAO.StoreLocationsDao;
import projecterp.Database.DAO.TypeOfOutcomeDao;
import projecterp.Database.DAO.WarehouseItemsDao;
import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Purchases;
import projecterp.Database.Entities.Sales;
import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.StoreLocations;
import projecterp.Database.Entities.TypeOfOutcome;
import projecterp.Database.Entities.WarehouseItems;

@Database(entities = {Colours.class, Designs.class, Outcomes.class, Products.class, ProductsCategories.class,
                        Purchases.class, Sales.class, Size.class, StoreLocations.class, TypeOfOutcome.class, WarehouseItems.class}, version = 1, exportSchema = true)
public abstract  class ACDatabase extends RoomDatabase {
    public abstract ColoursDao coloursDao();
    public abstract DesignsDao designsDao();
    public abstract OutcomesDao outcomesDao();
    public abstract ProductsCategoriesDao productsCategoriesDao();
    public abstract ProductsDao productsDao();
    public abstract PurchasesDao purchasesDao();
    public abstract SalesDao salesDao();
    public abstract SizeDao sizeDao();
    public abstract StoreLocationsDao storeLocationsDao();
    public abstract TypeOfOutcomeDao typeOfOutcomeDao();
    public abstract WarehouseItemsDao warehouseItemsDao();

    private static volatile ACDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseACExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ACDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ACDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ACDatabase.class, "ac_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
