package coshx.com.rewards;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import coshx.com.rewards.dummy.DummyContent.DummyItem;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public MyItemRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position % 2 == 0){
            if (holder.ll.getChildCount() > 1)
            holder.ll.removeViewAt(1);
        }

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        Typeface font = Typeface.createFromAsset(holder.mIdView.getContext().getAssets(), "sf-pro-display-regular.otf");
        holder.mIdView.setTypeface(font);

        holder.mView.setOnClickListener(v -> {
            Toast.makeText(holder.mIdView.getContext(), "onClick " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), DealDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final CardView cardView;
        public DummyItem mItem;
        public LinearLayout ll;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
            cardView = view.findViewById(R.id.card_view);
            ll = (LinearLayout) view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
