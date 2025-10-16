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

import projecterp.Database.Entities.Outcomes;
import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.Sales;
import projecterp.Database.Entities.WarehouseItems;

public class SalesActivity extends AppCompatActivity {

    private projecterp.ViewModels.SalesViewModel               salesViewModel;
    private projecterp.ViewModels.SizeViewModel                sizeViewModel;
    private projecterp.ViewModels.ColoursViewModel             coloursViewModel;
    private projecterp.ViewModels.ProductsCategoriesViewModel  categoriesViewModel;
    private projecterp.ViewModels.DesignsViewModel             designsViewModel;
    private projecterp.ViewModels.StoreLocationsViewModel      storeLocationsViewModel;
    private projecterp.ViewModels.ProductsViewModel              productsViewModel;

    // Spinner refs
    private Spinner spSize, spColour, spDesign, spCategory, spStore;

    // User choice
    private String selSize = "", selColour = "", selDesign = "", selCategory = "", selStore = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sales), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar title & back arrow
        getSupportActionBar().setTitle("Προσθήκη πώλησης");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting ViewModel instances scoped to this Activity
        productsViewModel       = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsViewModel.class);
        sizeViewModel           = new ViewModelProvider(this).get(projecterp.ViewModels.SizeViewModel.class);
        coloursViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);
        designsViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.DesignsViewModel.class);
        categoriesViewModel     = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsCategoriesViewModel.class);
        storeLocationsViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.StoreLocationsViewModel.class);
        salesViewModel          = new ViewModelProvider(this).get(projecterp.ViewModels.SalesViewModel.class);


        // Spinner look-ups                                                          +
        spSize     = findViewById(R.id.spinner_size);
        spColour   = findViewById(R.id.spinner_colour);
        spDesign   = findViewById(R.id.spinner_desing);
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
        android.util.Log.e("SalesActivity", "Κλήθηκε η μέθοδος SalesActivity");
        // View look-ups
        TextView amountTextView = (TextView)findViewById(R.id.editText_amount);
        TextView commentTextView = (TextView)findViewById(R.id.editText_comment);
        TextView totalIncomeTextView = (TextView)findViewById(R.id.editText_totalCost);

        // Take the text from amountTextView
        String amountString = amountTextView.getText().toString().trim();
        android.util.Log.d("SalesActivity", "Κείμενο ποσότητας: " + amountString);

        int amount;

        //Checking if the format of the amount is correct
        try {
            amount = Integer.parseInt(amountString);
            android.util.Log.d("SalesActivity", "Η ποσότητα μετατράπηκε σε αριθμό: " + amount);
        } catch (NumberFormatException e) {
            android.util.Log.w("SalesActivity", "Η ποσότητα δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι ακέραιος αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        // Take the text from totalIncomeTextView
        String totalIncomeString = totalIncomeTextView.getText().toString().trim();
        android.util.Log.d("SalesActivity", "Κείμενο κέρδους: " + totalIncomeString);

        float totalIncome;
        int totalIncomeInt; //Holds the cost in integer to avoid any floating-point precision errors

        //Checking if the format of the amount is correct
        try {
            totalIncomeString = totalIncomeTextView.getText().toString().trim().replace(',', '.');
            totalIncome = Float.parseFloat(totalIncomeString);
            totalIncomeInt = Math.round(totalIncome * 100f) ; //Holds the cost in integer to avoid any floating-point precision errors
            android.util.Log.d("SalesActivity", "Το κόστος μετατράπηκε σε αριθμό: " + totalIncome);
        } catch (NumberFormatException e) {
            android.util.Log.w("SalesActivity", "Το κόστος δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Το κόστος πρέπει να είναι δεκαδικός και θετικός αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if total cost is positive
        if (totalIncomeInt <= 0) { //Check cost in integer in order to avoid tiny non-zero floats
            android.util.Log.w("OutcomesActivity", "Το κόστος δεν είναι θετικό: " + totalIncome);
            Toast.makeText(this, "Το κόστος πρέπει να είναι θετικό", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if amount is positive
        if (amount <= 0) {
            android.util.Log.w("SalesActivity", "Η ποσότητα δεν είναι θετική: " + amount);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι θετική", Toast.LENGTH_SHORT).show();
            return;
        }

        // Take the text from commentTextView
        String commentString = commentTextView.getText().toString().trim();
        android.util.Log.d("SalesActivity", "Κείμενο σχολίου: " + commentString);

        android.util.Log.d("SalesActivity", "Επιλογές: category=" + selCategory + ", size=" + selSize + ", design=" + selDesign +
                ", colour=" + selColour + ", store=" + selStore);
        //Checking if there is something empty
        if (selCategory.isEmpty() || selSize.isEmpty() || selDesign.isEmpty() || selColour.isEmpty() || selStore.isEmpty()) {
            Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("SalesActivity", "Κλήση της μεθόδου removePrinted");
        removePrinted(amount, selCategory, selSize, selDesign, selColour, selStore, commentString, totalIncomeInt);
    }

    // Removes from the products and calls the addSale method to add to sales
    private void removePrinted(int amount, String category, String size, String design, String colour, String store,
                               String comment, int totalIncome) {
        {
            android.util.Log.d("SalesActivity", "Starting addPrinted with amount=" + amount + ", category=" + category +
                    ", size=" + size + ", design=" + design + ", colour=" + colour + ", store=" + store);


            LiveData<Products> productsLD = productsViewModel.findExactProduct(colour, size, category, design,store);

            //Decrease the amount in products
            productsLD.observe(this, product -> {
                //Remove first the observer
                productsLD.removeObservers(this);
                if (product == null) {
                    android.util.Log.d("SalesActivity", "Δεν υπάρχει απόθεμα στην αποθήκη με αυτά τα χαρακτηριστικά");
                    Toast.makeText(this, "Δεν υπάρχει καθόλου απόθεμα στην αποθήκη", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (product.getAmount() < amount) {
                    android.util.Log.d("SalesActivity", "Δεν υπάρχει αρκετό απόθεμα στην αποθήκη");
                    Toast.makeText(this, "Το απόθεμα στην αποθήκη είναι ανεπαρκές", Toast.LENGTH_SHORT).show();
                    return;
                }

                android.util.Log.d("SalesActivity", "Μείωση αποθέματος στην αποθήκη");
                productsViewModel.decreaseProductAmount(colour, size, category, design, store, amount);

                int newAmount = product.getAmount() - amount;
                if (newAmount <= 0) {
                    android.util.Log.d("SalesActivity", "Το απόθεμα έφτασε στο 0 – διαγραφή από τη βάση");
                    productsViewModel.deleteProduct(product);
                }

                //Add to sales
                addSale(amount, category, size, design, colour, store, comment, totalIncome );
            });
        }
    }

    private void addSale(int amount, String category, String size, String design, String colour,
                         String store, String comment, int totalIncome) {
        android.util.Log.d("SalesActivity", "Starting addSale with amount=" + amount + ", category=" + category +
                ", size=" + size + ", design=" + design + ", colour=" + colour + ", store=" + store + ", comment=" + comment + ", totalIncome=" + totalIncome);
        Sales newSale = new Sales(category, size, colour, amount, totalIncome, store, comment, design);
        salesViewModel.insertSales(newSale);
        Toast.makeText(this, "Η πώληση καταχωρήθηκε", Toast.LENGTH_SHORT).show();

    }

}