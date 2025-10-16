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
import java.util.Objects;
import java.util.stream.Collectors;

import projecterp.Models.OutcomeRow;
import projecterp.ViewHoldersAndAdapters.OutcomePerMonthListAdapter;
import projecterp.ViewsModels.PurchasesViewModel;

public class OutcomePerMonthActivity extends AppCompatActivity {

    private projecterp.ViewsModels.PurchasesViewModel purchasesVM;
    private projecterp.ViewModels.OutcomesViewModel  outcomesVM;

    private Spinner spnYear, spnMonth;
    private String  selYear = "", selMonth = "";

    private OutcomePerMonthListAdapter adapter;
    private TextView tvTotalOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outcome_per_month);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.outcome_per_month), (v,insets) -> {
            Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Έξοδα ανά μήνα");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnYear  = findViewById(R.id.list_year);
        spnMonth = findViewById(R.id.list_month);
        Button btnOK = findViewById(R.id.button_outcome_per_month);
        btnOK.setOnClickListener(v -> loadOutcomePerMonth());

        RecyclerView rv = findViewById(R.id.recyclerViewOutcomePerMonthList);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new OutcomePerMonthListAdapter();
        rv.setAdapter(adapter);

        tvTotalOutcome = findViewById(R.id.tv_total_outcome);

        purchasesVM = new ViewModelProvider(this).get(PurchasesViewModel.class);
        outcomesVM  = new ViewModelProvider(this).get(projecterp.ViewModels.OutcomesViewModel.class);

        purchasesVM.getYears().observe(this, yrsPurch -> {
            outcomesVM.getYears().observe(this, yrsOutc -> {
                List<String> list = new ArrayList<>();
                if (yrsPurch != null) list.addAll(yrsPurch);
                if (yrsOutc  != null) list.addAll(yrsOutc);
                List<String> years = list.stream()
                        .filter(Objects::nonNull)
                        .distinct()
                        .sorted((a, b) -> b.compareTo(a))
                        .collect(Collectors.toList());

                if (years.isEmpty()) {
                    Toast.makeText(this, "Δεν βρέθηκαν έξοδα", Toast.LENGTH_SHORT).show();
                } else {
                    setSpinner(spnYear, years, val -> selYear = val);
                }
            });
        });

        List<String> months = Arrays.asList(getResources().getStringArray(R.array.data_type_months));
        setSpinner(spnMonth, months, val -> {
            int pos  = spnMonth.getSelectedItemPosition();
            selMonth = String.format(Locale.getDefault(), "%02d", pos + 1);
        });
    }

    private void setSpinner(Spinner sp, List<String> data,
                            java.util.function.Consumer<String> onPick) {
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, data);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v,int pos,long id) {
                onPick.accept(p.getItemAtPosition(pos).toString());
            }
            @Override public void onNothingSelected(AdapterView<?> p) { }
        });
    }

    private void loadOutcomePerMonth() {
        if (selYear.isEmpty() || selMonth.isEmpty()) {
            Toast.makeText(this, "Διάλεξε έτος και μήνα", Toast.LENGTH_SHORT).show();
            return;
        }

        String from = selYear + "-" + selMonth + "-01";
        String to   = selYear + "-" + selMonth + "-31";

        purchasesVM.getPurchasesBetweenDates(from, to).observe(this, purchases -> {
            outcomesVM.getOutcomesBetweenDates(from, to).observe(this, outcomes -> {

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

                rows.sort((a,b) -> b.getDate().compareTo(a.getDate()));
                adapter.submitList(rows);

                double euros = sumCents / 100.0;
                tvTotalOutcome.setText(String.format(Locale.getDefault(),
                        "Συνολικά έξοδα: %.2f €", euros));
            });
        });
    }
}
