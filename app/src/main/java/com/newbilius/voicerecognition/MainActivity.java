package com.newbilius.voicerecognition;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.*;
import com.newbilius.voicerecognition.Recognizers.GoogleSpeechRecognizer;
import com.newbilius.voicerecognition.Recognizers.YandexSpeechRecognizer;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends FragmentActivity {

    private ArrayAdapter<String> listAdapter;
    private SimpleAlertDialog simpleAlertDialog;

    private TextView recognitionStatusLabel;
    private TextView startRecognitionButton;
    private ListView listView;
    private RadioGroup recognitionModeSelectorRadioGroup;
    private ISpeechRecognizer selectedRecognizer;

    private ArrayList<ISpeechRecognizer> speachRecognizers = new ArrayList<>();
    private ArrayList<String> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recognitionStatusLabel = (TextView) findViewById(R.id.recognitionStatusLabel);
        startRecognitionButton = (TextView) findViewById(R.id.startRecognitionButton);
        listView = (ListView) findViewById(R.id.listView);
        recognitionModeSelectorRadioGroup = (RadioGroup) findViewById(R.id.recognitionModeSelectorRadioGroup);
        simpleAlertDialog = new SimpleAlertDialog();

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, results);
        listView.setAdapter(listAdapter);

        speachRecognizers.add(new GoogleSpeechRecognizer(this.getApplicationContext()));
        speachRecognizers.add(new YandexSpeechRecognizer(this.getApplicationContext()));
        selectedRecognizer = speachRecognizers.get(0);

        for (ISpeechRecognizer recognizer : speachRecognizers) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(recognizer.Title());
            int index = speachRecognizers.indexOf(recognizer);
            radioButton.setChecked(index == 0);
            radioButton.setTag(index);
            radioButton.setId(index);
            recognitionModeSelectorRadioGroup.addView(radioButton);
            radioButton.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        selectedRecognizer = speachRecognizers.get((int) buttonView.getTag());
                }
            });
        }

        startRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results.clear();
                listAdapter.notifyDataSetChanged();

                selectedRecognizer.Recognize(new ISpeechRecognizerResultCallback() {
                    @Override
                    public void OnError(String errorText) {
                        recognitionStatusLabel.setText("Error :-(");
                        ShowMessage(errorText);
                    }

                    @Override
                    public void OnStatusChanged(String status) {
                        recognitionStatusLabel.setText(status);
                    }

                    @Override
                    public void OnSuccess(String[] texts) {
                        recognitionStatusLabel.setText("Success! :-)");
                        Collections.addAll(results, texts);
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void ShowMessage(String message) {
        simpleAlertDialog.Show(this, message);
    }
}