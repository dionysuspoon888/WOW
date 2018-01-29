package com.example.wow;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class FirebaseActivity extends BaseActivity {

    //ID for request
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button buttonChooseImage;
    private Button buttonUpload;
    private TextView textViewShowUploads;
    private EditText editTextFileName;
    private ImageView imageView;
    private ProgressBar progressBar;

    private Uri imageUri;

    //Firebase Storage &DB
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    //Control UploadTask
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        buttonChooseImage = findViewById(R.id.button_choose_image);
        buttonUpload = findViewById(R.id.button_firebase_upload);
        textViewShowUploads = findViewById(R.id.text_view_show_uploads);
        editTextFileName = findViewById(R.id.edit_text_file_name);
        imageView = findViewById(R.id.image_firebase_view);
        progressBar = findViewById(R.id.progress_firebase_bar);


        //Save it in "uploads" folder
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check UploadTask currently running
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(FirebaseActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        textViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
    }

    private void openFileChooser() {
        //Open File Chooser to choose image file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //Set ID PICK_IMAGE_REQUEST
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //load it to our apps wih ID PICK_IMAGE_REQUEST
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            Picasso.with(this).load(imageUri).into(imageView);
        }
    }
    //Get the File Extension e.g. jpeg,bmp
    private String getFileExtension(Uri uri) {

        ContentResolver cR = getContentResolver();
        //Mime=Multipurpose Internet Mail Extensions e.g.image/jpeg
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            //Create a name e.g. uploads/423423.jpg in Storage
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            //Upload the image to Storage
            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(FirebaseActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                            //Push Unique uploadID by getKey() to DB
                            String uploadID = databaseRef.push().getKey();

                            //Set up contructor,taskSnapshot.getDownloadUrl().toString()=Image Link (Storage)
                            FirebaseUploadItem upload= new FirebaseUploadItem(uploadID,editTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());

                            //Create entry with key ID,value upload(info)
                            databaseRef.child(uploadID).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Case false and error message
                            Toast.makeText(FirebaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //Progress Length
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    //Show upload folder
    private void openImagesActivity() {
        Intent intent = new Intent(this, FirebaseImageActivity.class);
        startActivity(intent);
    }
}