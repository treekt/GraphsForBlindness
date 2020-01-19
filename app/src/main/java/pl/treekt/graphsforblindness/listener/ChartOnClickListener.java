package pl.treekt.graphsforblindness.listener;

import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.types.DataType;
import pl.treekt.graphsforblindness.utils.DataSetUtil;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

import java.util.Map;

public class ChartOnClickListener extends ListenersInterface.OnClickListener {

    private TextToSpeechManager textToSpeechManager;
    private DataSet dataSet;

    public ChartOnClickListener(DataSet dataSet) {
        super(new String[]{"x", "value"});
        this.dataSet = dataSet;
        this.textToSpeechManager = new TextToSpeechManager();
    }

    @Override
    public void onClick(Event event) {
        Map<String, String> data = event.getData();
        String label = data.get("x");
        Integer value = Integer.valueOf(data.get("value"));

        textToSpeechManager.speak(DataSetUtil.prepareStatement(label, value, dataSet.getPrefix()));
    }
}
