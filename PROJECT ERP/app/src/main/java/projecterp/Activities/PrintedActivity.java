package projecterp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecterp.R;

import java.util.List;
import java.util.stream.Collectors;

import projecterp.Database.Entities.Colours;
import projecterp.Database.Entities.Designs;
import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.Size;
import projecterp.Database.Entities.StoreLocations;
import projecterp.ViewHoldersAndAdapters.NotPrintedListAdapter;
import projecterp.ViewHoldersAndAdapters.PrintedListAdapter;

public class PrintedActivity extends AppCompatActivity {

    // First spinner: what kind of filter (Category / Colour / Size / Store / Design)
    private Spinner spnDataType;
    // Second spinner: the values for the chosen filter
    private Spinner spnSelectionValue;

    // ViewModels for reading data from Room database
    private projecterp.ViewModels.ProductsViewModel productsViewModel;
    private projecterp.ViewModels.ColoursViewModel coloursViewModel;
    private projecterp.ViewModels.SizeViewModel sizeViewModel;
    private projecterp.ViewModels.ProductsCategoriesViewModel categoriesViewModel;
    private projecterp.ViewModels.DesignsViewModel designsViewModel;
    private projecterp.ViewModels.StoreLocationsViewModel storeLocationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Edge-to-edge rendering
        setContentView(R.layout.activity_printed);

        // Getting ViewModel instances scoped to this Activity
        productsViewModel   = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsViewModel.class);
        coloursViewModel     = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);
        sizeViewModel        = new ViewModelProvider(this).get(projecterp.ViewModels.SizeViewModel.class);
        categoriesViewModel  = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsCategoriesViewModel.class);
        designsViewModel  = new ViewModelProvider(this).get(projecterp.ViewModels.DesignsViewModel.class);
        storeLocationsViewModel  = new ViewModelProvider(this).get(projecterp.ViewModels.StoreLocationsViewModel.class);

        // View look-ups
        spnDataType       = findViewById(R.id.list_data_type);
        spnSelectionValue = findViewById(R.id.list_selection_value); // second spinner
        Button btnOK      = findViewById(R.id.button_data_type);

        // Fill the first spinner with options from a fixed list
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.data_type_printed,
                android.R.layout.simple_spinner_item
        );
        spnDataType.setAdapter(typeAdapter);

        // When user picks a filter type, load the available values into the second spinner
        spnDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateValueSpinner(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { /* no-op */ }
        });

        // RecyclerView setup
        RecyclerView recycler = findViewById(R.id.recyclerViewResults);
        PrintedListAdapter dataAdapter = new PrintedListAdapter(new PrintedListAdapter.ProductsDiff());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(dataAdapter);

        // When OK is clicked, get the filtered data and show it
        btnOK.setOnClickListener(v -> {
            String selectedType  = spnDataType.getSelectedItem().toString();
            String selectedValue = spnSelectionValue.getSelectedItem() != null
                    ? spnSelectionValue.getSelectedItem().toString()
                    : "";

            if (!selectedValue.isEmpty()) {
                fetchData(selectedType, selectedValue, dataAdapter);
                Toast.makeText(this, selectedType + ": " + selectedValue, Toast.LENGTH_SHORT).show();
            }
        });

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.printed), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar title & back arrow
        getSupportActionBar().setTitle("Προϊόντα");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    // Fills the second spinner based on the filter the user picked
    private void updateValueSpinner(String type) {
        switch (type) {
            case "Κατηγορία":   // Category
                categoriesViewModel.getAllProductCategories().observe(this, categories ->
                        updateSpinner(categories.stream()
                                .map(ProductsCategories::getCategoryName)
                                .collect(Collectors.toList()))
                );
                break;

            case "Χρώμα":       // Colour
                coloursViewModel.getAllColours().observe(this, colours ->
                        updateSpinner(colours.stream()
                                .map(Colours::getColours)
                                .collect(Collectors.toList()))
                );
                break;

            case "Μέγεθος":     // Size
                sizeViewModel.getAllSizes().observe(this, sizes ->
                        updateSpinner(sizes.stream()
                                .map(Size::getSize)
                                .collect(Collectors.toList()))
                );
                break;

            case "Κατάστημα":   // Store
                storeLocationsViewModel.getAllStoreLocations().observe(this, stores ->
                        updateSpinner(stores.stream()
                                .map(StoreLocations::getStoreLocation)
                                .collect(Collectors.toList()))
                );
                break;

            case "Σχέδιο":      // Design
                designsViewModel.getAllDesigns().observe(this, designs ->
                        updateSpinner(designs.stream()
                                .map(Designs::getDesign)
                                .collect(Collectors.toList()))
                );
                break;
        }
    }

   // Puts a list of strings into the second spinner
    private void updateSpinner(List<String> values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                values
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSelectionValue.setAdapter(adapter);
    }

    // Gets data from the database based on the selected filter and shows it in the adapter
    private void fetchData(String type, String value, PrintedListAdapter adapter) {
        switch (type) {
            case "Κατηγορία": // Category
                productsViewModel.getProductsByCategory(value)
                        .observe(this, adapter::submitList);
                break;

            case "Χρώμα":     // Colour
                productsViewModel.getProductsByColour(value)
                        .observe(this, adapter::submitList);
                break;

            case "Μέγεθος":   // Size
                productsViewModel.getProductsBySize(value)
                        .observe(this, adapter::submitList);
                break;

            case "Κατάστημα": // Store
                productsViewModel.getProductsByStoreLocation(value)
                        .observe(this, adapter::submitList);
                break;

            case "Σχέδιο":    // Design
                productsViewModel.getProductsByDesign(value)
                        .observe(this, adapter::submitList);
                break;
        }
    }
}