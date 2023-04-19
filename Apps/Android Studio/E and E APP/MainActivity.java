package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText password1, repassword1 , username1;
    Button buttonLogin, buttonRegister;
    DbHelper Db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username1 = (EditText) findViewById(R.id.username1);
        password1 = (EditText)findViewById(R.id.password1);
        repassword1 = (EditText)findViewById(R.id.repassword1);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setWidth(100);
        Db =new DbHelper(this) ;



        buttonRegister.setOnClickListener(view -> {
            String user  =  username1.getText().toString();
            String pass  = password1.getText().toString();
            String repass = repassword1.getText().toString();

            if(user.equals("") || pass.equals("") || repass.equals("")){
                Toast.makeText(MainActivity.this, "enter details", Toast.LENGTH_SHORT).show();
            }

            else
            {

                if(pass.equals(repass)){
                    Boolean usercheckResult = Db.checkusername(user);

                    if(usercheckResult == false){
                        Boolean regResult =  Db.insertData(user,pass);
                        if(regResult == true){
                            Toast.makeText(MainActivity.this, "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();



                }

                else
                    Toast.makeText(MainActivity.this, "Password and Repassword must be same", Toast.LENGTH_SHORT).show();




            }

        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        }) ;



    }


}