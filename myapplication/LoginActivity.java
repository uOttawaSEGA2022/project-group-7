package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private static final String VALID_EMAIL_PATTERN = "^[a-z0-9._]+@[a-z0-9_.]+\\.[a-z0-9]+$";
    private static final Pattern pattern = Pattern.compile(VALID_EMAIL_PATTERN);
    Button btnLogin;
    EditText userName;
    EditText password;

    public LoginActivity(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        userName=(EditText)findViewById(R.id.edtUsername);
        password=(EditText)findViewById(R.id.edtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userName.getText().toString(),password.getText().toString());
            }
        });
    }
    public LoginActivity(Context context){

    }

    public String validate(String userName, String password)
    {
        if(userName.equals("admin") && password.equals("admin"))
            return "Login was successful";
        else
            return "Invalid login!";
    }

    public boolean isValidUserName(String username) {
        return pattern.matcher(username.trim()).find();
    }
}