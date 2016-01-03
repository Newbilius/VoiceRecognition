package com.newbilius.voicerecognition.Recognizers;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import com.newbilius.voicerecognition.ISpeechRecognizer;
import com.newbilius.voicerecognition.ISpeechRecognizerResultCallback;
import ru.yandex.speechkit.*;
import ru.yandex.speechkit.gui.RecognizerActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class YandexSpeechRecognizer implements ISpeechRecognizer {

    private final Context context;

    public YandexSpeechRecognizer(Context context){
        this.context = context;
        SpeechKit.getInstance().configure(context,KEY);//you can get key there: https://developer.tech.yandex.ru/keys/
    }

    @Override
    public String Title() {
        return "Yandex";
    }

    @Override
    public void Recognize(ISpeechRecognizerResultCallback SpeechRecognizerResultCallback) {
        Recognizer.create("ru-RU", Recognizer.Model.general, new YandexRecognizerListener(SpeechRecognizerResultCallback)).start();
    }

    private class YandexRecognizerListener implements RecognizerListener{

        private final ISpeechRecognizerResultCallback speechRecognizerResultCallback;

        public YandexRecognizerListener(ISpeechRecognizerResultCallback SpeechRecognizerResultCallback){
            speechRecognizerResultCallback = SpeechRecognizerResultCallback;
        }
        @Override
        public void onRecordingBegin(Recognizer recognizer) {
            speechRecognizerResultCallback.OnStatusChanged("onRecordingBegin");
        }

        @Override
        public void onSpeechDetected(Recognizer recognizer) {
            speechRecognizerResultCallback.OnStatusChanged("onSpeechDetected");
        }

        @Override
        public void onRecordingDone(Recognizer recognizer) {
            speechRecognizerResultCallback.OnStatusChanged("onRecordingDone");
        }

        @Override
        public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {

        }

        @Override
        public void onPowerUpdated(Recognizer recognizer, float v) {
        }

        @Override
        public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
            speechRecognizerResultCallback.OnSuccess(GetResult(recognition));
        }

        @Override
        public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
            speechRecognizerResultCallback.OnSuccess(GetResult(recognition));
        }

        private String[] GetResult(Recognition recognition){
            ArrayList<String> results=new ArrayList<>();
            results.add(recognition.getBestResultText());
            for (RecognitionHypothesis recognitionHypothesis : recognition.getHypotheses()) {
                results.add(recognitionHypothesis.getNormalized());
            }
            return results.toArray(new String[results.size()]);
        }

        @Override
        public void onError(Recognizer recognizer, ru.yandex.speechkit.Error error) {
            speechRecognizerResultCallback.OnError(error.getString());
        }
    }
}