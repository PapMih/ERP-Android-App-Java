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

import projecterp.Database.Entities.TypeOfOutcome;


public class AddExpensesCategoryActivity extends AppCompatActivity {

    private projecterp.ViewModels.TypeOfOutcomeViewModel typeOfOutcomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expenses_category);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_expenses_category), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Προσθήκη κατηγορίας εξόδου");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onButtonClick(View v) {
        TextView expensesCategoryTextView = findViewById(R.id.editText_expenses_category);

        if (expensesCategoryTextView.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Το πεδίο προσθήκη κατηγορίας είναι κενό", Toast.LENGTH_SHORT).show();
        } else {
            addExpensesCategory(expensesCategoryTextView);
        }
    }

    private void addExpensesCategory(TextView expensesCategoryTextView) {
        //Get input text, trim any whitespace and convert it to uppercase to standardize the input and prevent duplicates due to case differences
        String expensesCategoryText = expensesCategoryTextView.getText().toString().trim().toUpperCase();

        //If the text is empty we do not add it
        if (expensesCategoryText.isEmpty()) {
            Toast.makeText(this, "Το πεδίο είναι κενό", Toast.LENGTH_SHORT).show();
            return;
        }

        typeOfOutcomeViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.TypeOfOutcomeViewModel.class);
        LiveData<TypeOfOutcome> typeOfOutcomeLD = typeOfOutcomeViewModel.findExactTypeOfOutcome(expensesCategoryText);

        typeOfOutcomeLD.observe(this, typeOfOutcome -> {
            typeOfOutcomeLD.removeObservers(this); // Remove observer

            if (typeOfOutcome == null) {
                TypeOfOutcome newTypeOfOutcome = new TypeOfOutcome(expensesCategoryText);
                typeOfOutcomeViewModel.insertTypes(newTypeOfOutcome);
                Toast.makeText(this, "Η κατηγορία προστέθηκε", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Η κατηγορία υπάρχει ήδη", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

    