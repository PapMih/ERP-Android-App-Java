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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import projecterp.Models.OutcomeRow;
import projecterp.ViewHoldersAndAdapters.OutcomeRangeListAdapter;
import projecterp.ViewsModels.PurchasesViewModel;

public class OutcomesRangeActivity extends AppCompatActivity {

    private projecterp.ViewsModels.PurchasesViewModel purchasesVM;
    private projecterp.ViewModels.OutcomesViewModel outcomesVM;

    private EditText etStartDate;
    private EditText etEndDate;

    private RecyclerView rvOutcomeRangeList;
    private OutcomeRangeListAdapter outcomeRangeListAdapter;

    private TextView tvTotalOutcomeRange;

    private static final DateTimeFormatter UI_FMT  = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
    private static final DateTimeFormatter ISO_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outcomes_range);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.outcome_range), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etStartDate = findViewById(R.id.et_start_date);
        etEndDate   = findViewById(R.id.et_end_date);
        Button btnOK = findViewById(R.id.button_outcome_range);
        btnOK.setOnClickListener(v -> loadOutcomeRange());

        rvOutcomeRangeList = findViewById(R.id.recyclerview_outcome_range);
        rvOutcomeRangeList.setLayoutManager(new LinearLayoutManager(this));
        outcomeRangeListAdapter = new OutcomeRangeListAdapter();
        rvOutcomeRangeList.setAdapter(outcomeRangeListAdapter);

        purchasesVM = new ViewModelProvider(this).get(PurchasesViewModel.class);
        outcomesVM  = new ViewModelProvider(this).get(projecterp.ViewModels.OutcomesViewModel.class);

        attachDatePicker(etStartDate);
        attachDatePicker(etEndDate);

        getSupportActionBar().setTitle("Έξοδα ανά εύρος ημερομηνιών");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTotalOutcomeRange = findViewById(R.id.tv_total_outcome_range);
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

    private void loadOutcomeRange() {
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

        purchasesVM.getPurchasesBetweenDates(isoFrom, isoTo).observe(this, purchases -> {
            outcomesVM.getOutcomesBetweenDates(isoFrom, isoTo).observe(this, outcomes -> {

                List<OutcomeRow> rows = new ArrayList<>();
                int sumCents = 0;

                if (purchases != null) {
                    for (projecterp.Database.Entities.Purchases p : purchases) {
                        rows.add(new OutcomeRow(
                                p.getDate(),
                                p.getCategoryName(),
                                p.getSize() + " / " + p.getColour(),
                                p.getAmount(),
                                p.getTotalCostInt(),
                                true
                        ));
                        sumCents += p.getTotalCostInt();
                    }
                }

                if (outcomes != null) {
                    for (projecterp.Database.Entities.Outcomes o : outcomes) {
                        rows.add(new OutcomeRow(
                                o.getDate(),
                                o.getTypeOfOutcome(),
                                "",
                                0,
                                o.getTotalCostInt(),
                                false
                        ));
                        sumCents += o.getTotalCostInt();
                    }
                }

                rows.sort((a, b) -> b.getDate().compareTo(a.getDate()));
                outcomeRangeListAdapter.submitList(rows);

                double euros = sumCents / 100.0;
                tvTotalOutcomeRange.setText(String.format(Locale.getDefault(),
                        "Συνολικά έξοδα: %.2f €", euros));
            });
        });
    }
}
