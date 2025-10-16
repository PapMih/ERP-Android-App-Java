package projecterp.ViewHoldersAndAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import projecterp.Database.Entities.WarehouseItems;
import com.example.projecterp.R;

// RecyclerView adapter for the warehouse lists.Uses ListAdapter

public class NotPrintedListAdapter extends ListAdapter<WarehouseItems, NotPrintedListAdapter.NotPrintedViewHolder> {

    // ViewHolder keeps the refs to TextViews
    static class NotPrintedViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoryText;
        private final TextView colourText;
        private final TextView sizeText;
        private final TextView amountText;

        private NotPrintedViewHolder(View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.textViewCategory);
            colourText   = itemView.findViewById(R.id.textViewColour);
            sizeText     = itemView.findViewById(R.id.textViewSize);
            amountText   = itemView.findViewById(R.id.textViewAmount);
        }

        // Put one WarehouseItems object into the row
        void bind(WarehouseItems item) {
            categoryText.setText("Κατηγορία: " + item.getCategoryName());
            colourText.setText("Χρώμα: " + item.getColours());
            sizeText.setText("Μέγεθος: " + item.getSize());
            amountText.setText("Ποσότητα: " + item.getAmount());
        }

        // Helper for onCreateViewHolder
        static NotPrintedViewHolder create(ViewGroup parent) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_not_printed, parent, false);
            return new NotPrintedViewHolder(v);
        }
    }

    // Tells DiffUtil when rows/items changed
    public static class WarehouseDiff extends DiffUtil.ItemCallback<WarehouseItems> {
        @Override
        public boolean areItemsTheSame(@NonNull WarehouseItems a, @NonNull WarehouseItems b) {
            return a.getCategoryName().equals(b.getCategoryName()) &&
                    a.getColours().equals(b.getColours()) &&
                    a.getSize().equals(b.getSize());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WarehouseItems a, @NonNull WarehouseItems b) {
            return areItemsTheSame(a, b) ;
        }
    }

    public NotPrintedListAdapter(@NonNull DiffUtil.ItemCallback<WarehouseItems> diff) {
        super(diff);
    }

    @Override
    public NotPrintedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NotPrintedViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NotPrintedViewHolder holder, int pos) {
        WarehouseItems item = getItem(pos);
        if (item != null) holder.bind(item);
    }
}
