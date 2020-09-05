package com.ihfazh.dicodingmyquote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
    private ArrayList<QuoteData> quotes;

    public QuotesAdapter(ArrayList<QuoteData> quotes) {
        this.quotes = quotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuoteData quote = quotes.get(position);
        holder.tvAuthor.setText(quote.getAuthor());
        holder.tvQuote.setText(quote.getQuote());
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAuthor, tvQuote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuote = itemView.findViewById(R.id.tvQuote);

        }
    }
}
