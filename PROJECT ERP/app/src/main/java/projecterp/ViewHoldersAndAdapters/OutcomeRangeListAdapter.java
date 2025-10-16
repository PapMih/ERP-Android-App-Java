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

import projecterp.Models.OutcomeRow;

public class OutcomeRangeListAdapter
        extends ListAdapter<OutcomeRow, OutcomeRangeListAdapter.VH> {

    public OutcomeRangeListAdapter() { super(DIFF); }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_outcome_per_month, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        OutcomeRow row = getItem(pos);

        h.tvDate.setText(row.getDate());
        h.tvTitle.setText(row.getTitle());

        if (row.isPurchase()) {
            h.tvExtra.setVisibility(View.VISIBLE);
            h.tvExtra.setText(row.getExtraInfo());
            h.tvAmount.setVisibility(View.VISIBLE);
            h.tvAmount.setText("Qty: " + row.getAmount());
        } else {
            h.tvExtra.setVisibility(View.GONE);
            h.tvAmount.setVisibility(View.GONE);
        }

        double euros = row.getTotalCostCents() / 100.0;
        h.tvCost.setText(String.format(Locale.getDefault(), "Cost: %.2f €", euros));
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvDate, tvTitle, tvExtra, tvAmount, tvCost;
        VH(View v) {
            super(v);
            tvDate   = v.findViewById(R.id.textViewDate);
            tvTitle  = v.findViewById(R.id.textViewTitle);
            tvExtra  = v.findViewById(R.id.textViewExtra);
            tvAmount = v.findViewById(R.id.textViewAmount);
            tvCost   = v.findViewById(R.id.textViewCost);
        }
    }

    private static final DiffUtil.ItemCallback<OutcomeRow> DIFF =
            new DiffUtil.ItemCallback<OutcomeRow>() {
                @Override
                public boolean areItemsTheSame(@NonNull OutcomeRow a, @NonNull OutcomeRow b) {
                    return a.getDate().equals(b.getDate()) &&
                            a.getTitle().equals(b.getTitle()) &&
                            a.getExtraInfo().equals(b.getExtraInfo());
                }
                @Override
                public boolean areContentsTheSame(@NonNull OutcomeRow a, @NonNull OutcomeRow b) {
                    return a.getTotalCostCents() == b.getTotalCostCents() &&
                            a.getAmount() == b.getAmount();
                }
            };
}
