package com.newbilius.voicerecognition;

public interface ISpeechRecognizerResultCallback{
    void OnError(String errorText);
    void OnStatusChanged(String status);
    void OnSuccess(String[] texts);
}