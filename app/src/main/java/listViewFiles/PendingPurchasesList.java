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

public class PendingPurchasesList extends ArrayAdapter<Order>
{
    private Activity context;
    List<Order> orders;

    public PendingPurchasesList(Activity context, List<Order> orders)
    {
        super(context, R.layout.layout_pending_orders, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_pending_orders, null, true);

        TextView textViewMeal = (TextView) listViewItem.findViewById(R.id.textViewMeal);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewMealPrice);
        TextView textViewClient = (TextView) listViewItem.findViewById(R.id.textViewMealClient);
        Order order = orders.get(position);
        textViewMeal.setText(order.getMeal().getName());
        textViewPrice.setText("$" + order.getMeal().getPrice());
        textViewClient.setText(order.getUserEmail());
        return listViewItem;
    }
}
