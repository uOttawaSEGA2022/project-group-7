package UserJavaFiles;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

/**
 * the order class that is decoupled and is standalone through the use of the DB.
 * Simply contains the content of an order i.e the cooks email the users email the
 * state of the order (pending accepted or rejected) as well as meal and a boolean
 * that tracks if the order has been properly rated yet or not
 */
public class Order implements Serializable {
    private String cookEmail, userEmail, state, id;
    private Meal meal;
    private boolean isRated;
    private static String PENDING = "PENDING";
    private static String ACCEPTED = "ACCEPTED";
    private static String REJECTED = "REJECTED";

    /**
     * this constructor is only ever called by Firebase to create the object, other than that
     * it is never called outside of the user method create order. this is because create order
     * specifically sets an id in the user class, it also restricts a order being made without
     * being logged into the database first!
     * @param cookEmail
     * @param userEmail
     * @param meal
     * @param id id is created by firebase and used by us the make sure we have the proper order
     */
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

    /**
     * sets an orders state as in PENDING, ACCEPTED or REJECTED
     * @param order a reference to the Order, has to be here due to visibility when using FB
     * @param state state of the order
     */
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

    /**
     * sets the order to rated or not this method should logically be only called when
     * the order is not rated as this method does not set the order from rated to unrated
     * @param order boolean value, false if not rated true otherwise
     */
    public void setRatedState(Order order){
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(!this.isRated){
            this.isRated = true;
            map.put("isRated", true);
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

    //equals method
    public boolean equals(Order orderToCompareTo) {
        return orderToCompareTo.id.equals(this.id);
    }
}
