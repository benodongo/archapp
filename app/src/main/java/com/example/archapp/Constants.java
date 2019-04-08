package com.example.archapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class Constants {
    //SQLITE DB CONSTANTS
    //DATABASE NAME
    public static final String DATABASE_NAME = "mbima";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";
    //COLUMN phone number
    public static final String KEY_USER_PHONE = "phone";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_USER_PHONE + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";
    //new client
    public static final   String FNAME = "firstname";
    public static final String LNAME = "lastname";
    public static final String INSURANCE_COMPANY = "company";
    public static final String POLICY = "policy";
    public static final String POLICY_DATE = "policy_date";
    public static  final String POLICY_DURATION = "duration";
    public static final String POLICY_NO = "policy_no";
    public static final String PREMIUM = "premium";
    //create client table
    //TABLE NAME
    public static final String TABLE_CLIENTS = "clients";

    public static final String SQL_TABLE_CLIENTS = " CREATE TABLE " + TABLE_CLIENTS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + FNAME + " TEXT, "
            + LNAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_USER_PHONE + " TEXT, "
            + INSURANCE_COMPANY + " TEXT, "
            + POLICY + " TEXT, "
            + POLICY_DATE + " TEXT, "
            + POLICY_DURATION + " TEXT, "
            + POLICY_NO + " TEXT, "
            + PREMIUM + " TEXT"
            + " ) ";
    //premiums table
    //TABLE NAME
    public static final String CLIENT = "client_name";
    public static final String TABLE_PREMIUMS = "premiums";
    public static final String SQL_TABLE_PREMIUMS = " CREATE TABLE " + TABLE_PREMIUMS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + INSURANCE_COMPANY + " TEXT, "
            + POLICY + " TEXT, "
            + POLICY_DATE + " TEXT, "
            + POLICY_DURATION + " TEXT, "
            + POLICY_NO + " TEXT, "
            + PREMIUM + " TEXT , "
            + CLIENT + " TEXT "
            + " ) ";
    //NETWORK
    public static final String base_url="http://192.168.43.82:8000/"; //Localhost
    public  static void  showDialog(Context context, String title, String content, String positive, MaterialDialog.SingleButtonCallback callback){
        new MaterialDialog.Builder(context)
                .title(title)
                .widgetColorRes(R.color.colorPrimary)
                .content(content)
                .positiveText(positive)
                .negativeText("CANCEL")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .autoDismiss(true)
                .onPositive(callback)
                .show();
    }
}
