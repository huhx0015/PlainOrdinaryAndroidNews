package com.huhx0015.poa.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.huhx0015.poa.R;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class DialogUtils {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private static final String LOG_TAG = DialogUtils.class.getSimpleName();

    /** DIALOG METHODS _________________________________________________________________________ **/

    public static void displayAlertDialog(String title, String message,
                                          View.OnClickListener listener, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View alertDialogView = inflater.inflate(R.layout.dialog_alert, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertDialogView);

        Button dialogPositiveButton = (Button) alertDialogView.findViewById(R.id.alert_dialog_positive_button);
        TextView dialogTitle = (TextView) alertDialogView.findViewById(R.id.alert_dialog_title);
        TextView dialogMessage = (TextView) alertDialogView.findViewById(R.id.alert_dialog_content);

        dialogPositiveButton.setOnClickListener(listener);
        dialogTitle.setText(title);
        dialogMessage.setText(message);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            Log.e(LOG_TAG, "ERROR: " + e.getLocalizedMessage());
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_progress);
        dialog.show();
        return dialog;
    }
}
