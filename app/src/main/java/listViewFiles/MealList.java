package listViewFiles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.group7mealerapp.R;

import java.util.List;

import UserJavaFiles.Meal;

//class that sets up the meal list
public class MealList extends ArrayAdapter<Meal> {
    private Activity context;
    List<Meal> meals;

    public MealList(Activity context, List<Meal> meals) {
        super(context, R.layout.layout_complaint_list, meals);
        this.context = context;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //grabbing the xml file info
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list, null, true);
        //get the text views of the xml file
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewMealType = (TextView) listViewItem.findViewById(R.id.textViewMealType);
        TextView textViewCusineType = (TextView) listViewItem.findViewById(R.id.textViewCusineType);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        //add elements dependent on array as per the adapter, get the element by position and add on
        Meal meal = meals.get(position);
        //set the name based on the meals name
        textViewName.setText(meal.getName());
        //set the meal type
        textViewMealType.setText(meal.getMealType());
        //set the cusine type
        textViewCusineType.setText(meal.getCusineType());
        //set the price by converting the price to a string
        textViewPrice.setText(String.valueOf(meal.getPrice()));
        //finalize
        return listViewItem;
    }
}
