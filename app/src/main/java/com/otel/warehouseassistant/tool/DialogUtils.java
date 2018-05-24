package com.otel.warehouseassistant.tool;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.otel.warehouseassistant.R;

public class DialogUtils {
    public interface OnClickListener {
        void onClick(DialogInterface dialog, int which, String output);
    }

    public static void ShowMessageDialog(final AppCompatActivity a, String title, String message) {
        ShowMessageDialog(a, title, message, 0);
    }

    public static void ShowMessageDialog(final AppCompatActivity a, String title, String message, int iconId) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(a);
        if (iconId > 0) dlg.setIcon(iconId);
        dlg.setTitle(title);

        dlg.setMessage(message);

        dlg.setCancelable(false)
                .setPositiveButton(a.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = dlg.create();

        alert.show();
    }

    public static void ShowErrorDialog(final AppCompatActivity a, String title, String message, final Boolean exitActivity) {

        Context c = a.getBaseContext();

        AlertDialog.Builder b = new AlertDialog.Builder(a);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                c.getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (exitActivity)
                            a.finish();
                    }
                });

        AlertDialog ad = b.create();
        ad.show();
    }

    public static void ShowYesDialog(final AppCompatActivity a, String title, String message, DialogInterface.OnClickListener OKClickListener) {
        Context c = a.getBaseContext();

        AlertDialog.Builder b = new AlertDialog.Builder(a);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(c.getString(R.string.confirm), OKClickListener);

        AlertDialog ad = b.create();
        ad.show();
    }

    public static void ShowYesDialog(final Context c, String title, String message, DialogInterface.OnClickListener OKClickListener) {
        AlertDialog.Builder b = new AlertDialog.Builder(c);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(c.getString(R.string.confirm), OKClickListener);

        AlertDialog ad = b.create();
        ad.show();
    }

    public static void ShowYesNoDialog(final AppCompatActivity a, String title, String message, DialogInterface.OnClickListener OKClickListener, DialogInterface.OnClickListener CancelClickListener) {
        Context c = a.getBaseContext();

        AlertDialog.Builder b = new AlertDialog.Builder(a);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(c.getString(R.string.confirm), OKClickListener);
        b.setNegativeButton(c.getString(R.string.cancel), CancelClickListener);

        AlertDialog ad = b.create();
        ad.show();
    }

    public static void ShowInputDialog(final AppCompatActivity a, String title, String textViewHint, OnClickListener OKClickListener, DialogInterface.OnClickListener CancelClickListener) {

        final DialogUtils.OnClickListener mOnClickListener = OKClickListener;

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(a);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(a);
        alertDialogBuilderUserInput.setView(mView);

        TextView t = (TextView) mView.findViewById(R.id.dialogTitle);
        t.setText(title);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        if (textViewHint != null)
            userInputDialogEditText.setHint(textViewHint);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(a.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnClickListener.onClick(dialog, which, userInputDialogEditText.getText().toString());
                    }
                })
                .setNegativeButton(a.getString(R.string.cancel), CancelClickListener);

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }
}
