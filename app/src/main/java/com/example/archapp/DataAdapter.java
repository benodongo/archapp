package com.example.archapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Designs> designsArrayList;
    private Context context;

    public DataAdapter(ArrayList<Designs> designsArrayList, Context context) {
        this.designsArrayList = designsArrayList;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_desc.setText(designsArrayList.get(i).getDescription());
        Picasso.with(context).load(designsArrayList.get(i).getImage_url()).resize(170, 170).into(viewHolder.img_design);
    }

    @Override
    public int getItemCount() {
        return designsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_desc;
        private ImageView img_design;
        public ViewHolder(View view) {
            super(view);

            tv_desc = (TextView)view.findViewById(R.id.tv_android);
            img_design = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}
