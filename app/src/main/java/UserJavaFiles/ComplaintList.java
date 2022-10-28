package UserJavaFiles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.group7mealerapp.R;

import java.util.List;


public class ComplaintList extends ArrayAdapter<Complaint> {
    private Activity context;
    List<Complaint> complaints;

    public ComplaintList(Activity context, List<Complaint> complaints) {
        super(context, R.layout.layout_complaint_list, complaints);
        this.context = context;
        this.complaints = complaints;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_complaint_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewComplaint);

        Complaint complaint = complaints.get(position);
        textViewName.setText(complaint.getEmail());
        textViewPrice.setText(String.valueOf(complaint.getComplaint()));
        return listViewItem;
    }
}
