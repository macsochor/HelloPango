package coshx.com.rewards;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import coshx.com.rewards.R;
import coshx.com.rewards.model.Offer;

/**
 * Created by MSMD on 10/21/17.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    public List<Offer> data;
    private Context context;

    public SwipeDeckAdapter(List<Offer> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            // normally use a viewholder
            v = inflater.inflate(R.layout.test_card, parent, false);
        }
        Offer offer = data.get(position);
        ((TextView) v.findViewById(R.id.textView2)).setText(data.get(position).merchantName);
        Picasso.with(v.getContext()).load(offer.merchantLogoUrl).into((ImageView) v.findViewById(R.id.iv_tinder_card));


        v.setOnClickListener(v1 -> {
            Offer o = data.get(position);
            Log.i("MainActivity", o.toString());
            Intent intent = new Intent(v1.getContext(), DealDetailsActivity.class);
            intent.putExtra("title", o.getMerchantName());
            v1.getContext().startActivity(intent);
        });

        return v;
    }
}
