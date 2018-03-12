package com.example.administrator.demo_wallpaper_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.model.Item_General;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/13/2017.
 */

public class Adapter_General extends ArrayAdapter<Item_General> {
    private LayoutInflater inflater ;
    private ArrayList<Item_General>arrayListGeneral= new ArrayList<>();
    public Adapter_General(@NonNull Context context, @NonNull ArrayList<Item_General> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        inflater=LayoutInflater.from(context);
        arrayListGeneral=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        ViewHoler viewHoler;
        if (v==null){
            viewHoler = new ViewHoler();
            v=inflater.inflate(R.layout.item_general,parent,false);
            viewHoler.imgGeneral =(ImageView) v.findViewById(R.id.imgGeneral);
            //viewHoler.txtNameGeneral =(TextView) v.findViewById(R.id.txtNameGeneral);
            v.setTag(viewHoler);
        }
        else {
            viewHoler= (ViewHoler) v.getTag();
        }
        Item_General item_general =arrayListGeneral.get(position);
        Picasso.with(getContext()).load("http://kiuerty.com/LeagueHD2/upload/thumbs/"+item_general.getImage()).placeholder(R.drawable.gallery).into(viewHoler.imgGeneral);
        return v;
    }

    class ViewHoler{
        private ImageView imgGeneral;
        //private TextView txtNameGeneral;
    }
}
