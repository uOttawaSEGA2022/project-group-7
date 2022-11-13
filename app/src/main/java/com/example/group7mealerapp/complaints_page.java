package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import UserJavaFiles.Administrator;
import UserJavaFiles.Complaint;
import codeModules.Modules;
import listViewFiles.ComplaintList;
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

    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Complaints");
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_page);
        listViewComplaints = (ListView) findViewById(R.id.listViewMeals);

        complaints = new ArrayList<>();
        String id = databaseReference.push().getKey();
        //creates a complaint since we dont have any atm
        /*complaint = new Complaint("cook@gmail.com", "there was rat in my soup!", id);
        databaseReference.child(id).setValue(complaint);*/

        //remove between these comments once done with this temp
        //catching user information and loading it to page
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

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
    /**
     * This method shows the resolution diolog when long clicked on a complaint (only useable if admin
     * allows the admin to take action against a user by either banning them, suspending them or simply resolving
     * the complaint if it is of no issue
     * @param complaintId complaint id in the database
     * @param email email of the cook being actioned against
     */
    private void showResolveDialog(final String complaintId, final String email) {
        //build the diologue
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_resolve_dialoge, null);
        dialogBuilder.setView(dialogView);

        //SETTING UP THE TEXT FIELDS AND BUTTONS
        final EditText editTextLengthToBan  = (EditText) dialogView.findViewById(R.id.editTextLengthToSuspend);
        final Button buttonBan = (Button) dialogView.findViewById(R.id.buttonBan);
        final Button buttonResolve = (Button) dialogView.findViewById(R.id.buttonPurchase);
        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.buttonSuspend);

        dialogBuilder.setTitle(email);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Suspension suspension = new Suspension(true,(Date) null);
                suspendOrBanUser(complaintId,suspension,email);
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
                Suspension suspension = null;
                Boolean flag = true;
                Context context = getApplicationContext();
                String error = "Incorrect date format";
                int duration = Toast.LENGTH_LONG;
                String day =editTextLengthToBan.getText().toString();

                try{
                    suspension = new Suspension(false,day);
                    System.out.println(suspension.getBannedUntil() + " you are banned until");
                }catch(ParseException e){
                    flag = false;
                    editTextLengthToBan.setError("incorrect date format");
                    Toast.makeText(context, error, duration).show();
                } catch (Exception e) {
                    flag = false;
                    editTextLengthToBan.setError("date you chose is before current date");
                    Toast.makeText(context, error, duration).show();
                }
                if(flag){
                    System.out.println(day);
                    suspendOrBanUser(complaintId,suspension,email);
                    finish();
                    b.dismiss();
                }


            }
        });

    }
    /**
     * simply deletes a complaint more so used for resolving complaints with no action to ban or
     * suspend
     * @param id the complaint being resolved in the DB
     * @return
     */
    private Boolean deleteComplaint(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Complaint Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
    /**
     * simply bans or suspends the user depending on the suspension variable, only useable if user on page is admin
     * and can call the admin command suspendCook
     * @param id the id position of the complaint in the database that is being viewed
     * @param suspension the type of suspension they will get either permanent or for a period
     */
    private void suspendOrBanUser(String id, Suspension suspension,String email) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Complaints").child(id);
        dR.removeValue();
        //only can ban if admin is logged so switch user to admin should
        //not throw error as only accessable by admin
        Administrator admin = (Administrator) user;
        admin.suspendCook(suspension,email);
        if(suspension.getPerma()){
            Toast.makeText(getApplicationContext(), "Cook banned", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Cook suspended", Toast.LENGTH_LONG).show();
        }

    }


}