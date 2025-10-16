package projecterp.ViewHoldersAndAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecterp.R;

import java.util.Locale;

import projecterp.Database.Entities.Sales;

public class IncomePerMonthListAdapter
        extends ListAdapter<Sales, IncomePerMonthListAdapter.SalesViewHolder> {


    static class SalesViewHolder extends RecyclerView.ViewHolder {

        private final TextView dateText, categoryText, designText, sizeText,
                storeText, colourText, incomeText, amountText, commentText;

        private SalesViewHolder(View v) {
            super(v);
            dateText     = v.findViewById(R.id.textViewDate);
            categoryText = v.findViewById(R.id.textViewCategory);
            designText   = v.findViewById(R.id.textViewDesign);
            sizeText     = v.findViewById(R.id.textViewSize);
            storeText    = v.findViewById(R.id.textViewStore);
            colourText   = v.findViewById(R.id.textViewColour);
            incomeText   = v.findViewById(R.id.textViewTotalIncome);
            amountText   = v.findViewById(R.id.textViewAmount);
            commentText  = v.findViewById(R.id.textViewComment);
        }

        void bind(Sales s) {
            dateText.setText(s.getDate());
            categoryText.setText("Κατηγορία: " + s.getCategoryName());
            designText.setText("Σχέδιο: " + s.getDesign());
            sizeText.setText("Μέγεθος: " + s.getSize());
            storeText.setText("Κατάστημα: " + s.getStoreLocation());
            colourText.setText("Χρώμα: " + s.getColours());

            double euros = s.getTotalIncome() / 100.0;
            incomeText.setText(String.format(Locale.getDefault(),
                    "Έσοδο: %.2f €", euros));

            amountText.setText("Ποσ.: " + s.getAmount());
            commentText.setText("Σχόλιο: " + s.getComment());
        }

        static SalesViewHolder create(ViewGroup parent) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_income_per_month, parent, false);
            return new SalesViewHolder(v);
        }

    }

    public static class SalesDiff extends DiffUtil.ItemCallback<Sales> {
        @Override public boolean areItemsTheSame(@NonNull Sales a, @NonNull Sales b) {
            return a.getId() == b.getId();
        }
        @Override public boolean areContentsTheSame(@NonNull Sales a, @NonNull Sales b) {
            return a.equals(b);
        }
    }

    public IncomePerMonthListAdapter() {
        super(new SalesDiff());
    }

    @Override public SalesViewHolder onCreateViewHolder(ViewGroup p, int v) {
        return SalesViewHolder.create(p);
    }

    @Override public void onBindViewHolder(SalesViewHolder h, int pos) {
        h.bind(getItem(pos));
    }
}
