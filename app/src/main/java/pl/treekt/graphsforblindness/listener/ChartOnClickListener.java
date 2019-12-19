package pl.treekt.graphsforblindness.listener;

import android.widget.Toast;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import pl.treekt.graphsforblindness.types.DataType;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

import java.util.Map;

public class ChartOnClickListener extends ListenersInterface.OnClickListener {

    private DataType dataType;
    private TextToSpeechManager textToSpeechManager;

    public ChartOnClickListener(DataType dataType) {
        super(new String[]{"x", "value"});
        this.dataType = dataType;
        this.textToSpeechManager = new TextToSpeechManager();
    }

    @Override
    public void onClick(Event event) {
        Map<String, String> data = event.getData();
        String label = data.get("x");
        Integer value = Integer.valueOf(data.get("value"));

        textToSpeechManager.speak(dataType.getStatement(label, value));
    }
}
