package com.example.wow;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by D on 1/28/2018.
 */

public class FirebaseClickedActivity extends BaseActivity{
    private String URL = FirebaseImageActivity.EXTRA_FIREBASE_IMAGE;
    private String NAME = FirebaseImageActivity.EXTRA__FIREBASE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebaseclicked);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(URL);
        String name = intent.getStringExtra(NAME);

        ImageView imageView = findViewById(R.id.image_firebase_view_click);
        TextView textView = findViewById(R.id.text_firebase_view_click);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textView.setText("NAME:  " + name);

    }
}
