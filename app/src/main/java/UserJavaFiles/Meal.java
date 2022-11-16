package UserJavaFiles;

import androidx.annotation.NonNull;

import com.example.group7mealerapp.Search;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import listViewFiles.MealList;

public class Meal implements Serializable {
    //Instance variables
    private String name, mealType, cusineType,description,email;

    private double price;

    private boolean offered;

    public Meal(String name, String mealType,String cusineType, String description,String email, double price,Boolean offered)
    {
        this.name = name;
        this.price = price;
        this.mealType = mealType;
        this.cusineType = cusineType;
        this.description = description;
        this.email = email;
        //auto set meals to not offered and then when they wish to be offered make the on click call
        // method setOffered
        offered = false;
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

    // if cook wishes to offer the meal pass true, if the cook no longer wishes to offer the meal pass false
    public void setOffered(boolean choice, Meal meal) {
    }


    //check if meal type, cusine type name and price match
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
        return name + " " + mealType + " " + cusineType + " " + price;
    }

}


