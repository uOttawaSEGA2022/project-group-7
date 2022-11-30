package listViewFiles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.group7mealerapp.R;

import java.util.List;

import UserJavaFiles.Cook;
import UserJavaFiles.Meal;
import UserJavaFiles.User;

//class that sets up the meal list
public class MealList extends ArrayAdapter<Meal> {
    private Activity context;
    List<Meal> meals;
    User user;
    public MealList(Activity context, List<Meal> meals, User user) {
        super(context, R.layout.layout_complaint_list, meals);
        this.context = context;
        this.meals = meals;
        this.user = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //grabbing the xml file info
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list, null, true);
        //get the text views of the xml file
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewMealType = (TextView) listViewItem.findViewById(R.id.textViewComplaint);
        TextView textViewCusineType = (TextView) listViewItem.findViewById(R.id.textViewCusineType);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewActive = (TextView) listViewItem.findViewById(R.id.textViewActive);
        TextView textViewIngredients = (TextView) listViewItem.findViewById(R.id.textViewIngredients);
        TextView textViewAllergens = (TextView) listViewItem.findViewById(R.id.textViewAllergens);

        //add elements dependent on array as per the adapter, get the element by position and add on
        Meal meal = meals.get(position);
        //set the name based on the meals name
        textViewName.setText(meal.getName());
        //set the meal type
        textViewMealType.setText("meal type: " +meal.getMealType());
        //set the cusine type
        textViewCusineType.setText("cusine type: " +meal.getCusineType());
        //set the price by converting the price to a string
        textViewPrice.setText("price: $" +String.valueOf(meal.getPrice()));
        //set the ingredients text
        textViewIngredients.setText("ingredients: " + meal.getIngredients());
        //set the allergens text
        textViewAllergens.setText("allergens: " +meal.getAllergens());
        //IF THE USER IS A COOK THEN YOU CAN SHOW THIS IF NOT DO NOT
        if (meal.isOffered())
            textViewActive.setText("OFFERED");
        else
            textViewActive.setText("INACTIVE");
        if(user.getClass() != Cook.class)
            textViewActive.setVisibility(View.GONE);
        //finalize
        return listViewItem;
    }
}
