package com.example.archapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }

    MaterialDialog dialog;
    private RecyclerView rc;
    private static DataAdapter mAdapter;
    static  ArrayList<Designs> designsArrayList ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        initViews(view);
        getClients();
        return view;
    }
    private void initViews(View view) {

        RecyclerView rc = view.findViewById(R.id.rv);
        rc.setHasFixedSize(true);
        RecyclerView.LayoutManager rl = new LinearLayoutManager(getActivity());
        rc.setLayoutManager(rl);

        designsArrayList = new ArrayList<>();
        // final ArrayList<Clients> clients = sqliteHelper.getSpecificClients(type);
        mAdapter = new DataAdapter(designsArrayList,getActivity());
        rc.setAdapter(mAdapter);



    }
    //get user clients
    private void getClients(){

        new myImages().execute("https://majaribio.000webhostapp.com/getImage.php");
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
    private class myImages extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            configureDialog("please wait","fetching");
            dialog.show();
        }
        protected String  doInBackground(String... params) {
            return NetworkHandler.get(params[0]);
        }
        protected void onPostExecute(String result) {
            dialog.dismiss();
            ArrayList<Designs> designs = new ArrayList<>();
            if (result != null) {
                try {
                    Object json = new JSONTokener(result).nextValue();
                    if (json instanceof JSONObject) {
                        final JSONObject object = new JSONObject(result);
                        String status = object.getString("status");
                        if (status.equals("success") ) {
                            JSONArray clientsArr = object.getJSONArray("images");
                            for (int i = 0; i < clientsArr.length(); i++)
                            {
                                //getting each client obj
                                JSONObject obj = clientsArr.getJSONObject(i);
                                //adding clients to arraylist
                              Designs designs1 = new Designs();
                              designs1.setDescription(obj.getString("description"));
                              designs1.setImage_url("https://majaribio.000webhostapp.com/uploads/"+obj.getString("file_name"));
                              OneFragment.designsArrayList.add(designs1);


                            }
                            OneFragment.mAdapter.notifyDataSetChanged();


                        } else {
                            MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    getClients();
                                }
                            };
                            Constants.showDialog(getActivity(),"Error",object.getString("message"),"RETRY",singleButtonCallback);
                        }

                    } else {
                        MaterialDialog.SingleButtonCallback singleButtonCallback=new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                getClients();
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
                        getClients();
                    }
                };
                Constants.showDialog(getContext(),"Error",getResources().getString(R.string.error),"RETRY",singleButtonCallback);
            }

        }
    }

}
