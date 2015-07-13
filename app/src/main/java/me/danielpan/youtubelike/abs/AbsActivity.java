package me.danielpan.youtubelike.abs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by it-od-m-2572 on 15/6/6.
 */
public abstract class AbsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateContentView();
        findViewsById();
        initWidgets();
        loadData();
    }

    protected abstract void inflateContentView();

    protected abstract void findViewsById();

    protected abstract void initWidgets();

    protected abstract void loadData();

    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    protected void showCenterToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showMaterialDialog(String message) {
        showMaterialDialog(null, message);
    }

    protected void showMaterialDialog(String title, String message) {
        showMaterialDialog(title, message, null, null);
    }

    protected void showMaterialDialog(String title, String message, String pstvText, DialogInterface.OnClickListener pstvListener) {
        showMaterialDialog(title, message, pstvText, pstvListener, null, null);
    }

    protected void showMaterialDialog(String title, String message, String pstvText, DialogInterface.OnClickListener pstvListener, String ngtvText, DialogInterface.OnClickListener ngtvListener) {
        showMaterialDialog(title, message, pstvText, pstvListener, ngtvText, ngtvListener, null, null);
    }

    protected void showMaterialDialog(String title, String message, String pstvText, DialogInterface.OnClickListener pstvListener, String ngtvText, DialogInterface.OnClickListener ngtvListener, String ntrlText,DialogInterface.OnClickListener ntrlListener) {
        showMaterialDialog(title, message, pstvText, pstvListener, ngtvText, ngtvListener, ntrlText, ntrlListener, null);
    }

    protected void showMaterialDialog(String title, String message, String pstvText, DialogInterface.OnClickListener pstvListener, String ngtvText, DialogInterface.OnClickListener ngtvListener, String ntrlText, DialogInterface.OnClickListener ntrlListener, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this/*, R.style.AppTheme*/);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }else {

        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(pstvText) && pstvListener != null) {
            builder.setPositiveButton(pstvText,pstvListener);
        }
        if (!TextUtils.isEmpty(ngtvText) && ngtvListener != null) {
            builder.setNegativeButton(ngtvText, ngtvListener);
        }
        if (!TextUtils.isEmpty(ntrlText) && ntrlListener != null) {
            builder.setNeutralButton(ntrlText, ntrlListener);
        }
        if (dismissListener != null) {
            builder.setOnDismissListener(dismissListener);
        }
        builder.create().show();
    }

    protected void showSimplestSnackbar(String text) {
        showSimplestSnackbar(text, null);
    }

    protected void showSimplestSnackbar(String text, String action) {
        showSimplestSnackbar(text, action, null);
    }

    protected void showSimplestSnackbar(String text, String action, View.OnClickListener actionListener) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).setAction(action, actionListener).show();
    }

    protected void showColorfulSnackbar(String text, String action, View.OnClickListener actionListener, int bgColor, int textColor) {
        showColorfulSnackbar(text, action, actionListener, bgColor, textColor, getResources().getColor(android.support.design.R.color.material_blue_grey_900));
    }

    protected void showColorfulSnackbar(String text, String action, View.OnClickListener actionListener, int bgColor, int textColor, int actionTextColor) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).setAction(action, actionListener);
        snackbar.setActionTextColor(actionTextColor);
        View bgView = snackbar.getView();
        bgView.setBackgroundColor(bgColor);
        TextView textView = (TextView) bgView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(textColor);
        snackbar.show();
    }

    protected void startActivity(Class<?> clzz) {
        startActivity(new Intent(this, clzz));
    }

    protected SharedPreferences getPref() {
        return getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor getEditor() {
        return getPref().edit();
    }
}
