package com.ashanudeshitha.instaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mInstaList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mInstaList = (RecyclerView) findViewById(R.id.insta_list);
        mInstaList.setHasFixedSize(true);
        mInstaList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("InstaApp");

        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(MainActivity.this,RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <insta,instaViewHolder > FBRA = new FirebaseRecyclerAdapter<insta, instaViewHolder>(
                insta.class,
                R.layout.insta_row,
                instaViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(instaViewHolder viewHolder, insta model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplication(),model.getImage());
            }
        };
        mInstaList.setAdapter(FBRA);
    }

    public static class instaViewHolder extends RecyclerView.ViewHolder{
        public instaViewHolder (View itemView){
            super(itemView);
            View mView = itemView;
        }

        public void setTitle (String title){
            TextView post_title = (TextView) itemView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }
        public void setDesc (String desc){
            TextView post_desc = (TextView) itemView.findViewById(R.id.textDescription);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx , String image){
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_img);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.addIcon){
            Intent intent = new Intent(MainActivity.this,PostActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
