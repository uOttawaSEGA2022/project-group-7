package UserJavaFiles;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Locale;

public class Cook extends User{
    private Bitmap blankCheque;
    private String description;
    private Suspension status;
    private ArrayList<Meal> menu = new ArrayList<Meal>();
    public Cook(String firstName,String lastName,String email,String password,String address, String description,Suspension status){
        super(firstName,lastName,email,password,address);
        this.description = description;
        this.blankCheque = null;
        this.status = status;
    }

    //getters and setters
    public Bitmap getBlankCheque() {
        return this.blankCheque;
    }
    public String getDescription() {return description;}
    public Suspension getSuspension() {return status;}
    public void setBlankCheque(Bitmap blankCheque) {
        this.blankCheque = blankCheque;
    }
    public void setDescription(String description) {this.description = description;}
    public void setSuspension(Suspension suspend){this.status = suspend;}

    public boolean equalsTo(Cook cook){
        return this.getAddress() == cook.getAddress() && this.getFirstName() == cook.getFirstName() && this.getLastName() == cook.getLastName()
                && this.getEmail() == cook.getEmail() && this.getPassword() == cook.getPassword() && this.getDescription() == cook.getDescription()
                && this.getSuspension().equalsTo(cook.getSuspension());
    }

    //adding a meal to the arraylist called menu
    public void addMeal(String name, String description, double price)
    {
        //calling the exists method to check if the meal is already in the menu if it is
        // then nothing is added to menu
        int flag = exists(name);

        // if flag is not -1 then it does not exist in the menu
        if (flag != -1)
        {

        }
        else {
            menu.add(new Meal(name,description,price));
        }
    }

    public void deleteMeal(String name)
    {
        // if exists returns -1 then the meal the cook is trying to delete was never added to the
        // menu therefore it cannot be deleted
        if(exists(name) != -1)
        {
            //removing the meal specified by getting the index from exists
            menu.remove(exists(name));
        }
    }

    // helper method which checks if a meal is already in the menu and if it is returns it's index
    private int exists(String name)
    {
        int index = -1;
        for(int i = 0; i< menu.size(); i++)
        {
            if (name.toLowerCase().equals(menu.get(i).getName().toLowerCase()))
            {
                //if it finds the meal with the name provided it gets its index
                index = i;
            }
        }

        //returns an index if the name was found in the menu and -1 if it was not
        return index;
    }
}
