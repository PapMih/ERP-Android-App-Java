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
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecterp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import projecterp.ViewsModels.PurchasesViewModel;

public class IncomeOutcomeRangeActivity extends AppCompatActivity {

    private projecterp.ViewModels.SalesViewModel salesVM;
    private projecterp.ViewsModels.PurchasesViewModel purchasesVM;
    private projecterp.ViewModels.OutcomesViewModel outcomesVM;

    private EditText etStartDate, etEndDate;
    private Button btnOK;

    private TextView tvTotalIncome, tvTotalOutcome, tvBalance;

    private static final DateTimeFormatter UI_FMT  = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
    private static final DateTimeFormatter ISO_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_outcome_range);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.income_outcome_range), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Έσοδα - Έξοδα (εύρος)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etStartDate = findViewById(R.id.et_start_date);
        etEndDate   = findViewById(R.id.et_end_date);
        btnOK       = findViewById(R.id.button_income_outcome_range);

        tvTotalIncome  = findViewById(R.id.tv_total_income_range);
        tvTotalOutcome = findViewById(R.id.tv_total_outcome_range);
        tvBalance      = findViewById(R.id.tv_balance_range);

        // Προαιρετικά, απόκρυψε το RecyclerView αν υπάρχει στο layout
        RecyclerView rv = findViewById(R.id.recyclerview_income_outcome_range);
        if (rv != null) {
            rv.setVisibility(android.view.View.GONE);
        }

        salesVM    = new ViewModelProvider(this).get(projecterp.ViewModels.SalesViewModel.class);
        purchasesVM = new ViewModelProvider(this).get(PurchasesViewModel.class);
        outcomesVM = new ViewModelProvider(this).get(projecterp.ViewModels.OutcomesViewModel.class);

        attachDatePicker(etStartDate);
        attachDatePicker(etEndDate);

        btnOK.setOnClickListener(v -> loadData());
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

    private void loadData() {
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

        salesVM.getSalesBetweenDates(isoFrom, isoTo).observe(this, sales -> {
            purchasesVM.getPurchasesBetweenDates(isoFrom, isoTo).observe(this, purchases -> {
                outcomesVM.getOutcomesBetweenDates(isoFrom, isoTo).observe(this, outcomes -> {

                    int totalIncomeCents = 0;
                    int totalOutcomeCents = 0;

                    if (sales != null) {
                        for (projecterp.Database.Entities.Sales s : sales) {
                            totalIncomeCents += s.getTotalIncome();
                        }
                    }
                    if (purchases != null) {
                        for (projecterp.Database.Entities.Purchases p : purchases) {
                            totalOutcomeCents += p.getTotalCostInt();
                        }
                    }
                    if (outcomes != null) {
                        for (projecterp.Database.Entities.Outcomes o : outcomes) {
                            totalOutcomeCents += o.getTotalCostInt();
                        }
                    }

                    double eurosIncome = totalIncomeCents / 100.0;
                    double eurosOutcome = totalOutcomeCents / 100.0;
                    double eurosBalance = eurosIncome - eurosOutcome;

                    tvTotalIncome.setText(String.format(Locale.getDefault(), "Συνολικά έσοδα: %.2f €", eurosIncome));
                    tvTotalOutcome.setText(String.format(Locale.getDefault(), "Συνολικά έξοδα: %.2f €", eurosOutcome));
                    tvBalance.setText(String.format(Locale.getDefault(), "Ισοζύγιο: %.2f €", eurosBalance));
                });
            });
        });
    }
}
