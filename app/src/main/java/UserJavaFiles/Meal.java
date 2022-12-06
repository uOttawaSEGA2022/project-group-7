package UserJavaFiles;

import androidx.annotation.NonNull;

import com.example.group7mealerapp.Search;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

import listViewFiles.MealList;

public class Meal implements Serializable {
    //Instance variables
    private String name,mealType,cusineType,description,email,ingredients,allergens;

    private double price;

    private boolean offered;


    public Meal(String name, String mealType,String cusineType, String description,String email, double price,boolean offered,String ingredients, String allergens)
    {
        this.name = name;
        this.price = price;
        this.mealType = mealType;
        this.cusineType = cusineType;
        this.description = description;
        this.email = email;
        this.offered = offered;
        this.ingredients = ingredients;
        this.allergens = allergens;
    }
    //for firebase
    public Meal(){}

    //getters
    public String getName()
    {
        return name;
    }

    public String getMealType() {return mealType;}

    public String getCusineType() {return cusineType;}

    public boolean isOffered() {return offered;}

    public double getPrice()
    {
        return price;
    }

    public String getDescription() {return description;}

    public String getEmail() {return email;}

    public void setName(String name) {
        this.name = name;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setCusineType(String cusineType) {
        this.cusineType = cusineType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {this.email = email;}

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOffered(boolean offered) {
        this.offered = offered;
    }

    public String getIngredients() {return ingredients;}

    public void setIngredients(String ingredients) {this.ingredients = ingredients;}

    public String getAllergens() {return allergens;}

    public void setAllergens(String allergens) {this.allergens = allergens;}

    public void setOfferedDB(Meal meal){
        HashMap<String,Object> map = new HashMap<String,Object>();

        if(meal.offered == true){
            this.offered = false;
            map.put("offered",false);
        }
        else {
            this.offered = true;
            map.put("offered", true);
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Meals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Meal temp = new Meal();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){

                    //no logic just stores the value onto user
                    temp = child.getValue(Meal.class);

                    //comparing the email and password from the database with the inputted text fields
                    if (temp.equals(meal)){

                        String id = child.getKey();
                        System.out.println("we in here");
                        firebaseDatabase.getReference("Meals").child(id).updateChildren(map);

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
    public void mealDeleteDB(Meal meal){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Meals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                Meal temp = new Meal();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){

                    //no logic just stores the value onto user
                    temp = child.getValue(Meal.class);

                    //comparing the email and password from the database with the inputted text fields
                    if (temp.equals(meal)){

                        String id = child.getKey();

                        firebaseDatabase.getReference("Meals").child(id).removeValue();
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

    //check if meal type, cuisine type name and price match
    public Boolean equals(Meal mealtoCompare){
        Boolean flag = true;
        if(!mealType.equals(mealtoCompare.mealType))
            flag = false;
        else if(!cusineType.equals(mealtoCompare.cusineType))
            flag = false;
        else if(!name.equals(mealtoCompare.name))
            flag = false;
        else if(!(price == mealtoCompare.price))
            flag = false;
        return flag;
    }
    public String toString(){
        return name + " " + mealType + " " + cusineType + " " + price + " " + ingredients + " " + allergens + " " + offered + " " + email + " " + description;
    }

}


