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
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecterp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import projecterp.ViewModels.SalesViewModel;
import projecterp.ViewsModels.PurchasesViewModel;
import projecterp.ViewModels.OutcomesViewModel;

public class IncomeOutcomePerMonthActivity extends AppCompatActivity {

    private SalesViewModel salesVM;
    private PurchasesViewModel purchasesVM;
    private OutcomesViewModel outcomesVM;

    private Spinner spnYear, spnMonth;
    private String selYear = "", selMonth = "";

    private TextView tvTotalIncome, tvTotalOutcome, tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_income_outcome_per_month);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.income_outcome_per_month), (v, insets) -> {
            Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return insets;
        });

        getSupportActionBar().setTitle("Έσοδα - Έξοδα ανά μήνα");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spnYear  = findViewById(R.id.list_year);
        spnMonth = findViewById(R.id.list_month);
        Button btnOK = findViewById(R.id.button_income_outcome_per_month);
        btnOK.setOnClickListener(v -> loadIncomeOutcomePerMonth());

        tvTotalIncome  = findViewById(R.id.tv_total_income);
        tvTotalOutcome = findViewById(R.id.tv_total_outcome);
        tvBalance      = findViewById(R.id.tv_balance);

        salesVM     = new ViewModelProvider(this).get(SalesViewModel.class);
        purchasesVM = new ViewModelProvider(this).get(PurchasesViewModel.class);
        outcomesVM  = new ViewModelProvider(this).get(OutcomesViewModel.class);

        // Years: collect from sales (int), purchases (string), outcomes (string)
        salesVM.getYears().observe(this, yearsSales -> {
            purchasesVM.getYears().observe(this, yearsPurch -> {
                outcomesVM.getYears().observe(this, yearsOutc -> {
                    List<String> list = new ArrayList<>();

                    if (yearsSales != null) {
                        List<String> yearsSalesStr = yearsSales.stream()
                                .map(String::valueOf)
                                .collect(Collectors.toList());
                        list.addAll(yearsSalesStr);
                    }
                    if (yearsPurch != null) list.addAll(yearsPurch);
                    if (yearsOutc  != null) list.addAll(yearsOutc);

                    List<String> years = list.stream()
                            .filter(Objects::nonNull)
                            .distinct()
                            .sorted((a, b) -> b.compareTo(a))
                            .collect(Collectors.toList());

                    if (years.isEmpty()) {
                        Toast.makeText(this, "Δεν βρέθηκαν δεδομένα", Toast.LENGTH_SHORT).show();
                    } else {
                        setSpinner(spnYear, years, val -> selYear = val);
                    }
                });
            });
        });

        List<String> months = Arrays.asList(getResources().getStringArray(R.array.data_type_months));
        setSpinner(spnMonth, months, val -> {
            int pos  = spnMonth.getSelectedItemPosition();
            selMonth = String.format(Locale.getDefault(), "%02d", pos + 1);
        });

    }

    private void setSpinner(Spinner sp, List<String> data, java.util.function.Consumer<String> onPick) {
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, data);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                onPick.accept(p.getItemAtPosition(pos).toString());
            }
            @Override public void onNothingSelected(AdapterView<?> p) { }
        });
    }

    private void loadIncomeOutcomePerMonth() {
        if (selYear.isEmpty() || selMonth.isEmpty()) {
            Toast.makeText(this, "Διάλεξε έτος και μήνα", Toast.LENGTH_SHORT).show();
            return;
        }

        String from = selYear + "-" + selMonth + "-01";
        String to   = selYear + "-" + selMonth + "-31";

        salesVM.getSalesBetweenDates(from, to).observe(this, salesList -> {
            purchasesVM.getPurchasesBetweenDates(from, to).observe(this, purchases -> {
                outcomesVM.getOutcomesBetweenDates(from, to).observe(this, outcomes -> {

                    int sumIncomeCents = 0;
                    int sumOutcomeCents = 0;

                    if (salesList != null) {
                        for (projecterp.Database.Entities.Sales s : salesList) {
                            sumIncomeCents += s.getTotalIncome();
                        }
                    }
                    if (purchases != null) {
                        for (projecterp.Database.Entities.Purchases p : purchases) {
                            sumOutcomeCents += p.getTotalCostInt();
                        }
                    }
                    if (outcomes != null) {
                        for (projecterp.Database.Entities.Outcomes o : outcomes) {
                            sumOutcomeCents += o.getTotalCostInt();
                        }
                    }

                    double eurosIncome  = sumIncomeCents / 100.0;
                    double eurosOutcome = sumOutcomeCents / 100.0;
                    double balance      = eurosIncome - eurosOutcome;

                    tvTotalIncome.setText(String.format(Locale.getDefault(),
                            "Συνολικά έσοδα: %.2f €", eurosIncome));
                    tvTotalOutcome.setText(String.format(Locale.getDefault(),
                            "Συνολικά έξοδα: %.2f €", eurosOutcome));
                    tvBalance.setText(String.format(Locale.getDefault(),
                            "Ισοζύγιο: %.2f €", balance));
                });
            });
        });
    }
}
