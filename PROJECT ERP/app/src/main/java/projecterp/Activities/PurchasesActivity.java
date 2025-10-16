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

import projecterp.Database.Entities.Purchases;
import projecterp.Database.Entities.WarehouseItems;


public class PurchasesActivity extends AppCompatActivity {

    private projecterp.ViewModels.WarehouseItemsViewModel      warehouseItemsViewModel;
    private projecterp.ViewModels.SizeViewModel                sizeViewModel;
    private projecterp.ViewModels.ColoursViewModel             coloursViewModel;
    private projecterp.ViewModels.ProductsCategoriesViewModel  categoriesViewModel;
    private projecterp.ViewsModels.PurchasesViewModel          purchasesViewModel;

    // Spinner refs
    private Spinner spSize, spColour, spCategory;

    // User choice
    private String selSize = "", selColour = "", selCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_purchases);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.purchases), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar title & back arrow
        getSupportActionBar().setTitle("Νέα αγορά");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting ViewModel instances scoped to this Activity
        warehouseItemsViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.WarehouseItemsViewModel.class);
        sizeViewModel           = new ViewModelProvider(this).get(projecterp.ViewModels.SizeViewModel.class);
        coloursViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);
        categoriesViewModel     = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsCategoriesViewModel.class);

        // Spinner look-ups                                                          +
        spSize     = findViewById(R.id.spinner_size);
        spColour   = findViewById(R.id.spinner_colour);
        spCategory = findViewById(R.id.spinner_category);

        // Load each spinner from LiveData
        sizeViewModel.getAllSizes().observe(this, list -> {
            setSpinner(spSize, list.stream().map(s -> s.getSize()).collect(Collectors.toList()),
                    value -> selSize = value);
        });

        coloursViewModel.getAllColours().observe(this, list -> {
            setSpinner(spColour, list.stream().map(c -> c.getColours()).collect(Collectors.toList()),
                    value -> selColour = value);
        });

        categoriesViewModel.getAllProductCategories().observe(this, list -> {
            setSpinner(spCategory, list.stream().map(c -> c.getCategoryName()).collect(Collectors.toList()),
                    value -> selCategory = value);
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
        android.util.Log.e("PurchasesActivity", "Κλήθηκε η μέθοδος okButtonClick");

        // Handling the input amount

        // View look-ups
        TextView amountTextView = (TextView)findViewById(R.id.editText_amount);

        // Take the text from amountTextView
        String amountString = amountTextView.getText().toString().trim();
        android.util.Log.d("PurchasesActivity", "Κείμενο ποσότητας: " + amountString);

        int amount;

        //Checking if the format of the amount is correct
        try {
            amount = Integer.parseInt(amountString);
            android.util.Log.d("PurchasesActivity", "Η ποσότητα μετατράπηκε σε αριθμό: " + amount);
        } catch (NumberFormatException e) {
            android.util.Log.w("PurchasesActivity", "Η ποσότητα δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι ακέραιος θετικός αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if amount is positive
        if (amount <= 0) {
            android.util.Log.w("PurchasesActivity", "Η ποσότητα δεν είναι θετική: " + amount);
            Toast.makeText(this, "Η ποσότητα πρέπει να είναι ακέραιος θετικός αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        // Handling the input total cost

        // View look-ups
        TextView totalCostTextView = (TextView)findViewById(R.id.editText_totalCost);

        // Take the text from totalCostTextView
        String totalCostString = totalCostTextView.getText().toString().trim();
        android.util.Log.d("PurchasesActivity", "Κείμενο κοστους: " + totalCostString);

        float totalCost;
        int totalCostInt; //Holds the cost in integer to avoid any floating-point precision errors

        //Checking if the format of the amount is correct
        try {
            totalCostString = totalCostTextView.getText().toString().trim().replace(',', '.');
            totalCost = Float.parseFloat(totalCostString);
            totalCostInt = Math.round(totalCost * 100f) ; //Holds the cost in integer to avoid any floating-point precision errors
            android.util.Log.d("PurchasesActivity", "Το κόστος μετατράπηκε σε αριθμό: " + totalCost);
        } catch (NumberFormatException e) {
            android.util.Log.w("PurchasesActivity", "Το κόστος δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Το κόστος πρέπει να είναι δεκαδικός και θετικός αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if amount is positive
        if (totalCostInt <= 0) { //Check cost in integer in order to avoid tiny non-zero floats
            android.util.Log.w("PurchasesActivity", "Το κόστος δεν είναι θετικό: " + totalCost);
            Toast.makeText(this, "Το κόστος πρέπει να είναι θετικό", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("PurchasesActivity", "Επιλογές: category=" + selCategory + ", size=" + selSize + ", colour=" + selColour);
        //Checking if there is something empty
        if (selCategory.isEmpty() || selSize.isEmpty() || selColour.isEmpty()) {
            Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("PurchasesActivity", "Κλήση της μεθόδου addPrinted");
        addPurchase(amount, selCategory, selSize, selColour, totalCostInt);
    }

    //Adds the purchase
    private void addPurchase(int amount, String category, String size, String colour, Integer totalCostInt) {
        // Get today's date
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());

        purchasesViewModel = new ViewModelProvider(this).get(projecterp.ViewsModels.PurchasesViewModel.class);

        LiveData<Purchases> purchaseLD = purchasesViewModel.findExactPurchase(colour, size, category, today);

        purchaseLD.observe(this, existingPurchase -> {
            purchaseLD.removeObservers(this);  // Remove observer

            if (existingPurchase != null) {
                Toast.makeText(this, "Έχει γίνει ήδη καταχώρηση της αγοράς", Toast.LENGTH_LONG).show();
            } else {
                Purchases newPurchase = new Purchases(category, size, colour, amount, totalCostInt);
                purchasesViewModel.insertPurchase(newPurchase);
                Toast.makeText(this, "Η αγορά καταχωρήθηκε", Toast.LENGTH_SHORT).show();
                addNotPrinted(amount, selCategory, selSize, selColour);
            }
        });
    }


    //Adds the purchased item to the db
    private void addNotPrinted(int amount, String category, String size, String colour) {
        android.util.Log.d("PurchasesActivity", "Starting addPrinted with amount=" + amount + ", category=" + category +
                ", size=" + size + ", colour=" + colour );

        //Add to warehouse
        LiveData<WarehouseItems> warehouseItemLD = warehouseItemsViewModel.findExactWarehouseItem(colour, size, category);

        //Decrease the amount in warehouse items
        warehouseItemLD.observe(this, warehouseItem -> {
            //Remove first the observer
            warehouseItemLD.removeObservers(this);

            //If the warehouse item exists we change the amount else we add it to the db
            if (warehouseItem != null) {
                android.util.Log.d("PurchasesActivity", "Το προιόν βρέθηκε");
                warehouseItemsViewModel.increaseWarehouseItemAmount(colour, size, category, amount);
                Toast.makeText(this, "Η ποσότητα ενημερώθηκε", Toast.LENGTH_SHORT).show();
            } else {
                android.util.Log.d("PurchasesActivity", "Το προιόν δεν βρέθηκε");
                WarehouseItems newWarehouseItem = new WarehouseItems(amount, colour, size, category);
                warehouseItemsViewModel.insertWarehouseItems(newWarehouseItem);
                Toast.makeText(this, "Το προϊόν προστέθηκε", Toast.LENGTH_SHORT).show();
            }
        });
    }


}