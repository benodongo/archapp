package com.example.archapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class RegisterActivity extends AppCompatActivity  {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    //    edit texts
    private EditText et_email;
    private  EditText et_password;
    private  EditText et_name;
    //
    private String email,name;
    private String password;
    MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        et_name = findViewById(R.id.etFullName);
        Button register = findViewById(R.id.btnRegister);
        Button btnlogin = findViewById(R.id.btnRegisterLogin);
       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
               startActivity(i);
           }
       });
        //login when button login is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                email = et_email.getText().toString().toLowerCase().trim();
                name = et_name.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });



    }
    private void login() {
        String params = null;
        try {
            params = "name="+ URLEncoder.encode(name, "UTF-8")+
                    "&email="+ URLEncoder.encode(email, "UTF-8")+
                    "&password="+ URLEncoder.encode(password, "UTF-8")
            ;
            new loginTask().execute("https://majaribio.000webhostapp.com/api/register.php", params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
    private void configureDialog(String title,String message){
        dialog=new MaterialDialog.Builder(this)
                .title(title)
                .cancelable(false)
                .titleGravity(GravityEnum.CENTER)
                .widgetColorRes(R.color.colorPrimary)
                .customView(R.layout.dialog, true)
                .build();
        View view=dialog.getCustomView();
        TextView messageText=view.findViewById(R.id.message);
        messageText.setText(message);
    }
    private class loginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            configureDialog("Login","Please wait as we set up your account.We will automatically redirect you once we finish");
            dialog.show();
        }
        protected String  doInBackground(String... params) {
            return NetworkHandler.post(params[0], params[1]);
        }
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result != null) {
                try {
                    Object json = new JSONTokener(result).nextValue();
                    if (json instanceof JSONObject) {
                        final JSONObject object = new JSONObject(result);
                        String status = object.getString("status");
                        if (status.equals("success") ) {
//                            preferences.setId(object.getString("id"));
//                            preferences.setName(object.getString("name"));
//                            preferences.setEmail(object.getString("email"));
//                            preferences.setPhone(object.getString("phone"));
//                            preferences.setIsLoggedin(true);


                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();



                        } else {
                            MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    login();
                                }
                            };
                            Constants.showDialog(RegisterActivity.this,"Error",object.getString("message"),"RETRY",singleButtonCallback);
                        }

                    } else {
                        MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                login();
                            }
                        };
                        Constants.showDialog(RegisterActivity.this,"Error",getResources().getString(R.string.error),"RETRY",singleButtonCallback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        login();
                    }
                };
                Constants.showDialog(RegisterActivity.this,"Error",getResources().getString(R.string.error),"RETRY",singleButtonCallback);
            }

        }
    }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(email)){
            et_email.setError("Username cannot be empty");
            et_email.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            et_password.setError("Password cannot be empty");
            et_password.requestFocus();
            return false;
        }
        return true;
    }



}
