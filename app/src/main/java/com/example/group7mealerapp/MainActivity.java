package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    //Global variables
    EditText username = (EditText) findViewById(R.id.username);
    EditText password = (EditText) findViewById(R.id.password);
    TextView result;
    Button buttonLogin = (Button) findViewById(R.id.login_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin (View view)
    {
        String line = "";
        String splitter = ",";
        boolean found = false;
        int counter = 0;

        try {
            //Getting the path of the text file
            URL url = getClass().getResource("registration.txt");
            //Parsing the csv file using buffered reader
            BufferedReader reader = new BufferedReader(new FileReader(url.getPath()));
            while ((line = reader.readLine()) != null)
            {
                counter = 0;
                String[] user = line.split(splitter);

                //comparing entered username with usernames from the csv
                if (user[0].equals(username.getText().toString()))
                {
                    counter++;
                }

                //comparing entered password with passwords from the csv
                if (user[1].equals(password.getText().toString()))
                {
                    counter++;
                }

                //if both the username and password matched then the user is validated
                if (counter == 2 ) found = true;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}