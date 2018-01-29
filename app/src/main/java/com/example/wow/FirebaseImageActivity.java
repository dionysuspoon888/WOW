package com.example.wow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseImageActivity extends BaseActivity implements FirebaseImageAdapter.OnItemClickListener {



    //For Click Event ID
    public static final String EXTRA__FIREBASE_NAME = "firebaseName";
    public static final String EXTRA_FIREBASE_IMAGE = "firebaseImage";


    private RecyclerView recyclerView;
    private FirebaseImageAdapter adapter;

    //Loading Page=Progress Bar
    private ProgressBar progressCircle;

    private DatabaseReference databaseRef;
    private List<FirebaseUploadItem> uploads;

    //Control Change
    private int mChange;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);


        recyclerView = findViewById(R.id.recycler_firebase_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressCircle = findViewById(R.id.progress_firebase_circle);

        uploads = new ArrayList<>();

        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        databaseRef.addValueEventListener(new ValueEventListener() {
            //Retrieve data from DB to Recycler view
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                //Look for all children under "Uploads" folder -> postSnapshot
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Control Change
                    if(mChange==0) {
                        //getValue get the Object and Save it to upload,holds the name and imageUrl(Firebase Storage)
                        FirebaseUploadItem upload = postSnapshot.getValue(FirebaseUploadItem.class);

                        //getKey() gets uploadID  for manipulation of DB
                        upload.setID(postSnapshot.getKey());

                        //Add it to List
                        uploads.add(upload);
                    }
                }
                //Control Change
                if(mChange==0) {
                    //Pass it to Adapter &ReyclerView
                    adapter = new FirebaseImageAdapter(FirebaseImageActivity.this, uploads);

                    recyclerView.setAdapter(adapter);
                }

                //Set up Custom OnItemClickListener
                adapter.setOnItemClickListener(FirebaseImageActivity.this);

                //Loading Page=Progress Bar,INVISIBLE when Successful
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FirebaseImageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //Loading Page=Progress Bar,INVISIBLE when Successful
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    //Override the interface of Custom OnItemClickListener
    @Override
    public void getIDHint(int position) {
        FirebaseUploadItem firebaseItem= uploads.get(position);
        String key=firebaseItem.getID();

        Toast.makeText(this, "ID in DB: "+key+". Please Try Long Click for Menu:Zoom and Delete" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getZoom(int position) {

        Intent firebaseClickedIntent = new Intent(this, FirebaseClickedActivity.class);
        FirebaseUploadItem firebaseClickedItem = uploads.get(position);
        firebaseClickedIntent.putExtra(EXTRA_FIREBASE_IMAGE, firebaseClickedItem.getImageUrl());
        firebaseClickedIntent.putExtra(EXTRA__FIREBASE_NAME, firebaseClickedItem.getName());
        startActivity(firebaseClickedIntent);

    }

    @Override
    public void deleteItem(int position) {
        mChange=1;
        FirebaseUploadItem firebaseDeletedItem = uploads.get(position);
        String uploadID=firebaseDeletedItem.getID();
        String imageURL=firebaseDeletedItem.getImageUrl();
        storageRef=FirebaseStorage.getInstance().getReferenceFromUrl(imageURL);

        databaseRef.child(uploadID).removeValue();//Remove it from DB
        storageRef.delete();//Delete it from Storage

        uploads.remove(position);//Remove it from the List (otherwise,it would keep adding to the  RecyclerView)
        adapter.notifyItemRemoved(position);//Remove it from the RecyclerView


        Toast.makeText(this, "Delete item from Firebase DB,Storage", Toast.LENGTH_SHORT).show();
    }
}