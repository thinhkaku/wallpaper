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
 * Created by Administrator on 11/15/2017.
 */

public class Adapter_ChampionSelect extends ArrayAdapter<Item_General> {
    private LayoutInflater inflater;
    private ArrayList<Item_General>arrChampionSelect= new ArrayList<>();
    public Adapter_ChampionSelect(@NonNull Context context, @NonNull ArrayList<Item_General> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        inflater=LayoutInflater.from(context);
        arrChampionSelect=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        ViewHodler viewHodler;
        if (v==null){
            viewHodler = new ViewHodler();
            v= inflater.inflate(R.layout.item_championselect,parent,false);
            viewHodler.txtChampionSelect=(TextView) v.findViewById(R.id.txtChampionSelect);
            viewHodler.imgChampionSelect=(ImageView) v.findViewById(R.id.imgChampionSelect);
            v.setTag(viewHodler);
        }else {
            viewHodler= (ViewHodler) v.getTag();
        }
        Item_General item_general = arrChampionSelect.get(position);
        viewHodler.txtChampionSelect.setText(item_general.getName());
        Picasso.with(getContext()).load("http://kiuerty.com/LeagueHD2/upload/thumbs/"+item_general.getImage()).into(viewHodler.imgChampionSelect);
        return  v;
    }

    class ViewHodler{
        private ImageView imgChampionSelect;
        private TextView txtChampionSelect;
    }
}
