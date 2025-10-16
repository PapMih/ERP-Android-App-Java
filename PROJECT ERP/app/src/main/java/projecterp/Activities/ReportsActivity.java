package projecterp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecterp.R;

public class ReportsActivity extends AppCompatActivity {
    private Switch switchPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reports);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reports), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Αναφορές");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchPeriod = findViewById(R.id.switch_period);
    }

    public void startIncome(View v) {
        if (switchPeriod.isChecked()) {
            startIncomeRangeActivity();
        } else {
            startIncomePerMonthActivity();
        }
    }

    public void startIncomeRangeActivity() {
        startActivity(new Intent(this, IncomeRangeActivity.class));
    }

    public void startIncomePerMonthActivity() {
        startActivity(new Intent(this, IncomePerMonthActivity.class));
    }

    public void startOutcome(View v) {
        if (switchPeriod.isChecked()) {
            startOutcomesRangeActivity();
        } else {
            startOutcomePerMonthActivity();
        }
    }

    public void startOutcomesRangeActivity() {
        startActivity(new Intent(this, OutcomesRangeActivity.class));
    }

    public void startOutcomePerMonthActivity() {
        startActivity(new Intent(this, OutcomePerMonthActivity.class));
    }

    public void startBalance(View v) {
        if (switchPeriod.isChecked()) {
            startIncomeOutcomeRangeActivity();
        } else {
            startIncomeOutcomePerMonthActivity();
        }
    }

    public void startIncomeOutcomeRangeActivity() {
        startActivity(new Intent(this, IncomeOutcomeRangeActivity.class));
    }

    public void startIncomeOutcomePerMonthActivity() {
        startActivity(new Intent(this, IncomeOutcomePerMonthActivity.class));
    }

}
