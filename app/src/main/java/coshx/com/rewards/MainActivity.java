package coshx.com.rewards;

import android.content.Context;
import android.media.Image;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import coshx.com.rewards.dummy.DummyContent;
import coshx.com.rewards.model.Offer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView rv;
    public static final String TAG = "MainActivity";
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference offerReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

//        TextView textView = (TextView) findViewById(R.id.test);

        //RecyclerView stuff
//        rv = (RecyclerView) findViewById(R.id.rv);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//        rv.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));

//        ImageView imageView = (ImageView) findViewById(R.id.iv_profpic);

//        Picasso.with(this).load(user.getPhotoUrl().toString()).into(imageView);
        FirebaseMessaging.getInstance().subscribeToTopic("Offers");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("backend").child("offers");
        ArrayList<Offer> al_offers = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Offer offer = postSnapshot.getValue(Offer.class);
                    Log.e("Get Data", offer.toString());
                    al_offers.add(offer);
                }

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        ImageButton imageButton = myToolbar.findViewById(R.id.action_bar_profile);

        imageButton.setOnClickListener(v -> {
            Toast.makeText(this, "profile pressed", Toast.LENGTH_SHORT).show();
        });

        SwipeDeck cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        final ArrayList<String> testData = new ArrayList<>();


        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(al_offers, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                testData.add(""+position%5);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                testData.add(""+position%5);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });

        Button b = (Button) findViewById(R.id.b_mainact);
        b.setOnClickListener(v -> {
            cardStack.invalidate();
            Log.e(TAG, al_offers.toString());
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_favorite:
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
