package com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing.R.*;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener{
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";


    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;
    private ArrayList<ItemDetail> mItemList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        mRecyclerView = findViewById(id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<ItemDetail>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=9745639-19ef2fadb8edd9c8f561f20c7&q=cars&image_type=photo&pretty=true";

        // Get the first JSONObject
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");

                            // Go through the array to fetch the Creator's Name, the Like Count and the Image URL
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                //Get CreatorName from hit JSON Object
                                String creatorName = hit.getString("user");

                                // Get the image Url
                                String imageUrl = hit.getString("webformatURL");

                                // Get the Like Count
                                int likeCount = hit.getInt("likes");

                                // This will construct the view for each image and its details
                                mItemList.add(new ItemDetail(imageUrl, creatorName, likeCount));

                            }

                            // This will take care of parsing the JSON data into our ItemList
                            // and then set the adapter into our recycler view
                            mItemAdapter = new ItemAdapter(MainActivity.this, mItemList);
                            mRecyclerView.setAdapter(mItemAdapter);

                            // Set a ClickListener
                            mItemAdapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() { //Error response in case Volley can fetch the JSON Object

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        // Add the request to our requestQueue
        mRequestQueue.add(request);

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ItemDetail clickedItem = mItemList.get(position);

        // Fetch appropriate variable for appropriate image and send into Intent
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }
}
