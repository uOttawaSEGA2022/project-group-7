package UserJavaFiles;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * the client is simply a user who buys food. The client can complain and rate the cook
 * (rating is done outside this class) client has a credit card attached to differentiate
 * from the other classes that inherit from user
 */
public class Client extends User {
    private CreditCard creditCardInfo;

    public Client(String firstName,String lastName,String email,String password,String address, CreditCard creditCardInfo){
        super(firstName,lastName,email,password,address);
        this.creditCardInfo = creditCardInfo;
    }
    //getter and setter methods
    public CreditCard getCreditCardInfo() {
        return this.creditCardInfo;
    }
    public void setCreditCardInfo(CreditCard creditCardInfo) {this.creditCardInfo = creditCardInfo;}
    //equals to method
    public boolean equalsTo(Client client){
        return this.getAddress() == client.getAddress() && this.getFirstName() == client.getFirstName() && this.getLastName() == client.getLastName()
                && this.getEmail() == client.getEmail() && this.getPassword() == client.getPassword() && this.creditCardInfo.equalsTo(client.getCreditCardInfo());
    }

    /**
     * adds an order to the DATABASE, keep in mind an order is not attached to cook as per
     * decoupling! needs a cooks email, the clients email is autofilled as whoever the client
     * is who called this method. needs a proper meal as well to add onto, this is one of the
     * only ways to called the order classes constructor and as such creates an id by using
     * firebase
     * @param cookEmail
     * @param meal
     */
    public void addOrder(String cookEmail, Meal meal){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference orderDB = firebaseDatabase.getReference("Orders");
        String id = orderDB.push().getKey();
        Order order = new Order(cookEmail,this.getEmail(),meal,id);
        orderDB.child(id).setValue(order);
    }

    /**
     * Deletes an order from the DATABASE as per decoupling. This is a depricated method
     * that should not be used ignore only keeping just incase!!
     * @param order
     */
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

    /**
     * adds a complaint to the DB using the user class
     * @param complaint needs complaint with words
     * @param email needs cooks email specifically the complaint is being issued against
     */
    public void addComplaintDB(String complaint, String email){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference complaintsDB = firebaseDatabase.getReference("Complaints");
        String id = complaintsDB.push().getKey();

        Complaint complaintObj = new Complaint(email,complaint,id);
        complaintsDB.child(id).setValue(complaintObj);
    }

}