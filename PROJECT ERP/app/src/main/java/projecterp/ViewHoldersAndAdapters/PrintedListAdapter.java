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

import projecterp.Database.Entities.Products;
import projecterp.Database.Entities.WarehouseItems;

public class PrintedListAdapter extends ListAdapter<Products, PrintedListAdapter.PrintedViewHolder> {

    static class PrintedViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryText;
        private final TextView colourText;
        private final TextView sizeText;
        private final TextView amountText;
        private final TextView designText;
        private final TextView storeLocationText;

        private PrintedViewHolder(View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.textViewCategory);
            colourText = itemView.findViewById(R.id.textViewColour);
            sizeText = itemView.findViewById(R.id.textViewSize);
            amountText = itemView.findViewById(R.id.textViewAmount);
            designText = itemView.findViewById(R.id.textViewDesign);
            storeLocationText = itemView.findViewById(R.id.textViewStore);
        }

        // Put one Products object into the row
        void bind(Products item) {
            categoryText.setText("Κατηγορία: " + item.getCategoryName());
            colourText.setText("Χρώμα: " + item.getColours());
            sizeText.setText("Μέγεθος: " + item.getSize());
            amountText.setText("Ποσότητα: " + item.getAmount());
            designText.setText("Σχέδιο: " + item.getDesign());
            storeLocationText.setText("Σχέδιο: " + item.getStoreLocation());
        }

        // Helper for onCreateViewHolder
        static PrintedListAdapter.PrintedViewHolder create(ViewGroup parent) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_printed, parent, false);
            return new PrintedListAdapter.PrintedViewHolder(v);
        }
    }

        // Tells DiffUtil when rows/items changed
        public static class ProductsDiff extends DiffUtil.ItemCallback<Products> {
            @Override
            public boolean areItemsTheSame(@NonNull Products a, @NonNull Products b) {
                return a.getCategoryName().equals(b.getCategoryName()) &&
                        a.getColours().equals(b.getColours()) &&
                        a.getSize().equals(b.getSize()) &&
                        a.getStoreLocation().equals(b.getStoreLocation()) &&
                        a.getDesign().equals(b.getDesign());
            }
            @Override
            public boolean areContentsTheSame(@NonNull Products a, @NonNull Products b) {
                return areItemsTheSame(a, b);
            }
        }

        public PrintedListAdapter(@NonNull DiffUtil.ItemCallback<Products> diff) {
            super(diff);
        }

        @Override
        public PrintedListAdapter.PrintedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return PrintedListAdapter.PrintedViewHolder.create(parent);
        }

    @Override
    public void onBindViewHolder(PrintedListAdapter.PrintedViewHolder holder, int pos) {
        Products item = getItem(pos);
        if (item != null) holder.bind(item);
    }
}
