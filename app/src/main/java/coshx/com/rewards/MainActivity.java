package coshx.com.rewards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;

import coshx.com.rewards.model.Offer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    OffersFragment offersFragment;
    FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView rv;
    public Fragment fragmet;
    public CardFragment cardFragment;
    public static final String TAG = "MainActivity";
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference offerReference;
    ArrayList<Offer> al_offers;
    boolean stars_active;
    boolean in_progress_active;
    FragmentTransaction ft;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        ImageButton imageButton = myToolbar.findViewById(R.id.action_bar_profile);
        ImageButton b_star = myToolbar.findViewById(R.id.action_bar_star);
        ImageButton b_logo = myToolbar.findViewById(R.id.action_bar_logo);

        Drawable active_topright = getResources().getDrawable(R.drawable.progress_active);
        Drawable inactive_topright = getResources().getDrawable(R.drawable.progress);
        Drawable active_toprleft = getResources().getDrawable(R.drawable.rewards_active);
        Drawable inactive_topleft = getResources().getDrawable(R.drawable.rewards);
        Drawable active_center = getResources().getDrawable(R.drawable.pango);
        Drawable inactive_center = getResources().getDrawable(R.drawable.pango);
        Drawable inactive_plus = getResources().getDrawable(R.drawable.add_card_pressed);
        Drawable active_plus = getResources().getDrawable(R.drawable.add_card);

        offersFragment = new OffersFragment();
        cardFragment = new CardFragment();

        fragmet = offersFragment;
        replaceFragment();

        imageButton.setOnClickListener(v -> {
            if(fragmet == offersFragment) {
//            Toast.makeText(this, "profile pressed", Toast.LENGTH_SHORT).show();
                imageButton.setImageDrawable(active_topright);
                b_star.setImageDrawable(inactive_topleft);
                b_logo.setImageDrawable(inactive_center);

                ArrayList<Offer> in_progress = new ArrayList<>();
                for (Offer o : offersFragment.al_offers) {
                    if (o.type.equals("progress")) {
                        in_progress.add(o);
                    }
                }
                offersFragment.setAdatper(in_progress);
            }
        });

        imageButton.setImageDrawable(inactive_topright);
        b_star.setOnClickListener(v1 -> {
            if(fragmet == offersFragment) {
                b_star.setImageDrawable(active_toprleft);
                imageButton.setImageDrawable(inactive_topright);
                b_logo.setImageDrawable(inactive_center);
                ArrayList<Offer> starred = new ArrayList<>();
                for (Offer o : offersFragment.al_offers) {
                    if (o.type.equals("reward")) {
                        starred.add(o);
                    }
                }
                offersFragment.setAdatper(starred);
            }
        });

        b_logo.setOnClickListener(v1 -> {
            if(fragmet == offersFragment) {
                b_logo.setImageDrawable(active_center);
                imageButton.setImageDrawable(inactive_topright);
                b_star.setImageDrawable(inactive_topleft);
                offersFragment.setAdatper(offersFragment.al_offers);
            }
        });


        BottomNavigationView bnv = findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_card:
                    fragmet = cardFragment;
                    imageButton.setImageDrawable(null);
                    b_star.setImageDrawable(active_plus);
                    break;
                case R.id.action_pango_home:
                    imageButton.setImageDrawable(inactive_topright);
                    b_star.setImageDrawable(inactive_topleft);
                    b_logo.setImageDrawable(active_center);
                    fragmet = offersFragment;
                    break;

            }
            replaceFragment();
            return true;
        });
    }

        public void replaceFragment(){
            //replacing the fragment
            if (fragmet != null) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fl, fragmet);
                ft.commit();
            }
        }

//        SwipeDeckAdapter adapter = new SwipeDeckAdapter(al_offers, this);
//
//        cardStack.setAdapter(adapter);
//
//        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
//            @Override
//            public void cardSwipedLeft(int position) {
//                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
//                al_offers.add(adapter.data.get(position%12));
//            }
//
//            @Override
//            public void cardSwipedRight(int position) {
//                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
//                al_offers.add(adapter.data.get(position%12));
//            }
//
//            @Override
//            public void cardsDepleted() {
//                Log.i("MainActivity", "no more cards");
//            }
//
//            @Override
//            public void cardActionDown() {
//
//            }
//
//            @Override
//            public void cardActionUp() {
//
//            }
//        });
//
//        Button b = (Button) findViewById(R.id.b_mainact);
//        b.setOnClickListener(v -> {
//            cardStack.invalidate();
//            Log.e(TAG, al_offers.toString());
//            adapter.notifyDataSetChanged();
//        });

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card:
                Toast.makeText(this, "jlasflsdf", Toast.LENGTH_SHORT).show();
                LinearLayout root_ll = findViewById(R.id.ll_root);
                root_ll.removeAllViews();
                View v = getLayoutInflater().inflate(R.layout.card_layout, null);
                root_ll.addView(v);
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                Toast.makeText(this, "profile icon pressed", Toast.LENGTH_SHORT).show();
//                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
