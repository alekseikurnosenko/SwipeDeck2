package com.daprlabs.aaron.swipedeck2;

import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;
    private SwipeDeckAdapter adapter;
    private ArrayList<String> testData;
    private ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        flipper = (ViewFlipper) findViewById(R.id.flipper);

        testData = new ArrayList<>();

        adapter = new SwipeDeckAdapter(testData, this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + stableId);
            }

            @Override
            public void cardSwipedRight(long stableId) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + stableId);

            }
        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        Button btn = (Button) findViewById(R.id.button_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(180);

            }
        });
        Button btn2 = (Button) findViewById(R.id.button_right);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });

        Button btn3 = (Button) findViewById(R.id.button_center);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testData.add("0");
                testData.add("1");
                testData.add("2");
                testData.add("3");
                testData.add("4");
                // Show swipe deck
                flipper.setDisplayedChild(1);
                // With this one, app would crash with SIGSEGV as soon as we start to drag card
                // adapter.notifyDataSetChanged();
                // With this one, it's ok
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;

        public SwipeDeckAdapter(List<String> data, Context context) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.test_card2, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            Picasso.with(context).load(R.drawable.food).fit().centerCrop().into(imageView);
            TextView textView = (TextView) v.findViewById(R.id.sample_text);
            String item = (String)getItem(position);
            textView.setText(item);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    /*Intent i = new Intent(v.getContext(), BlankActivity.class);
                    v.getContext().startActivity(i);*/
                }
            });
            return v;
        }
    }
}
