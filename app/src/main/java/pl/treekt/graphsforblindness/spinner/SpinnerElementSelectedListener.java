package pl.treekt.graphsforblindness.spinner;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import pl.treekt.graphsforblindness.utils.TextToSpeechManager;

public class SpinnerElementSelectedListener implements AdapterView.OnItemSelectedListener {

    private TextToSpeechManager textToSpeechManager;

    private Context context;
    private String subText;

    public SpinnerElementSelectedListener(Context context, String subText) {
        this.context = context;
        this.subText = subText.length() > 0 ? subText + " " : "";
        this.textToSpeechManager = new TextToSpeechManager();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItemText = (String) parent.getItemAtPosition(position);
        if (position > 0) {
            String message = "Wybrano" + " " + subText + selectedItemText.toLowerCase();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            textToSpeechManager.speak(message);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
