package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.ComplaintList;
import UserJavaFiles.Cook;
import UserJavaFiles.User;

public class complaints_page extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listViewComplaints;
    List<Complaint> complaints;
    Complaint complaint;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Complaints");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);
        listViewComplaints = (ListView) findViewById(R.id.listViewComplaints);

        complaints = new ArrayList<>();
        String id = databaseReference.push().getKey();
        //creates a complaint since we dont have any atm
        complaint = new Complaint("everyone@gmail.com", "got food poisning lol", id);
        databaseReference.child(id).setValue(complaint);

        //remove between these comments once done with this temp
        //catching user information and loading it to page
        try{
            user = (Cook) getIntent().getSerializableExtra("Cook");
            user.getFirstName();
        }catch (Exception e){
            System.out.println("error here1 " + e);
            try{
                user = (Administrator) getIntent().getSerializableExtra("Admin");
                user.getFirstName();
            }catch (Exception g){
                System.out.println("error here2 " + e);

            }
        }
        listViewComplaints.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Complaint complaint = complaints.get(i);
                showResolveDialog(complaint.getId(),complaint.getEmail());
                return true;
            }
        });

    }
    //populate the list with complaints from the DB
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaints.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    if(user.getClass() == Cook.class && user.getEmail().equals(complaint.getEmail())){
                        complaints.add(complaint);
                    }

                    else if (user.getClass() == Administrator.class){
                        complaints.add(complaint);
                    }

                }
                ComplaintList complaintsAdapter = new ComplaintList(complaints_page.this, complaints);
                listViewComplaints.setAdapter(complaintsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showResolveDialog(final String complaintId, final String email) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_resolve_dialoge, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextLengthToSuspend);
        final Button buttonBan = (Button) dialogView.findViewById(R.id.buttonBan);
        final Button buttonResolve = (Button) dialogView.findViewById(R.id.buttonResolve);
        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.buttonSuspend);

        dialogBuilder.setTitle(email);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
            }
        });

        buttonResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComplaint(complaintId);
                b.dismiss();
            }
        });

        buttonSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "NOT IMPLEMENTED YET", Toast.LENGTH_LONG).show();
            }
        });

    }
    private Boolean deleteComplaint(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Complaint Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}