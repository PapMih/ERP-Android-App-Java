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

public class WarehouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_warehouse);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.warehouse), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Αποθήκη");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void startAddCharacteristicsActivity(View v){
        Intent addCharacteristics = new Intent(this, AddCharacteristicsActivity.class);
        startActivity(addCharacteristics);
    }

    public void startNotPrintedActivity(View v){
        Intent notPrinted = new Intent(this, NotPrintedActivity.class);
        startActivity(notPrinted);
    }

    public void startPrintedActivity(View v){
        Intent printed = new Intent(this, PrintedActivity.class);
        startActivity(printed);
    }

    public void startAddPrintedActivity(View v){
        Intent addPrinted = new Intent(this, AddPrintedActivity.class);
        startActivity(addPrinted);
    }
}