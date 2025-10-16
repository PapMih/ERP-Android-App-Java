package projecterp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.projecterp.R;

import java.util.List;
import java.util.stream.Collectors;

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.WarehouseItems;

public class AddPrintedActivity extends AppCompatActivity {
    //Adding View Models
    private projecterp.ViewModels.ProductsViewModel           productsViewModel;
    private projecterp.ViewModels.WarehouseItemsViewModel      warehouseItemsViewModel;
    private projecterp.ViewModels.SizeViewModel                sizeViewModel;
    private projecterp.ViewModels.ColoursViewModel             coloursViewModel;
    private projecterp.ViewModels.DesignsViewModel             designsViewModel;
    private projecterp.ViewModels.ProductsCategoriesViewModel  categoriesViewModel;
    private projecterp.ViewModels.StoreLocationsViewModel      storeLocationsViewModel;

    // Spinner refs
    private Spinner spSize, spColour, spDesign, spCategory, spStore;

    // User choice
    private String selSize = "", selColour = "", selDesign = "", selCategory = "", selStore = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);   // Edge-to-edge rendering
        setContentView(R.layout.activity_add_printed);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_printed), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar title & back arrow
        getSupportActionBar().setTitle("Προσθήκη προϊόντος");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting ViewModel instances scoped to this Activity
        productsViewModel       = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsViewModel.class);
        warehouseItemsViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.WarehouseItemsViewModel.class);
        sizeViewModel           = new ViewModelProvider(this).get(projecterp.ViewModels.SizeViewModel.class);
        coloursViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);
        designsViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.DesignsViewModel.class);
        categoriesViewModel     = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsCategoriesViewModel.class);
        storeLocationsViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.StoreLocationsViewModel.class);

        // Spinner look-ups                                                          +
        spSize     = findViewById(R.id.spinner_size);
        spColour   = findViewById(R.id.spinner_colour);
        spDesign   = findViewById(R.id.spinner_design);
        spCategory = findViewById(R.id.spinner_category);
        spStore    = findViewById(R.id.spinner_store);

        // Load each spinner from LiveData
        sizeViewModel.getAllSizes().observe(this, list -> {
            setSpinner(spSize, list.stream().map(s -> s.getSize()).collect(Collectors.toList()),
                    value -> selSize = value);
        });

        coloursViewModel.getAllColours().observe(this, list -> {
            setSpinner(spColour, list.stream().map(c -> c.getColours()).collect(Collectors.toList()),
                    value -> selColour = value);
        });

        designsViewModel.getAllDesigns().observe(this, list -> {
            setSpinner(spDesign, list.stream().map(d -> d.getDesign()).collect(Collectors.toList()),
                    value -> selDesign = value);
        });

        categoriesViewModel.getAllProductCategories().observe(this, list -> {
            setSpinner(spCategory, list.stream().map(c -> c.getCategoryName()).collect(Collectors.toList()),
                    value -> selCategory = value);
        });

        storeLocationsViewModel.getAllStoreLocations().observe(this, list -> {
            setSpinner(spStore, list.stream().map(s -> s.getStoreLocation()).collect(Collectors.toList()),
                    value -> selStore = value);
        });

    }

    //Sets up a Spinner with a list of string items and a selection listener.
    private void setSpinner(Spinner sp, List<String> data, java.util.function.Consumer<String> onPick) {
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, data);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // +
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                onPick.accept(p.getItemAtPosition(pos).toString());
            }
            @Override public void onNothingSelected(AdapterView<?> p) { /* no-op */ }
        });
    }

    public void okButtonClick(View v){
        android.util.Log.e("AddPrintedActivity", "Κλήθηκε η μέθοδος okButtonClick");
        // View look-ups
        TextView amountTextView = (TextView)findViewById(R.id.editText_amount);

        // Take the text from amountTextView
        String amountString = amountTextView.getText().toString().trim();
        android.util.Log.d("AddPrintedActivity", "Κείμενο ποσότητας: " + amountString);

        int amount;

        //Checking if the format of the amount is correct
        try {
            amount = Integer.parseInt(amountString);
            android.util.Log.d("AddPrintedActivity", "Η ποσότητα μετατράπηκε σε αριθμό: " + amount);
        } catch (NumberFormatException e) {
            android.util.Log.w("AddPrintedActivity", "Η ποσότητα δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι ακέραιος αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if amount is positive
        if (amount <= 0) {
            android.util.Log.w("AddPrintedActivity", "Η ποσότητα δεν είναι θετική: " + amount);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι θετική", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("AddPrintedActivity", "Επιλογές: category=" + selCategory + ", size=" + selSize + ", design=" + selDesign +
                ", colour=" + selColour + ", store=" + selStore);
        //Checking if there is something empty
        if (selCategory.isEmpty() || selSize.isEmpty() || selDesign.isEmpty() || selColour.isEmpty() || selStore.isEmpty()) {
            Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("AddPrintedActivity", "Κλήση της μεθόδου addPrinted");
        addPrinted(amount, selCategory, selSize, selDesign, selColour, selStore);
    }

    protected void addPrinted(int amount, String category, String size, String design, String colour, String store) {
        android.util.Log.d("AddPrintedActivity", "Starting addPrinted with amount=" + amount + ", category=" + category +
                ", size=" + size + ", design=" + design + ", colour=" + colour + ", store=" + store);

        //Add in products
        LiveData<WarehouseItems> warehouseItemLD = warehouseItemsViewModel.findExactWarehouseItem(colour, size, category);

        //Decrease the amount in warehouse items
        warehouseItemLD.observe(this, warehouseItem -> {
            //Remove first the observer
            warehouseItemLD.removeObservers(this);
            if (warehouseItem == null) {
                android.util.Log.d("AddPrintedActivity", "Δεν υπάρχει απόθεμα στην αποθήκη με αυτά τα χαρακτηριστικά");
                Toast.makeText(this, "Δεν υπάρχει καθόλου απόθεμα στην αποθήκη", Toast.LENGTH_SHORT).show();
                return;
            }

            if (warehouseItem.getAmount() < amount) {
                android.util.Log.d("AddPrintedActivity", "Δεν υπάρχει αρκετό απόθεμα στην αποθήκη");
                Toast.makeText(this, "Το απόθεμα στην αποθήκη είναι ανεπαρκές", Toast.LENGTH_SHORT).show();
                return;
            }

            android.util.Log.d("AddPrintedActivity", "Μείωση αποθέματος στην αποθήκη");
            warehouseItemsViewModel.decreaseWarehouseItemAmount(colour, size, category, amount);

            int newAmount = warehouseItem.getAmount() - amount;
            if (newAmount <= 0) {
                android.util.Log.d("AddPrintedActivity", "Το απόθεμα έφτασε στο 0 – διαγραφή από τη βάση");
                warehouseItemsViewModel.deleteWarehouseItem(warehouseItem);
            }

            //Add in products
            LiveData<Products> productLD = productsViewModel.findExactProduct(colour, size, category, design, store);

            productLD.observe(this, product -> {
                //Remove first the observer
                productLD.removeObservers(this);

                //If the product exists we change the amount else we add the product to the db
                if (product != null) {
                    android.util.Log.d("AddPrintedActivity", "Το προιόν βρέθηκε");
                    productsViewModel.increaseProductAmount(colour, size, category, design, store, amount);
                    Toast.makeText(this, "Η ποσότητα ενημερώθηκε", Toast.LENGTH_SHORT).show();
                } else {
                    android.util.Log.d("AddPrintedActivity", "Το προιόν δεν βρέθηκε");
                    Products newProduct = new Products(amount, category, size, design, colour, store);
                    productsViewModel.insertProducts(newProduct);
                    Toast.makeText(this, "Το προϊόν προστέθηκε", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}