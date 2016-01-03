package com.newbilius.voicerecognition;

public interface ISpeechRecognizer {
    String Title();
    void Recognize(ISpeechRecognizerResultCallback SpeechRecognizerResultCallback);
}
