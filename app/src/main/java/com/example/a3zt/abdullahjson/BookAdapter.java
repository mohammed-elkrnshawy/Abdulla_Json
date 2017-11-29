package com.example.a3zt.abdullahjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by 3ZT on 8/9/2017.
 */

public class BookAdapter extends ArrayAdapter {

    private List<UserDetails> UserModelList;
    private int resource;
    private LayoutInflater inflater;
    public BookAdapter(Context context, int resource, List<UserDetails> objects) {
        super(context, resource, objects);
        UserModelList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(resource, null);


            holder.tvName = (TextView)convertView.findViewById(R.id.TV_Name);
            holder.tvEmail = (TextView)convertView.findViewById(R.id.TV_Email);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        final ViewHolder finalHolder = holder;

        holder.tvName.setText(UserModelList.get(position).Name);
        holder.tvEmail.setText(UserModelList.get(position).Emial);

        return convertView;
    }


    class ViewHolder{
        private TextView tvName;
        private TextView tvEmail;

    }

}