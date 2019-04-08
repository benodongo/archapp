package com.example.archapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

private EditText et_Type,et_section,et_rooms;
private Button btnSave;
private String Type,Section, Rooms;
MaterialDialog dialog;
    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        et_Type = view.findViewById(R.id.etDesignType);
        et_section = view.findViewById(R.id.etDesignCompatment);
        et_rooms = view.findViewById(R.id.etDesignRooms);
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = et_Type.getText().toString();
                Section = et_section.getText().toString();
                Rooms =et_rooms.getText().toString();
                save();
            }
        });
    }

    private void save() {
        String params = null;
        try {
            params = "Type="+ URLEncoder.encode(Type, "UTF-8")+
                    "&Section="+ URLEncoder.encode(Section, "UTF-8")+
                    "&Rooms="+ URLEncoder.encode(Rooms, "UTF-8")
            ;
            new saveTask().execute("https://majaribio.000webhostapp.com/api/register.php", params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void configureDialog(String title,String message){
        dialog=new MaterialDialog.Builder(getActivity())
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
    private class saveTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            configureDialog("Request","Sending....");
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


                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            startActivity(i);




                        } else {
                            MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    save();
                                }
                            };
                            Constants.showDialog(getActivity(),"Error",object.getString("message"),"RETRY",singleButtonCallback);
                        }

                    } else {
                        MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                save();
                            }
                        };
                        Constants.showDialog(getActivity(),"Error",getResources().getString(R.string.error),"RETRY",singleButtonCallback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        save();
                    }
                };
                Constants.showDialog(getActivity(),"Error",getResources().getString(R.string.error),"RETRY",singleButtonCallback);
            }

        }
    }

}
