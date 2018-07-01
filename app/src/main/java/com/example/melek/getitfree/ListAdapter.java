package com.example.melek.getitfree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class ListAdapter extends BaseAdapter {

    ArrayList<post> arrayList;
    Context context;

    public ListAdapter(ArrayList<post> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView=LayoutInflater.from(context).inflate(R.layout.post,parent,false);
        }

        TextView t1 =(TextView) convertView.findViewById(R.id.label);
        TextView t2 =(TextView) convertView.findViewById(R.id.description);
       // TextView t3 =(TextView) convertView.findViewById(R.id.period_name);
        ImageView t4 =(ImageView) convertView.findViewById(R.id.icon);
        post u= arrayList.get(position);
        t1.setText(u.getLabel());
        t2.setText(u.getDescription());
       // t3.setText(u.getPeriod_name());
        Picasso.with(context).load(u.getUrl()).into(t4);

        return convertView;
    }


}
