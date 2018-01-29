package com.example.wow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



public class JSONClickedActivity extends BaseActivity {
    private String URL =JSONActivity.EXTRA_URL;
    private String LIKES = JSONActivity.EXTRA_LIKES;
    private String CREATOR = JSONActivity.EXTRA_CREATOR;
    private String VIEWS =JSONActivity.EXTRA_VIEWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonclicked);

        //Retrieve the Data in Intent

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(URL);
        String creatorName = intent.getStringExtra(CREATOR);
        int likeCount = intent.getIntExtra(LIKES, 0);
        int viewCount = intent.getIntExtra(VIEWS,0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes = findViewById(R.id.text_view_like_detail);
        TextView textView = findViewById(R.id.text_view_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Likes:   " + likeCount);
        textView.setText("Views:  "+viewCount);
    }
}

