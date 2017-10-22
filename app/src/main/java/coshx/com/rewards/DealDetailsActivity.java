package coshx.com.rewards;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DealDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);
        Bundle extras = getIntent().getExtras();
        TextView freecoffee = findViewById(R.id.freecoffee);
        TextView pb1 = findViewById(R.id.tv_purchase_business1);
        TextView pb2 = findViewById(R.id.tv_purchase_business2);
        TextView pb3 = findViewById(R.id.tv_purchase_business3);
        TextView pb4 = findViewById(R.id.tv_purchase_business4);


        Typeface font = Typeface.createFromAsset(getAssets(), "poppins-bold.ttf");

        pb1.setTypeface(font);
        pb2.setTypeface(font);
        pb3.setTypeface(font);
        pb4.setTypeface(font);
        freecoffee.setTypeface(font);

    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
        super.onBackPressed();
    }
}
