package com.ashanudeshitha.instaapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST =1;
    private Uri uri = null;
    private ImageButton imageButton;
    private EditText editName;
    private EditText editDesc;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        editName = (EditText) findViewById(R.id.editName);
        editDesc = (EditText) findViewById(R.id.editDesc);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = database.getInstance().getReference().child("InstaApp");
     }
    public void imageButtonClicked(View view){
        Intent imgintent = new Intent(new Intent("android.intent.action.GET_CONTENT")).setType("image/*");
        //imgintent.setType("Image/*");
        startActivityForResult(imgintent,GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            uri = data.getData();
            Log.i("URI", uri.toString());
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageURI(uri);

        }
    }
    public void submitButtonClicked(View view){
        final String titleVallue = editName.getText().toString().trim();
        final String descValue = editDesc.getText().toString().trim();
        if(!TextUtils.isEmpty(titleVallue) && !TextUtils.isEmpty(descValue)){
            StorageReference filePath = storageReference.child("PostImage").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"Upload Complete",Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("title").setValue(titleVallue);
                    newPost.child("desc").setValue(descValue);
                    newPost.child("image").setValue(downloadurl.toString());
                }
            });
        }

    }
}
