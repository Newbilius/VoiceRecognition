package com.newbilius.voicerecognition;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class SimpleAlertDialog extends DialogFragment {
    private String message;

    public void Show(FragmentActivity activity,String message){
        this.message = message;
        this.show(activity.getSupportFragmentManager(),"42");
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK",null);
        return adb.create();
    }
}
