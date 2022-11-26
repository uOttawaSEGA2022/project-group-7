package UserJavaFiles;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class Client extends User {
    private CreditCard creditCardInfo;

    //Creates Client from Super Class User, adds creditCardInfo attribute (class is set up but can be null for now
    public Client(String firstName,String lastName,String email,String password,String address, CreditCard creditCardInfo){
        super(firstName,lastName,email,password,address);
        this.creditCardInfo = creditCardInfo;
    }
    //getter and setter methods
    public CreditCard getCreditCardInfo() {
        return this.creditCardInfo;
    }

    public void setCreditCardInfo(CreditCard creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public boolean equalsTo(Client client){
        return this.getAddress() == client.getAddress() && this.getFirstName() == client.getFirstName() && this.getLastName() == client.getLastName()
                && this.getEmail() == client.getEmail() && this.getPassword() == client.getPassword() && this.creditCardInfo.equalsTo(client.getCreditCardInfo());
    }
    //adds order to the DB
    public void addOrder(String cookEmail, String userEmail, Meal meal){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference orderDB = firebaseDatabase.getReference("Orders");
        String id = orderDB.push().getKey();
        Order order = new Order(cookEmail,userEmail,meal,id);
        orderDB.push().setValue(order);
    }
    //removes order from database
    public void deleteOrder(Order order){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Order temp = new Order();
                for (DataSnapshot child: children){
                    temp = child.getValue(Order.class);
                    if (temp.equals(order)){
                        String id = child.getKey();
                        firebaseDatabase.getReference("Orders").child(id).removeValue();
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

}