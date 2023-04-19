package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText  username, password  ;
    Button btnLogin ;

    DbHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(EditText) findViewById(R.id.usernameLogin) ;
        password = (EditText) findViewById(R.id.passwordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
         myDB = new DbHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  user = username.getText().toString() ;
                String pass = password.getText().toString() ;


                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter Credentials", Toast.LENGTH_SHORT).show();
                }


                else{
                   Boolean res =  myDB.checkUsernamePassword(user,pass);
                    if(res){
                        Intent intent1 =  new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();


                    }

                }




            }
        });
    }
}