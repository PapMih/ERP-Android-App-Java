package projecterp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import projecterp.ViewHoldersAndAdapters.IncomePerMonthListAdapter;

public class IncomePerMonthActivity extends AppCompatActivity {

    // ViewModels for reading data from Room database
    private projecterp.ViewModels.SalesViewModel salesViewModel;

    // First spinner: year
    private Spinner spnYear;
    // Second spinner: month
    private Spinner spnMonth;

    private RecyclerView rvIncomePerMonthList;
    private IncomePerMonthListAdapter incomePerMonthListAdapter;

    private String selYear = "", selMonth = "";

    private TextView tvTotalIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_per_month);

        // Add padding so content doesn't go under the status or navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.income_per_month), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // View look-ups
        spnYear       = findViewById(R.id.list_year); // first spinner
        spnMonth      = findViewById(R.id.list_month); // second spinner
        Button btnOK      = findViewById(R.id.button_income_per_month);
        btnOK.setOnClickListener(v -> loadIncomePerMonth());

        rvIncomePerMonthList = findViewById(R.id.recyclerViewIncomePerMonthList);
        rvIncomePerMonthList.setLayoutManager(                       // <-- ΝΕΟ
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        incomePerMonthListAdapter = new IncomePerMonthListAdapter();
        rvIncomePerMonthList.setAdapter(incomePerMonthListAdapter);

        // Getting ViewModel instances scoped to this Activity
        salesViewModel   = new ViewModelProvider(this).get(projecterp.ViewModels.SalesViewModel.class);

        salesViewModel.getYears().observe(this, years -> {
            if (years == null || years.isEmpty()) {
                Toast.makeText(this, "Δεν βρέθηκαν πωλήσεις", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> yearStrings = years.stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            setSpinner(spnYear, yearStrings, value -> selYear = value);
        });

        List<String> months = Arrays.asList(
                getResources().getStringArray(R.array.data_type_months)
        );

        setSpinner(spnMonth, months, value -> {
            int pos      = spnMonth.getSelectedItemPosition();   // 0 … 11
            selMonth     = String.format("%02d", pos + 1);       // "01" … "12"
        });

        getSupportActionBar().setTitle("Έσοδα ανά μήνα");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotalIncome = findViewById(R.id.tv_total_income);
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

    private void loadIncomePerMonth() {
        if (selYear.isEmpty() || selMonth.isEmpty()) {
            Toast.makeText(this, "Διάλεξε έτος και μήνα", Toast.LENGTH_SHORT).show();
            return;
        }

        String from = selYear + "-" + selMonth + "-01";
        String to   = selYear + "-" + selMonth + "-31";

        salesViewModel.getSalesBetweenDates(from, to)
                .observe(this, list -> {
                    incomePerMonthListAdapter.submitList(list);

                    int sumCents = 0;
                    for (projecterp.Database.Entities.Sales s : list) {
                        sumCents += s.getTotalIncome();
                    }
                    double euros = sumCents / 100.0;
                    tvTotalIncome.setText(
                            String.format(Locale.getDefault(),
                                    "Συνολικά έσοδα: %.2f €", euros));
                });
    }



}