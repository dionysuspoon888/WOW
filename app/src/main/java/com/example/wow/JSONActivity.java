package com.example.wow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

public class JSONActivity extends BaseActivity implements JsonRecyclerViewAdapter.OnItemClickListener {
    //DATE TRANSMITTED KEY
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";
    public static final String EXTRA_VIEWS = "viewCount";

    //RecyclerView setting
    private RecyclerView recyclerView;
    private JsonRecyclerViewAdapter adapter;
    private ArrayList<JSONItem> list;

    //Volley for JSON
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        //RecyclerView setting
        recyclerView = findViewById(R.id.recycler_view);
        //better performance
        recyclerView.setHasFixedSize(true);
        //grid view
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        list = new ArrayList<>();

        //Volley queue
        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        //JSON Link,q for search,type=photo
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=car&image_type=photo";
        //JSONRequest for later use
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //array that stores all the object in API Documentation
                            JSONArray jsonArray = response.getJSONArray("hits");
                            //Loop all the object of hit array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                //Extrieve what we want by Keys
                                String creatorName = hit.getString("user");
                                String imageUrl = hit.getString("webformatURL");
                                int likeCount = hit.getInt("likes");
                                int viewCount = hit.getInt("views");

                                list.add(new JSONItem(imageUrl, creatorName, likeCount,viewCount));
                            }

                            adapter = new JsonRecyclerViewAdapter(JSONActivity.this, list);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(JSONActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //Start the request through Volley
        requestQueue.add(request);
    }

    //Override OnClick method in JsonRecyclerViewAdapter
    @Override
    public void onItemClick(int position) {

        //Pass the Data by Intent to JSONClickedActivity
        Intent jsonClickedIntent = new Intent(this, JSONClickedActivity.class);
        JSONItem clickedItem = list.get(position);

        jsonClickedIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        jsonClickedIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        jsonClickedIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());
        jsonClickedIntent.putExtra(EXTRA_VIEWS, clickedItem.getViewCount());

        //Start JSONClickedActivity
        startActivity(jsonClickedIntent);
    }
}