package projecterp.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import projecterp.ViewHoldersAndAdapters.IncomeRangeListAdapter; // new adapter

public class IncomeRangeActivity extends AppCompatActivity {

    private projecterp.ViewModels.SalesViewModel salesViewModel;

    private EditText etStartDate;
    private EditText etEndDate;

    private RecyclerView rvIncomeRangeList;
    private IncomeRangeListAdapter incomeRangeListAdapter;

    private TextView tvTotalIncome;

    private static final DateTimeFormatter UI_FMT  = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
    private static final DateTimeFormatter ISO_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_range);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.income_range), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etStartDate = findViewById(R.id.et_start_date);
        etEndDate   = findViewById(R.id.et_end_date);
        Button btnOK = findViewById(R.id.button_income_range);
        btnOK.setOnClickListener(v -> loadIncomeRange());

        rvIncomeRangeList = findViewById(R.id.recyclerview_income_range);
        rvIncomeRangeList.setLayoutManager(new LinearLayoutManager(this));
        incomeRangeListAdapter = new IncomeRangeListAdapter(); // instantiate new adapter
        rvIncomeRangeList.setAdapter(incomeRangeListAdapter);

        salesViewModel = new ViewModelProvider(this).get(projecterp.ViewModels.SalesViewModel.class);

        attachDatePicker(etStartDate);
        attachDatePicker(etEndDate);

        getSupportActionBar().setTitle("Έσοδα ανά εύρος ημερομηνιών");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotalIncome = findViewById(R.id.tv_total_income_range);
    }

    private void attachDatePicker(EditText target) {
        LocalDate today = LocalDate.now();
        target.setOnClickListener(v -> {
            DatePickerDialog dp = new DatePickerDialog(
                    this,
                    (view, year, month, day) -> {
                        LocalDate picked = LocalDate.of(year, month + 1, day);
                        target.setText(picked.format(UI_FMT));
                    },
                    today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
            dp.show();
        });
    }

    private void loadIncomeRange() {
        String fromStr = etStartDate.getText().toString().trim();
        String toStr   = etEndDate.getText().toString().trim();

        if (fromStr.isEmpty() || toStr.isEmpty()) {
            Toast.makeText(this, "Διάλεξε αρχική και τελική ημερομηνία", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate from, to;
        try {
            from = LocalDate.parse(fromStr, UI_FMT);
            to   = LocalDate.parse(toStr, UI_FMT);
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Λάθος μορφή ημερομηνιών", Toast.LENGTH_SHORT).show();
            return;
        }

        if (from.isAfter(to)) {
            Toast.makeText(this, "Η αρχική ημερομηνία πρέπει να είναι πριν την τελική", Toast.LENGTH_SHORT).show();
            return;
        }

        String isoFrom = from.format(ISO_FMT);
        String isoTo   = to.format(ISO_FMT);

        salesViewModel.getSalesBetweenDates(isoFrom, isoTo)
                .observe(this, list -> {
                    incomeRangeListAdapter.submitList(list);

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