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
        Drawable progress_drawable = context.getDrawable(R.drawable.progress_card);
        Drawable rewards_drawable = context.getDrawable(R.drawable.rewards_card);

        holder.tv_price.setText("$"+offer.getAmount());
        if(offer.getType().equals("offer")){
            Glide.with(context).load(offer.getMerchantLogoUrl()).into(holder.iv_topleft_icon);
            holder.gradient.setBackground(ad_gradient);
            holder.iv_gif.setBackground(null);
            holder.tv_brandname.setText("");
            holder.mIdView.setTextColor(context.getResources().getColor(R.color.black));
            holder.mContentView.setTextColor(context.getResources().getColor(R.color.black));
            holder.iv_notad.setVisibility(View.GONE);
            holder.tv_price.setText("");
        }
        else if (offer.getType().equals("progress")){
            holder.iv_mega.setVisibility(View.GONE);
            holder.tv_sponsor.setVisibility(View.GONE);
            holder.gradient.setBackground(progress_gradient);
            holder.tv_brandname.setText(offer.merchantName);
            holder.tv_price.setText("");
            Glide.with(context).load(offer.getBackgroundUrl()).into(holder.iv_gif);
            holder.iv_notad.setImageDrawable(context.getResources().getDrawable(R.drawable.progress_card));
        } else/* (offer.getType().equals("reward"))*/{
            Glide.with(context).load(offer.getBackgroundUrl()).into(holder.iv_gif);
            holder.tv_brandname.setText(offer.merchantName);
            holder.iv_mega.setVisibility(View.GONE);
            holder.tv_sponsor.setVisibility(View.GONE);
            holder.gradient.setBackground(reward_gradient);
            holder.iv_notad.setImageDrawable(context.getResources().getDrawable(R.drawable.rewards_card));
        }

        if (position == getItemCount()-1){
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(16, 16, 16, 64);
            holder.ll.setLayoutParams(llp);
        }
        holder.mItem = offers.get(position);
        holder.mIdView.setText(offers.get(position).title);
        holder.mContentView.setText(offers.get(position).subtitle);
        Typeface font = Typeface.createFromAsset(holder.mIdView.getContext().getAssets(), "poppins-bold.ttf");
        holder.mIdView.setTypeface(font);

        holder.mView.setOnClickListener(v -> {
//            Toast.makeText(holder.mIdView.getContext(), "onClick " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), DealDetailsActivity.class);

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
        public final ImageView iv_notad;
        public final View gradient;
        public final TextView tv_brandname;
        public final TextView tv_sponsor;
        public final ImageView iv_mega;
        public final TextView tv_price;
        public Offer mItem;
        public LinearLayout ll;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tv_brandname = view.findViewById(R.id.tv_merchname);
            gradient = view.findViewById(R.id.card_gradient);
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
            cardView = view.findViewById(R.id.card_view);
            iv_gif = view.findViewById(R.id.iv_gif);
            tv_sponsor = view.findViewById(R.id.tv_sponsor);
            tv_price = view.findViewById(R.id.tv_price);
            iv_mega = view.findViewById(R.id.iv_megaphone);
            iv_topleft_icon = view.findViewById(R.id.iv_topleft_icon);
            iv_notad = view.findViewById(R.id.iv_notad_icon);
            ll = (LinearLayout) view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
