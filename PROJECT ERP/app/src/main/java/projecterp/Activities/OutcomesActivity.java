package projecterp.Activities;

import android.annotation.SuppressLint;
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
import projecterp.Database.Entities.Purchases;

public class OutcomesActivity extends AppCompatActivity {

    private projecterp.ViewModels.OutcomesViewModel outcomesViewModel;
    private projecterp.ViewModels.TypeOfOutcomeViewModel typeOfOutcomeViewModel;

    // Spinner refs
    private Spinner spTypeOfOutcome;

    // User choice
    private String selTypeOfOutcome = "";




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outcomes);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.outcomes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ActionBar title & back arrow
        getSupportActionBar().setTitle("Προσθήκη εξόδου ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting ViewModel instances scoped to this Activity
        outcomesViewModel       = new ViewModelProvider(this).get(projecterp.ViewModels.OutcomesViewModel.class);
        typeOfOutcomeViewModel  = new ViewModelProvider(this).get(projecterp.ViewModels.TypeOfOutcomeViewModel.class);

        // Spinner look-ups                                                          +
        spTypeOfOutcome     = findViewById(R.id.spinner_typeOfExpense);

        // Load each spinner from LiveData
        typeOfOutcomeViewModel.getAllTypes().observe(this, list -> {
            setSpinner(spTypeOfOutcome, list.stream().map(s -> s.getTypeOfOutcome()).collect(Collectors.toList()),
                    value -> selTypeOfOutcome = value);
        });
    }

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
        android.util.Log.e("OutcomesActivity", "Κλήθηκε η μέθοδος okButtonClick");

        // Handling the input total cost

        // View look-ups
        TextView totalCostTextView = (TextView)findViewById(R.id.editText_totalCost);

        // Take the text from totalCostTextView
        String totalCostString = totalCostTextView.getText().toString().trim();
        android.util.Log.d("OutcomesActivity", "Κείμενο κοστους: " + totalCostString);

        float totalCost;
        int totalCostInt; //Holds the cost in integer to avoid any floating-point precision errors

        //Checking if the format of the amount is correct
        try {
            totalCostString = totalCostTextView.getText().toString().trim().replace(',', '.');
            totalCost = Float.parseFloat(totalCostString);
            totalCostInt = Math.round(totalCost * 100f) ; //Holds the cost in integer to avoid any floating-point precision errors
            android.util.Log.d("OutcomesActivity", "Το κόστος μετατράπηκε σε αριθμό: " + totalCost);
        } catch (NumberFormatException e) {
            android.util.Log.w("OutcomesActivity", "Το κόστος δεν είναι έγκυρος αριθμός", e);
            Toast.makeText(this, "Το κόστος πρέπει να είναι δεκαδικός και θετικός αριθμός", Toast.LENGTH_SHORT).show();
            return;
        }

        //Checking if total cost is positive
        if (totalCostInt <= 0) { //Check cost in integer in order to avoid tiny non-zero floats
            android.util.Log.w("OutcomesActivity", "Το κόστος δεν είναι θετικό: " + totalCost);
            Toast.makeText(this, "Το κόστος πρέπει να είναι θετικό", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("OutcomesActivity", "Επιλογές: type of outcome=" + selTypeOfOutcome);
        //Checking if there is something empty
        if (selTypeOfOutcome.isEmpty()) {
            Toast.makeText(this, "Συμπλήρωσε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }

        android.util.Log.d("OutcomesActivity", "Κλήση της μεθόδου addPrinted");
        addOutcome(selTypeOfOutcome, totalCostInt);
    }

    //Adds the outcome
    private void addOutcome(String selTypeOfOutcome, int totalCostInt) {
        // Get today's date
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());

        outcomesViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.OutcomesViewModel.class);

        LiveData<Outcomes> purchaseLD = outcomesViewModel.findExactOutcome(selTypeOfOutcome, today);

        purchaseLD.observe(this, existingOutcome -> {
            purchaseLD.removeObservers(this);  // Remove observer

            if (existingOutcome != null) {
                Toast.makeText(this, "Έχει γίνει ήδη καταχώρηση του εξόδου", Toast.LENGTH_LONG).show();
            } else {
                Outcomes newOutcome = new Outcomes(totalCostInt, selTypeOfOutcome);
                outcomesViewModel.insertOutcomes(newOutcome);
                Toast.makeText(this, "Το έξοδο καταχωρήθηκε", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
