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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.ComplaintList;
import UserJavaFiles.Cook;
import UserJavaFiles.Suspension;
import UserJavaFiles.User;
import UserJavaFiles.UserPOJO;

public class complaints_page extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, cookDatabaseReference;
    ListView listViewComplaints;
    List<Complaint> complaints;
    Complaint complaint;
    User user;
    //simply find the cook by their email in the user database and set the reference to them
    private void findCookEmail(String email){
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        cookDatabaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Context context = getApplicationContext();
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                UserPOJO temp = new UserPOJO();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){
                    //no logic just stores the value onto user
                    temp = child.getValue(UserPOJO.class);
                    //comparing the email and password from the database with the inputted text fields
                    if (temp.getEmail().equals(email)){
                        String id = child.getKey();
                        System.out.println(id + "this is the proper id");
                        cookDatabaseReference = firebaseDatabase.getReference("UserInfo").child(id);
                        System.out.println("this is the email" + temp.getEmail());
                        break;
                    }
                    temp = null;
                }
            }
            //no need for this function but must be overridden
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
}
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Complaints");
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);
        listViewComplaints = (ListView) findViewById(R.id.listViewComplaints);

        complaints = new ArrayList<>();
        String id = databaseReference.push().getKey();
        //creates a complaint since we dont have any atm
        /*complaint = new Complaint("nocturne@gmail.com", "he parry striked me", id);
        databaseReference.child(id).setValue(complaint);
        */
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
                //only able to administer if you are an admin
                if(user.getClass() == Administrator.class){
                    Complaint complaint = complaints.get(i);
                    showResolveDialog(complaint.getId(),complaint.getEmail());
                    return true;
                }
                return false;
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
        findCookEmail(email);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_resolve_dialoge, null);
        dialogBuilder.setView(dialogView);


        final EditText editTextLengthToBan  = (EditText) dialogView.findViewById(R.id.editTextLengthToSuspend);
        final Button buttonBan = (Button) dialogView.findViewById(R.id.buttonBan);
        final Button buttonResolve = (Button) dialogView.findViewById(R.id.buttonResolve);
        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.buttonSuspend);

        dialogBuilder.setTitle(email);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Suspension suspension = new Suspension(true,(Date) null);
                suspendOrBanUser(complaintId,suspension);
                finish();
                b.dismiss();
            }
        });

        buttonResolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComplaint(complaintId);
                finish();
                b.dismiss();
            }
        });

        buttonSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length =Integer.parseInt(editTextLengthToBan.getText().toString());
                Calendar c = Calendar.getInstance(); // starts with today's date and time
                c.add(Calendar.DAY_OF_YEAR, length);  // advances day by 2
                Date date = c.getTime(); // gets modified time
                System.out.println(date);
                Suspension suspension = new Suspension(false,date);
                suspendOrBanUser(complaintId,suspension);
                finish();
                b.dismiss();
            }
        });

    }
    private Boolean deleteComplaint(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Complaint Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
    private void suspendOrBanUser(String id, Suspension suspension) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
        dR.removeValue();
        //only can ban if admin is logged so switch user to admin should
        //not throw error as only accessable by admin
        Administrator admin = (Administrator) user;
        admin.suspendCook(suspension,cookDatabaseReference);
        if(suspension.getPerma()){
            Toast.makeText(getApplicationContext(), "Cook banned", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Cook suspended", Toast.LENGTH_LONG).show();
        }

    }


}