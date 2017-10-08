package com.example.vitaliy.comandroidchinema.Data.Helpers.Components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.vitaliy.comandroidchinema.R;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class CustomDialog {
    public static void CreateDialog (final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Важное сообщение!")
                .setMessage("Fatal Error")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
