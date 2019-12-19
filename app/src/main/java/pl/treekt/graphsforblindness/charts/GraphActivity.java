package pl.treekt.graphsforblindness.charts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.anychart.AnyChartView;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.types.DataType;
import pl.treekt.graphsforblindness.types.GraphType;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

public abstract class GraphActivity extends AppCompatActivity {

    private TextToSpeechManager textToSpeechManager;

    @BindView(R.id.chart_view)
    AnyChartView chartView;

    private GraphType graphType;
    private DataType dataType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        textToSpeechManager = new TextToSpeechManager();

        receiveExtras();
        prepareChart();
    }

    public abstract void prepareChart();

    private void receiveExtras() {
        Intent intent = getIntent();
        graphType = (GraphType) intent.getSerializableExtra("graphType");
        dataType = (DataType) intent.getSerializableExtra("dataType");

        String message = "Uruchomiono " + graphType.getTitle().toLowerCase() + " z zestawem danych " + dataType.getTitle().toLowerCase();
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

    public DataType getDataType() {
        return dataType;
    }
}
