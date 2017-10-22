package coshx.com.rewards;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coshx.com.rewards.model.CardTransaction;

/**
 * Created by MSMD on 10/22/17.
 */

public class CardTransactionAdapter extends RecyclerView.Adapter<CardTransactionAdapter.ViewHolder> {

    private final List<CardTransaction> transactions;
    public static final String TAG = "MyItemAdapter";

    public CardTransactionAdapter(List<CardTransaction> items) {
        transactions = items;
        transactions.add(new CardTransaction("1", "2", "3"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_transaction, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        final int position = pos;
        Log.e(TAG, ""+position + "       " + transactions.get(position));
        Context context = holder.mIdView.getContext();
        CardTransaction ct = transactions.get(pos);
        holder.mContentView.setText(ct.getAmount());
        holder.mIdView.setText(ct.getPurchase());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = mView.findViewById(R.id.tv_transaction_business);
            mContentView = mView.findViewById(R.id.tv_transaction_price);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

