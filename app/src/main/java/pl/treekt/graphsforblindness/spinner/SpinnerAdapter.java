package pl.treekt.graphsforblindness.spinner;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.treekt.graphsforblindness.database.entity.DataSet;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, ArrayList<String> list) {
        super(context, android.R.layout.simple_list_item_1, list);
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView label = (TextView) view;
        if (position == 0) {
            // Set the hint text color gray
            label.setTextColor(Color.GRAY);
        } else {
            label.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item from Spinner
        // First item will be use for hint
        return position != 0;
    }


}
