package pl.treekt.graphsforblindness.dataset;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.dataset.adapter.DatasetAdapter;
import pl.treekt.graphsforblindness.dataset.adapter.DatasetElementAdapter;

public class DatasetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_dataset);

        ListView dataSetElementsListView = findViewById(R.id.dataset_elements_list);
        DatasetElementAdapter datasetElementAdapter = new DatasetElementAdapter(getLayoutInflater());
        dataSetElementsListView.setAdapter(datasetElementAdapter);

        ListView dataSetListView = findViewById(R.id.dataset_list);
        DatasetAdapter datasetAdapter = new DatasetAdapter(getLayoutInflater());
        dataSetListView.setAdapter(datasetAdapter);
        datasetAdapter.attach(datasetElementAdapter);


        dataSetListView.setOnItemClickListener((parent, view, position, id) -> {
            datasetAdapter.setSelectedDataSetAtPosition(position);
        });

    }
}
