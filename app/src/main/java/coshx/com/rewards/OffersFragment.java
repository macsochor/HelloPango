package coshx.com.rewards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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


public class OffersFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String TAG = "MainActivity";
    private ItemTouchHelper mItemTouchHelper;
    private DatabaseReference offerReference;
    public ArrayList<Offer> al_offers;
    boolean stars_active;
    boolean in_progress_active;
    FragmentTransaction ft;

    FirebaseUser user;
    FirebaseAuth auth;
    RecyclerView rv;


    public OffersFragment() {
        // Required empty public constructor
    }

    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_blank, container, false);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

//        TextView textView = (TextView) findViewById(R.id.test);

        al_offers = new ArrayList<>();

//        RecyclerView stuff
        rv = (RecyclerView) v.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(new MyItemRecyclerViewAdapter(al_offers));



//        ImageView iv_gif = (ImageView) findViewById(R.id.iv_profpic);
//
//        Picasso.with(this).load(user.getPhotoUrl().toString()).into(iv_gif);
        FirebaseMessaging.getInstance().subscribeToTopic("Offers");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("backend").child("offers");


//        SwipeDeck cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());
                al_offers.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Offer offer = postSnapshot.getValue(Offer.class);
                    Log.e("Get Data", offer.toString());
                    al_offers.add(offer);
                    rv.invalidate();
                }
                Collections.sort(al_offers, new OfferComparator());
                rv.setAdapter(new MyItemRecyclerViewAdapter(al_offers));

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void setAdatper(ArrayList<Offer> list){
        rv.setAdapter(new MyItemRecyclerViewAdapter(list));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
