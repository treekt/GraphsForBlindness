package pl.treekt.graphsforblindness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.treekt.graphsforblindness.types.DataType;
import pl.treekt.graphsforblindness.types.GraphType;
import pl.treekt.graphsforblindness.spinner.SpinnerAdapter;
import pl.treekt.graphsforblindness.spinner.SpinnerElementSelectedListener;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.graph_type_spinner)
    Spinner graphTypesSpinner;

    @BindView(R.id.data_type_spinner)
    Spinner dataTypesSpinner;

    @BindView(R.id.show_graph_button)
    Button showGraphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TextToSpeechManager.initializeInstance(getApplicationContext());

        prepareSpinners();

        showGraphButton.setOnClickListener(v -> openGraphActivity());
    }

    private void prepareSpinners() {
        SpinnerAdapter graphTypesAdapter = new SpinnerAdapter(MainActivity.this, GraphType.getGraphTypesWithHint());
        graphTypesSpinner.setAdapter(graphTypesAdapter);
        graphTypesSpinner.setOnItemSelectedListener(new SpinnerElementSelectedListener(getApplicationContext(), ""));

        SpinnerAdapter dataTypesAdapter = new SpinnerAdapter(MainActivity.this, DataType.getDataTypesWithHint());
        dataTypesSpinner.setAdapter(dataTypesAdapter);
        dataTypesSpinner.setOnItemSelectedListener(new SpinnerElementSelectedListener(getApplicationContext(), "zestaw"));


    }

    private void openGraphActivity() {
        GraphType graphType = GraphType.getByTitle(graphTypesSpinner.getSelectedItem().toString());
        DataType dataType = DataType.getByTitle(dataTypesSpinner.getSelectedItem().toString());

        Intent graphActivity = new Intent(this, Objects.requireNonNull(graphType).getActivityClass());
        graphActivity.putExtra("graphType", graphType);
        graphActivity.putExtra("dataType", dataType);
        startActivity(graphActivity);
    }
}
