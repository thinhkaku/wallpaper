package com.example.administrator.demo_wallpaper_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.adapter.Adapter_General;
import com.example.administrator.demo_wallpaper_android.model.Item_General;
import com.example.administrator.demo_wallpaper_android.sqllite.SQLLiteData;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/18/2017.
 */

public class Act_General_Favorie extends AppCompatActivity {
    private SQLLiteData sqlLiteData;
    private ArrayList<Item_General>arrGeneral=new ArrayList<>();
    private GridView gvHinhFavorite;
    private Adapter_General adapter_general;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_general_favorite);
         sqlLiteData= new SQLLiteData(this);
         initActionBar();
        readData();
        initView();
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void readData() {
        arrGeneral.clear();
        arrGeneral.addAll(sqlLiteData.getDataGeneral());
    }

    private void initView() {
        gvHinhFavorite=(GridView) findViewById(R.id.gvHinhFavorite);
        adapter_general= new Adapter_General(Act_General_Favorie.this,arrGeneral);
        gvHinhFavorite.setAdapter(adapter_general);
        gvHinhFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(Act_General_Favorie.this,Act_Zoom_General.class);
                intent.putExtra("position",i);
                intent.putExtra("image",arrGeneral);
                startActivityForResult(intent,50);
                overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
            }
        });
        gvHinhFavorite.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item_General item_general=arrGeneral.get(i);
                sqlLiteData.delete(item_general.getId());
                arrGeneral.clear();
                readData();
                adapter_general.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
        }
        return super.onOptionsItemSelected(item);
    }
}
