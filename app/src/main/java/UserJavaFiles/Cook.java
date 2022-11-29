package UserJavaFiles;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Cook class that simply stores information relevant to a cook such as the blank cheque, description
 * and status of the cooks account.
 */
public class Cook extends User{
    //instance variables
    private String blankCheque;
    private String description;
    private Suspension status;

    //constructor
    public Cook(String firstName,String lastName,String email,String password,String address, String description,Suspension status, String blankCheque){
        super(firstName,lastName,email,password,address);
        this.description = description;
        this.blankCheque = blankCheque;
        this.status = status;
    }

    //getters and setters
    public String getBlankCheque() {return this.blankCheque;}
    public String getDescription() {return description;}
    public Suspension getSuspension() {return status;}
    public void setBlankCheque(String blankCheque) {this.blankCheque = blankCheque;}
    public void setDescription(String description) {this.description = description;}
    public void setSuspension(Suspension suspend){this.status = suspend;}


    /**
     * adds a meal to the database of meals. Meals is decoupled from cook and as such you only access
     * the menu when the cook is on the view menu screen which has no relation to cook
     * @param name
     * @param mealType
     * @param cusineType
     * @param description
     * @param price
     * @param isActive
     */
    public void addMeal(String name, String mealType, String cusineType, String description, double price, boolean isActive)
    {
        //calling the exists method to check if the meal is already in the menu if it is
        // then nothing is added to menu
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Meals");
        // if flag is -1 then it does not exist in the menu
        Meal meal = new Meal(name,mealType,cusineType,description,this.getEmail(),price,isActive);
        //ADD TO THE DATABASE
        databaseReference.push().setValue(meal);
    }

    //equals method
    public boolean equalsTo(Cook cook){
        return this.getAddress() == cook.getAddress() && this.getFirstName() == cook.getFirstName() && this.getLastName() == cook.getLastName()
                && this.getEmail() == cook.getEmail() && this.getPassword() == cook.getPassword() && this.getDescription() == cook.getDescription()
                && this.getSuspension().equalsTo(cook.getSuspension());
    }
}
