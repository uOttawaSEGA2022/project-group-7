package UserJavaFiles;

import android.content.Context;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

/**
 * the admin class only differentiates itself from user with its unique methods that it only
 * has access to, i.e suspending and banning a cook if need be
 */
public class Administrator extends User {
    public Administrator(String firstName,String lastName,String email,String password,String address) throws IllegalArgumentException{
        super(firstName,lastName,email,password,address);
    }

    /**
     * This method bans a Cook either permanently or until a specified date
     * When a Cook is banned, their 'status' attribute points to an object of type Suspension
     * Objects of type suspension specify if the ban is permanent or if it is time-set
     *
     * @param email Cook that is going to be banned from the database
     * @param s Suspension object that wil determine the ban of the Cook
     */
    public void suspendCook(Suspension s, String email)
    {
        //set the suspension object up
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("suspension",s);
        //from within the database set the cooks status
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        cookDatabaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                        firebaseDatabase.getReference("UserInfo").child(id).updateChildren(map);
                        cookDatabaseReference.removeEventListener(this);
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
    //equals to method
    public boolean equalsTo(Administrator admin){
        return this.getAddress() == admin.getAddress() && this.getFirstName() == admin.getFirstName() && this.getLastName() == admin.getLastName()
                && this.getEmail() == admin.getEmail() && this.getPassword() == admin.getPassword();
    }
}
