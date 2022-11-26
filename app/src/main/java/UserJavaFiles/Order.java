package UserJavaFiles;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {
    private String cookEmail, userEmail, state, id;
    private Meal meal;
    private boolean isRated;
    private static String PENDING = "PENDING";
    private static String ACCEPTED = "ACCEPTED";
    private static String REJECTED = "REJECTED";


    public Order(String cookEmail, String userEmail, Meal meal, String id) {
        this.cookEmail = cookEmail;
        this.userEmail = userEmail;
        this.meal = meal;
        this.isRated = false;
        this.state = PENDING;
        this.id = id;
    }
    public Order(){}
    //getters and setters
    public String getCookEmail() {return cookEmail;}
    public void setCookEmail(String cookEmail) {this.cookEmail = cookEmail;}
    public String getUserEmail() {return userEmail;}
    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
    public String getState() {return state;}
    public void setState(String state) {this.state = state;}
    public Meal getMeal() {return meal;}
    public void setMeal(Meal meal) {this.meal = meal;}
    public boolean isRated() {return isRated;}
    public void setRated(boolean rated) {isRated = rated;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    //setters for the DB
    //sets the state of the order either pending accepted or rejected
    public void setOrderState(Order order, String state){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(state == PENDING){
            this.state = PENDING;
            map.put("state",this.state);
        }
        else if(state == ACCEPTED){
            this.state = ACCEPTED;
            map.put("state",this.state);
        }
        else if(state == REJECTED){
            this.state = REJECTED;
            map.put("state",this.state);
        }
        else{
            return;
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Order temp = new Order();
                for (DataSnapshot child: children){
                    temp = child.getValue(Order.class);
                    if (temp.equals(meal)){
                        String id = child.getKey();
                        firebaseDatabase.getReference("Orders").child(id).updateChildren(map);
                        databaseReference.removeEventListener(this);
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
    //sets if the order has been rated or not in the DB
    public void setRatedState(Order order){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(!this.isRated){
            this.isRated = true;
            map.put("isRated", true);
        }

        else{
            this.isRated = false;
            map.put("isRated",false);
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Order temp = new Order();
                for (DataSnapshot child: children){
                    temp = child.getValue(Order.class);
                    if (temp.equals(meal)){
                        String id = child.getKey();
                        firebaseDatabase.getReference("Orders").child(id).updateChildren(map);
                        databaseReference.removeEventListener(this);
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


    public boolean equals(Order orderToCompareTo) {
        return orderToCompareTo.id.equals(this.id);
    }
}
