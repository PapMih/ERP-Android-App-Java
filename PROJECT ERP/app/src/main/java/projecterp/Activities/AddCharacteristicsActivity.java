package projecterp.Activities;

import android.os.Bundle;
import android.view.View;
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

import projecterp.Database.Entities.ProductsCategories;
import projecterp.Database.Entities.StoreLocations;
import projecterp.ViewModels.ProductsCategoriesViewModel;

public class AddCharacteristicsActivity extends AppCompatActivity {

    private projecterp.ViewModels.StoreLocationsViewModel storeLocationsViewModel;
    private ProductsCategoriesViewModel productsCategoriesViewModel;
    private projecterp.ViewModels.ColoursViewModel colourViewModel;
    private projecterp.ViewModels.SizeViewModel sizeViewModel;
    private projecterp.ViewModels.DesignsViewModel designViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_characteristics);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_characteristics), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().setTitle("Προσθήκη χαρακτηριστικών");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onButtonClick(View v) {
        int id = v.getId(); // The id of clicked button

        if (id == R.id.button_size) {
            TextView sizeTextView = findViewById(R.id.editText_size);
            if (sizeTextView.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Το πεδίο προσθήκη μεγέθους είναι κενό", Toast.LENGTH_SHORT).show();
            } else {
                addSize(sizeTextView);
            }
        } else if (id == R.id.button_colour) {
            TextView colourTextView = findViewById(R.id.editText_colour);
            if (colourTextView.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Το πεδίο προσθήκη χρώματος είναι κενό", Toast.LENGTH_SHORT).show();
            } else {
                addColour(colourTextView);
            }
        } else if (id == R.id.button_design) {
            TextView desingTextView = findViewById(R.id.editText_design);
            if (desingTextView.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Το πεδίο προσθήκη σχεδίου είναι κενό", Toast.LENGTH_SHORT).show();
            } else {
                addDesign(desingTextView);
            }
        } else if (id == R.id.button_category) {
            TextView categoryTextView = findViewById(R.id.editText_category);
            if (categoryTextView.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Το πεδίο προσθήκη κατηγορίας είναι κενό", Toast.LENGTH_SHORT).show();
            } else {
                addCategory(categoryTextView);
            }
        } else if (id == R.id.button_pos) {
            TextView posTextView = findViewById(R.id.editText_pos);
            if (posTextView.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Το πεδίο προσθήκη σημείου πώλησης είναι κενό", Toast.LENGTH_SHORT).show();
            } else {
                addPos(posTextView);
            }
        }
    }


    private void addPos(TextView posTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String posText = posTextView.getText().toString().trim().toUpperCase();

        //If the text is empty we do not add it
        if (posText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        storeLocationsViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.StoreLocationsViewModel.class);
        LiveData<StoreLocations> storeLocationLD = storeLocationsViewModel.findExactStoreLocation(posText);

        storeLocationLD.observe(this, storeLocation -> {
            storeLocationLD.removeObservers(this); // Remove observer

            if (storeLocation == null) {
                StoreLocations newLocation = new StoreLocations(posText);
                storeLocationsViewModel.insertStoreLocations(newLocation);
                Toast.makeText(this, "Το σημείο πώλησης προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Το σημείο πώλησης υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addCategory(TextView categoryTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String categoryText = categoryTextView.getText().toString().trim().toUpperCase();

        //If the text is empty we do not add it
        if (categoryText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        //Find if category exists
        productsCategoriesViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.ProductsCategoriesViewModel.class);
        LiveData<ProductsCategories> productsCategoriesLD = productsCategoriesViewModel.findExactProductCategory(categoryText);

        productsCategoriesLD.observe(this, productsCategories -> {
            productsCategoriesLD.removeObservers(this); // Remove observer

            if (productsCategories == null) {
                ProductsCategories newCategory = new ProductsCategories(categoryText);
                productsCategoriesViewModel.insertProductsCategories(newCategory);
                Toast.makeText(this, "Η κατηγορία προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Η κατηγορία υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addColour(TextView colourTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String colourText = colourTextView.getText().toString().trim().toUpperCase();

        // If the text is empty we do not add it
        if (colourText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find if colour exists
        projecterp.ViewModels.ColoursViewModel colourViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);
        LiveData<projecterp.Database.Entities.Colours> colourLD = colourViewModel.findExactColour(colourText);

        colourLD.observe(this, colour -> {
            colourLD.removeObservers(this); // Remove observer

            if (colour == null) {
                projecterp.Database.Entities.Colours newColour = new projecterp.Database.Entities.Colours(colourText);
                colourViewModel.insertColour(newColour);
                Toast.makeText(this, "Το χρώμα προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Το χρώμα υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addSize(TextView sizeTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String sizeText = sizeTextView.getText().toString().trim().toUpperCase();

        // If the text is empty we do not add it
        if (sizeText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find if size exists
        projecterp.ViewModels.SizeViewModel sizeViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.SizeViewModel.class);
        LiveData<projecterp.Database.Entities.Size> sizeLD = sizeViewModel.findExactSize(sizeText);

        sizeLD.observe(this, size -> {
            sizeLD.removeObservers(this); // Remove observer

            if (size == null) {
                projecterp.Database.Entities.Size newSize = new projecterp.Database.Entities.Size(sizeText);
                sizeViewModel.insertSizes(newSize);
                Toast.makeText(this, "Το μέγεθος προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Το μέγεθος υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDesign(TextView designTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String designText = designTextView.getText().toString().trim().toUpperCase();

        // If the text is empty we do not add it
        if (designText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        // Find if design exists
        projecterp.ViewModels.DesignsViewModel designViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.DesignsViewModel.class);
        LiveData<projecterp.Database.Entities.Designs> designLD = designViewModel.findExactDesign(designText);

        designLD.observe(this, design -> {
            designLD.removeObservers(this); // Remove observer

            if (design == null) {
                projecterp.Database.Entities.Designs newDesign = new projecterp.Database.Entities.Designs(designText);
                designViewModel.insertDesigns(newDesign);
                Toast.makeText(this, "Το σχέδιο προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Το σχέδιο υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }

}