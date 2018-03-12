package com.example.administrator.demo_wallpaper_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.adapter.Adapter_ChampionSelect;
import com.example.administrator.demo_wallpaper_android.async_system.BaseActyvity;
import com.example.administrator.demo_wallpaper_android.config.General_Config;
import com.example.administrator.demo_wallpaper_android.model.Item_General;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/15/2017.
 */

public class Act_ChampionSelect extends BaseActyvity {
    private GridView gvChampionSelect;
    private ArrayList<Item_General> arrChampionSelect;
    private ArrayList arrayList=new ArrayList();
    private int numberPhoto=200;
    private Adapter_ChampionSelect adapter_championSelect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        //connect(General_Config.ADDRESS+numberPhoto);
        initView();

    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_champion_select;
    }

    public void initView() {
        gvChampionSelect=(GridView)findViewById(R.id.gvChampionSelect);
        arrChampionSelect = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        arrayList = extras.getStringArrayList("image");
        for (int i=0;i<arrayList.size();i++) {
                Item_General item_general = (Item_General) arrayList.get(i);
                arrChampionSelect.add(new Item_General(item_general.getId(),item_general.getName(), item_general.getImage(),item_general.getCid()));
        }
        int a=1;
        while (a<100){
            for (int b = 0; b<arrChampionSelect.size(); b++){
                for (int j=b+1;j<arrChampionSelect.size();j++){
                    Item_General item_general11 = arrChampionSelect.get(b);
                    Item_General item_general1 = arrChampionSelect.get(j);
                    if (item_general11.getCid()==item_general1.getCid()){
                        arrChampionSelect.remove(j);
                    }
                }
            }
            a++;
        }
        //adapter_championSelect.notifyDataSetChanged();

        adapter_championSelect = new Adapter_ChampionSelect(Act_ChampionSelect.this,arrChampionSelect);
        gvChampionSelect.setAdapter(adapter_championSelect);
        gvChampionSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item_General item_general =arrChampionSelect.get(i);
                Intent intent = new Intent(Act_ChampionSelect.this,MainActivity.class);
                intent.putExtra("cid",item_general.getCid());
                intent.putExtra("cid1",1);
                setResult(61,intent);
                finish();
            }
        });

    }

    @Override
    public void suscess(String s) {
//        arrChampionSelect.clear();
//        try {
//            JSONObject jsonResponse = new JSONObject(s);
//            JSONArray cast = jsonResponse.getJSONArray("MaterialWallpaper");
//            for (int i=0;i<cast.length();i++){
//                JSONObject actor = cast.getJSONObject(i);
//                String name = actor.getString("category_name");
//                String hinh = actor.getString("image");
//                int cid = actor.getInt("cid");
//                Item_General item_general = new Item_General(1,name,hinh,cid);
//                arrChampionSelect.add(item_general);
//            }
//            int a=1;
//            while (a<100){
//                for (int b = 0; b<arrChampionSelect.size(); b++){
//                    for (int j=b+1;j<arrChampionSelect.size();j++){
//                        Item_General item_general11 = arrChampionSelect.get(b);
//                        Item_General item_general1 = arrChampionSelect.get(j);
//                        if (item_general11.getCid()==item_general1.getCid()){
//                            arrChampionSelect.remove(j);
//                        }
//                    }
//                }
//                a++;
//            }
//            adapter_championSelect.notifyDataSetChanged();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
