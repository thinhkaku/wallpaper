package com.example.administrator.demo_wallpaper_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.adapter.Adapter_General;
import com.example.administrator.demo_wallpaper_android.async_system.BaseActyvity;
import com.example.administrator.demo_wallpaper_android.config.General_Config;
import com.example.administrator.demo_wallpaper_android.model.Item_General;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class MainActivity extends BaseActyvity implements View.OnClickListener {
    private GridView gvGeneral;
    private ArrayList<Item_General>arrGeneral;
    private ArrayList<Item_General>arrGeneral1=new ArrayList<>();
    private LinearLayout linearRecentImage,linearChampionSelect,linearFavorite,linearRateApp,linearMoreApps,linearSetting;
    private Adapter_General adapter_general;
    private ImageButton btnNext;
    int numberPhoto=30;
    int cid=-1;
    int cid1=0;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Intent  intent=getIntent();
        connect(General_Config.ADDRESS+numberPhoto);

        initView();
        addEvent();
    }

    private void addEvent() {
        linearRecentImage.setOnClickListener(this);
        linearChampionSelect.setOnClickListener(this);
        linearFavorite.setOnClickListener(this);
        linearRateApp.setOnClickListener(this);
        linearMoreApps.setOnClickListener(this);
        linearSetting.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        linearRecentImage = (LinearLayout)findViewById(R.id.linearRecentImage);
        linearChampionSelect = (LinearLayout)findViewById(R.id.linearChampionSelect);
        linearFavorite = (LinearLayout)findViewById(R.id.linearFavorite);
        linearRateApp = (LinearLayout)findViewById(R.id.linearRateApp);
        linearMoreApps = (LinearLayout)findViewById(R.id.linearMoreApps);
        linearSetting = (LinearLayout)findViewById(R.id.linearSetting);
        gvGeneral = (GridView)findViewById(R.id.gvHinh);
        btnNext=(ImageButton)findViewById(R.id.btnNext);
        arrGeneral = new ArrayList<>();
        adapter_general = new Adapter_General(MainActivity.this,arrGeneral);
        gvGeneral.setAdapter(adapter_general);

        gvGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(MainActivity.this,Act_Zoom_General.class);
                intent.putExtra("position",i);
                intent.putExtra("image",arrGeneral);
                startActivityForResult(intent,50);
                overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }



    @Override
    public void suscess(String s) {

        arrGeneral.clear();
            try {
                JSONObject jsonResponse = new JSONObject(s);
                JSONArray cast = jsonResponse.getJSONArray("MaterialWallpaper");
                for (int i=0;i<cast.length();i++){
                    JSONObject actor = cast.getJSONObject(i);
                    String name = actor.getString("category_name");
                    String hinh = actor.getString("image");
                    int cid = actor.getInt("cid");
                    Item_General item_general = new Item_General(1,name,hinh,cid);
                    arrGeneral.add(item_general);
                    if (arrGeneral.size()==numberPhoto){
                            arrGeneral1.clear();
                            for (int c=0;c<numberPhoto;c++)
                            {
                                Item_General item_general11 = arrGeneral.get(c);
                                arrGeneral1.add(item_general11);
                                adapter_general.notifyDataSetChanged();
                            }
                    }
                }

                int a=0;
                while (a<100){
                    for (int i=0;i<arrGeneral.size();i++){
                        Item_General item_general= arrGeneral.get(i);
                        if (item_general.getCid()!=cid&&cid1==1){
                            arrGeneral.remove(i);
                        }
                    }
                    a++;
                }
                cid1=0;
                adapter_general.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==51&&resultCode==61){
            cid=data.getIntExtra("cid",0);
            cid1=data.getIntExtra("cid1",0);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connect(General_Config.ADDRESS+numberPhoto);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                drawer.closeDrawers();
                numberPhoto+=30;
                connect(General_Config.ADDRESS+numberPhoto);
                if (arrGeneral.size()==numberPhoto){
                    arrGeneral1.clear();
                    for (int c=0;c<numberPhoto;c++)
                    {
                        Item_General item_general11 = arrGeneral.get(c);
                        arrGeneral1.add(item_general11);
                        adapter_general.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.linearRecentImage:
                drawer.closeDrawers();
                connect(General_Config.ADDRESS+numberPhoto);

                break;
            case R.id.linearChampionSelect:
                drawer.closeDrawers();
                Intent intent =new Intent(MainActivity.this,Act_CropImage.class);
                intent.putExtra("image",arrGeneral);
                startActivityForResult(intent,51);
                overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);

                break;
            case R.id.linearFavorite:
                drawer.closeDrawers();
                Intent intentFavorite=new Intent(MainActivity.this,Act_General_Favorie.class);
                startActivity(intentFavorite);
                overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);

                break;
            case R.id.linearRateApp:
                drawer.closeDrawers();
                break;
            case R.id.linearMoreApps:
                drawer.closeDrawers();
                break;
            case R.id.linearSetting:
                drawer.closeDrawers();
                break;
        }
    }


}
