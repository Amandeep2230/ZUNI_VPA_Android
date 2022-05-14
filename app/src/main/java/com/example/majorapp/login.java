package com.example.majorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    EditText username, underpass;
    Button button,button2;
    TextInputLayout u_ip_layout;
    TextInputLayout p_ip_layout;
    SQLiteHelper db;

    @Override
    protected void onStart() {
        super.onStart();
        Session session = new Session(login.this);
       int userID = session.getSession();
        if(userID!=-1){
           Intent intent=new Intent(login.this, Chatbot.class);
            startActivity(intent);
        }
        else{
            //nothing
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        username = (EditText)findViewById(R.id.username);
        underpass = (EditText)findViewById(R.id.underpass);
        u_ip_layout = (TextInputLayout)findViewById(R.id.u_ip_layout);
        p_ip_layout = (TextInputLayout)findViewById(R.id.p_ip_layout) ;
        button = (Button)findViewById(R.id.btn_login);
        button2 = (Button)findViewById(R.id.btn_signup);
        db = new SQLiteHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    //Get values from EditText fields
                    String Email = username.getText().toString();
                    String Password = underpass.getText().toString();

                    //Authenticate user
                    User currentUser = db.Authenticate(new User(0, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(button, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
                        Session session = new Session(login.this);
                        session.saveSession(currentUser);
                        //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(login.this, Chatbot.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {

                        //User Logged in Failed
                        Snackbar.make(button, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Signup.class);
                startActivity(intent);
            }
        });
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = username.getText().toString();
        String Password = underpass.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            u_ip_layout.setError("Please enter valid email!");
        } else {
            valid = true;
            u_ip_layout.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            p_ip_layout.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
                p_ip_layout.setError(null);
            } else {
                valid = false;
                p_ip_layout.setError("Password is to short!");
            }
        }

        return valid;
    }

}