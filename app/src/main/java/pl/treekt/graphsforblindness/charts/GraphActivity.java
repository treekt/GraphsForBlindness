package pl.treekt.graphsforblindness.charts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.database.dao.DataSetElementDao;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.database.entity.DataSetElement;
import pl.treekt.graphsforblindness.types.DataType;
import pl.treekt.graphsforblindness.types.GraphType;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GraphActivity extends AppCompatActivity {

    private TextToSpeechManager textToSpeechManager;

    @BindView(R.id.chart_view)
    AnyChartView chartView;

    private GraphType graphType;
    private DataSet dataSet;

    private DataSetElementDao dataSetElementDao;

    private ArrayList<DataSetElement> dataSetElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        textToSpeechManager = new TextToSpeechManager();
        dataSetElementDao = new DataSetElementDao();

        receiveExtras();

        dataSetElements = dataSetElementDao.readAllByDataSetId(dataSet.getId());

        prepareChart();
    }

    public abstract void prepareChart();

    private void receiveExtras() {
        Intent intent = getIntent();
        graphType = (GraphType) intent.getSerializableExtra("graphType");
        dataSet = (DataSet) intent.getSerializableExtra("dataSet");

        String message = "Uruchomiono " + graphType.getTitle().toLowerCase() + " z zestawem danych " + dataSet.getTitle().toLowerCase();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        textToSpeechManager.speak(message);
    }

    public TextToSpeechManager getTextToSpeechManager() {
        return textToSpeechManager;
    }

    public AnyChartView getChartView() {
        return chartView;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public ArrayList<DataSetElement> getDataSetElements() {
        return dataSetElements;
    }

    public ArrayList<DataEntry> getDataEntries(){
        return dataSetElements.stream()
                .map(dataSetElement -> new ValueDataEntry(dataSetElement.getTitle(), dataSetElement.getValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
