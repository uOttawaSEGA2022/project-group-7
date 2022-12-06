package listViewFiles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.group7mealerapp.R;

import java.util.List;

import UserJavaFiles.Order;

//class that sets up the complaint list
public class OrderList extends ArrayAdapter<Order> {
    private Activity context;
    List<Order> orders;

    public OrderList(Activity context, List<Order> orders) {
        super(context, R.layout.layout_order_list, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_order_list, null, true);

        TextView textViewMeal = (TextView) listViewItem.findViewById(R.id.textViewMeal);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);

        Order order = orders.get(position);
        textViewMeal.setText(String.valueOf(order.getMeal()));
        textViewEmail.setText(order.getCookEmail());
        textViewStatus.setText(order.getState());

        return listViewItem;
    }
}

