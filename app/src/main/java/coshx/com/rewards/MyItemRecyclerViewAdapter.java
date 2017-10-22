package coshx.com.rewards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import coshx.com.rewards.model.Offer;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Offer> offers;
    public static final String TAG = "MyItemAdapter";

    public MyItemRecyclerViewAdapter(List<Offer> items) {
        offers = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        final int position = pos;
        Log.e(TAG, ""+position + "       " + offers.get(position));
        Context context = holder.mIdView.getContext();
        Offer offer = offers.get(position);
        Drawable ad_gradient = context.getDrawable(R.drawable.ad_gradient);
        Drawable reward_gradient = context.getDrawable(R.drawable.reward_gradient);
        Drawable progress_gradient = context.getDrawable(R.drawable.progress_gradient);


        if(offer.getType().equals("offer")){
            Glide.with(context).load(offer.getMerchantLogoUrl()).into(holder.iv_topleft_icon);
            holder.gradient.setBackground(ad_gradient);
            holder.iv_gif.setBackground(null);
            holder.mIdView.setTextColor(context.getResources().getColor(R.color.black));
            holder.mContentView.setTextColor(context.getResources().getColor(R.color.black));
        }
        else if (offer.getType().equals("progress")){
            holder.iv_mega.setVisibility(View.GONE);
            holder.tv_sponsor.setVisibility(View.GONE);
            holder.gradient.setBackground(progress_gradient);
            Glide.with(context).load(offer.getBackgroundUrl()).into(holder.iv_gif);
        } else/* (offer.getType().equals("reward"))*/{
            Glide.with(context).load(offer.getBackgroundUrl()).into(holder.iv_gif);
            holder.iv_mega.setVisibility(View.GONE);
            holder.tv_sponsor.setVisibility(View.GONE);
            holder.gradient.setBackground(reward_gradient);
        }


        holder.mItem = offers.get(position);
        holder.mIdView.setText(offers.get(position).merchantName);
        holder.mContentView.setText(offers.get(position).title);
        Typeface font = Typeface.createFromAsset(holder.mIdView.getContext().getAssets(), "sf-pro-display-regular.otf");
        holder.mContentView.setTypeface(font);

        holder.tv_sponsor.setTypeface(font);

        holder.mView.setOnClickListener(v -> {
            Toast.makeText(holder.mIdView.getContext(), "onClick " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), DealDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final CardView cardView;
        public final ImageView iv_gif;
        public final ImageView iv_topleft_icon;
        public final View gradient;
        public final TextView tv_sponsor;
        public final ImageView iv_mega;
        public Offer mItem;
        public LinearLayout ll;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            gradient = view.findViewById(R.id.card_gradient);
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
            cardView = view.findViewById(R.id.card_view);
            iv_gif = view.findViewById(R.id.iv_gif);
            tv_sponsor = view.findViewById(R.id.tv_sponsor);
            iv_mega = view.findViewById(R.id.iv_megaphone);
            iv_topleft_icon = view.findViewById(R.id.iv_topleft_icon);
            ll = (LinearLayout) view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
