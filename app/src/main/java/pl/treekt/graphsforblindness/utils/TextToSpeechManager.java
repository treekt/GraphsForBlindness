package pl.treekt.graphsforblindness.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextToSpeechManager {

    private static TextToSpeech textToSpeech;

    public static void initializeInstance(Context context) {
        if (textToSpeech == null) {
            Locale plLocale = new Locale("pl", "PL");
            textToSpeech = new TextToSpeech(context, status -> {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(plLocale);
                }
            });
        }
    }

    public void speak(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
