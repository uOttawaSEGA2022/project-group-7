package UserJavaFiles;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.Serializable;

/**
 * The rating class creates a rating object with only the email of the cook and the rating
 * the reason this is a seperate class is to reduce decoupling but also becuase
 * in order to get the total rating one must average out all pre existing ratings into one
 * total. This class is mainly used to push to the database in firebase and nothing else
 */
public class Rating implements Serializable {
    //instance variables
    private double rating;
    private String email;
    //constructors
    public Rating(double rating, String email){
        // check if rating is btwn 5 and 0
        if(rating>5 || rating <0){
            throw new IllegalArgumentException();
        }
        else{
            this.rating = rating;
            this.email = email;
        }
    }
    //for FireBase
    public Rating(){}
    //getters and setters
    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    /**
     * Method that pushes rating to the DB
     */
    public void setRatingDB() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Ratings");
        Rating rating = new Rating(this.rating, this.email);
        databaseReference.push().setValue(rating);
    }
}
