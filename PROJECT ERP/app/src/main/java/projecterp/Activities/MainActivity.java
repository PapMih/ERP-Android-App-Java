package projecterp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.projecterp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.warehouse), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        projecterp.ViewModels.ColoursViewModel vm = new ViewModelProvider(this).get(projecterp.ViewModels.ColoursViewModel.class);

        vm.getAllColours()                                  // DAO query
                .observe(this, list ->                           // χρειάζεται observer!
                        android.util.Log.d("DB_TEST", "Χρώματα: " + list.size()));

    }

    public void startWarehouseActivity(View v){
        Intent warehouse = new Intent(this, WarehouseActivity.class);
        startActivity(warehouse);
    }

    public void startExpensesActivity(View v){
        Intent expenses = new Intent(this, ExpensesActivity.class);
        startActivity(expenses);
    }

    public void startReportsActivity(View v){
        Intent reports = new Intent(this, ReportsActivity.class);
        startActivity(reports);
    }

    public void startSalesActivity(View v){
        Intent sales = new Intent(this, SalesActivity.class);
        startActivity(sales);
    }




}
