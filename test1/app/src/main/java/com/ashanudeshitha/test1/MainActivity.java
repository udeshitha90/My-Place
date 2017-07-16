package com.ashanudeshitha.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    public void login(View view){
//        EditText username = (EditText)findViewById(R.id.username);
//        EditText password = (EditText)findViewById(R.id.password);
//        Log.i("Username",username.getText().toString());
//        Log.i("Password",password.getText().toString());
//
//        Toast toast = Toast.makeText(getApplicationContext(), username.getText().toString(),Toast.LENGTH_LONG);
//        toast.show();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnclick ();
    }

    Button btn ;

    public void btnclick (){
        btn= (Button) findViewById(R.id.btnlog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,test.class);
                startActivity(i);
            }
        });
    }
}
