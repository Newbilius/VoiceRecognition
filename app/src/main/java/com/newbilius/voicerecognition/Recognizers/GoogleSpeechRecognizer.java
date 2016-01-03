package com.newbilius.voicerecognition.Recognizers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import com.newbilius.voicerecognition.ISpeechRecognizer;
import com.newbilius.voicerecognition.ISpeechRecognizerResultCallback;

public class GoogleSpeechRecognizer implements ISpeechRecognizer {
    private final Context context;

    public GoogleSpeechRecognizer(Context context){
        this.context = context;
    }
    @Override
    public String Title() {
        return "Google";
    }

    @Override
    public void Recognize(ISpeechRecognizerResultCallback speechRecognizerResultCallback) {
        SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(new GoogleRecognitionListener(speechRecognizerResultCallback));

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"ru");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 999);
        speechRecognizer.startListening(recognizerIntent);
    }

    private class GoogleRecognitionListener implements RecognitionListener {
        private final ISpeechRecognizerResultCallback speechRecognizerResultCallback;

        public GoogleRecognitionListener(ISpeechRecognizerResultCallback speechRecognizerResultCallback){
            this.speechRecognizerResultCallback = speechRecognizerResultCallback;
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            speechRecognizerResultCallback.OnStatusChanged("onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            speechRecognizerResultCallback.OnStatusChanged("onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
            speechRecognizerResultCallback.OnStatusChanged("onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            if (error==SpeechRecognizer.ERROR_AUDIO)
                speechRecognizerResultCallback.OnError("ERROR_AUDIO");
            if (error==SpeechRecognizer.ERROR_CLIENT)
                speechRecognizerResultCallback.OnError("ERROR_CLIENT");
            if (error==SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS)
                speechRecognizerResultCallback.OnError("ERROR_INSUFFICIENT_PERMISSIONS");
            if (error==SpeechRecognizer.ERROR_NETWORK)
                speechRecognizerResultCallback.OnError("ERROR_NETWORK");
            if (error==SpeechRecognizer.ERROR_NETWORK_TIMEOUT)
                speechRecognizerResultCallback.OnError("ERROR_NETWORK_TIMEOUT");
            if (error==SpeechRecognizer.ERROR_NO_MATCH)
                speechRecognizerResultCallback.OnError("ERROR_NO_MATCH");
            if (error==SpeechRecognizer.ERROR_RECOGNIZER_BUSY)
                speechRecognizerResultCallback.OnError("ERROR_RECOGNIZER_BUSY");
            if (error==SpeechRecognizer.ERROR_SPEECH_TIMEOUT)
                speechRecognizerResultCallback.OnError("ERROR_SPEECH_TIMEOUT");
        }

        @Override
        public void onResults(Bundle results) {
            speechRecognizerResultCallback.OnSuccess(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toArray(new String[results.size()]));
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            speechRecognizerResultCallback.OnSuccess(partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).toArray(new String[partialResults.size()]));
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }
}
