package coshx.com.rewards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DealDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            TextView tv = (TextView) findViewById(R.id.tv_details_title);
            tv.setText(extras.getString("title", "Q_Q"));
        }
    }
}
