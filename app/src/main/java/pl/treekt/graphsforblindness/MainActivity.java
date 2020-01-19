package pl.treekt.graphsforblindness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.stetho.Stetho;
import pl.treekt.graphsforblindness.database.DatabaseAdapter;
import pl.treekt.graphsforblindness.database.dao.DataSetDao;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.dataset.DatasetActivity;
import pl.treekt.graphsforblindness.types.GraphType;
import pl.treekt.graphsforblindness.spinner.SpinnerAdapter;
import pl.treekt.graphsforblindness.spinner.SpinnerElementSelectedListener;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_DATA_SET_CODE = 55;

    @BindView(R.id.graph_type_spinner)
    Spinner graphTypesSpinner;

    @BindView(R.id.data_type_spinner)
    Spinner dataSetsSpinner;

    @BindView(R.id.show_graph_button)
    Button showGraphButton;

    @BindView(R.id.open_dataset_form_button)
    Button openDatasetFormButton;

    private DataSetDao dataSetDao;

    private ArrayList<DataSet> dataSets;

    private SpinnerAdapter dataSetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TextToSpeechManager.initializeInstance(getApplicationContext());
        DatabaseAdapter.initializeInstance(getApplicationContext());

        dataSetDao = new DataSetDao();
        prepareSpinners();

        showGraphButton.setOnClickListener(v -> openGraphActivity());
        openDatasetFormButton.setOnClickListener(v -> openDataSetActivity());
    }

    private void prepareSpinners() {
        SpinnerAdapter graphTypesAdapter = new SpinnerAdapter(MainActivity.this, GraphType.getGraphTypesWithHint());
        graphTypesSpinner.setAdapter(graphTypesAdapter);
        graphTypesSpinner.setOnItemSelectedListener(new SpinnerElementSelectedListener(getApplicationContext(), ""));

        dataSets = dataSetDao.readAll();

        dataSetsAdapter = new SpinnerAdapter(MainActivity.this, getTitlesFromDataSetsWithHint());
        dataSetsSpinner.setAdapter(dataSetsAdapter);
        dataSetsSpinner.setOnItemSelectedListener(new SpinnerElementSelectedListener(getApplicationContext(), "zestaw"));
    }

    private void openGraphActivity() {
        GraphType graphType = GraphType.getByTitle(graphTypesSpinner.getSelectedItem().toString());
        DataSet dataSet = getDataSetFromTitle(dataSetsSpinner.getSelectedItem().toString());

        if(graphType == null || dataSet == null) {
            Toast.makeText(this, "You didnt choose graph type or data set!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent graphActivity = new Intent(this, Objects.requireNonNull(graphType).getActivityClass());
        graphActivity.putExtra("graphType", graphType);
        graphActivity.putExtra("dataSet", dataSet);
        startActivity(graphActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_DATA_SET_CODE) {
                dataSets = dataSetDao.readAll();
                dataSetsAdapter.clear();
                dataSetsAdapter.addAll(getTitlesFromDataSetsWithHint());
                dataSetsAdapter.notifyDataSetChanged();
        }
    }

    private void openDataSetActivity() {
        Intent datasetFormActivity = new Intent(this, DatasetActivity.class);
        startActivityForResult(datasetFormActivity, REQUEST_DATA_SET_CODE);
    }

    private ArrayList<String> getTitlesFromDataSetsWithHint() {
        ArrayList<String> list =  dataSets.stream()
                .map(DataSet::getTitle)
                .collect(Collectors.toCollection(ArrayList::new));
        list.add(0, "Wybierz nowy zestaw");

        return list;
    }

    private DataSet getDataSetFromTitle(String title) {
        return dataSets.stream()
                .filter(ds -> ds.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
}
