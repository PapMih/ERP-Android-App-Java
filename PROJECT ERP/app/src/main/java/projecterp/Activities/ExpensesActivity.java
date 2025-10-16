package projecterp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecterp.R;

public class ExpensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expenses);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.expenses), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().setTitle("Έξοδα");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void startAddExpensesCategoryActivity(View v){
        Intent addExpensesCategory = new Intent(this, AddExpensesCategoryActivity.class);
        startActivity(addExpensesCategory);
    }

    public void startPurchasesActivity(View v){
        Intent purchases = new Intent(this, PurchasesActivity.class);
        startActivity(purchases);
    }

    public void startOutcomesActivity(View v){
        Intent outcome = new Intent(this, OutcomesActivity.class);
        startActivity(outcome);
    }
}