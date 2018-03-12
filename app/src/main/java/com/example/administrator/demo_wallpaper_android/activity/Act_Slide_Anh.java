package com.example.administrator.demo_wallpaper_android.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.adapter.ViewpagerAdapter;
import com.example.administrator.demo_wallpaper_android.model.Item_General;
import com.example.administrator.demo_wallpaper_android.sqllite.SQLLiteData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/22/2017.
 */

public class Act_Slide_Anh extends AppCompatActivity {
    private ViewPager viewPager;
    ViewpagerAdapter viewpagerAdapter;
    private ArrayList<String>arrayList1;
    private int position;
    private ArrayList arrayList = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slide_anh);
        initView();
    }

    private void initView() {
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        Bundle extras = getIntent().getExtras();
        arrayList = extras.getStringArrayList("image");
        arrayList1= new ArrayList<>();
        for (int i=0;i<arrayList.size();i++){
            Item_General item_general= (Item_General) arrayList.get(i);
            arrayList1.add(item_general.getImage());
        }
        Intent intent=getIntent();
        position=intent.getIntExtra("position",1);
        viewpagerAdapter =new ViewpagerAdapter(Act_Slide_Anh.this,arrayList1,position);
        viewPager.setAdapter(viewpagerAdapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home){
            setResult(50);
            finish();
            overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
        }
        else if(item.getItemId()==R.id.setWallpaper){
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                ImageView imageView =viewpagerAdapter.getImageView();
                wallpaperManager.setBitmap(viewBitMap(imageView,imageView.getWidth(),imageView.getHeight()));
                Toast.makeText(Act_Slide_Anh.this,"Cài hình nền thành công",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }else if(item.getItemId()==R.id.slideShow){


        }else if(item.getItemId()==R.id.share){
            //startShare();
        }else if(item.getItemId()==R.id.save){
            //saveFile();

        }else if(item.getItemId()==R.id.pinchZoom){

        }else if(item.getItemId()==R.id.nextWallpaper){
//            position++;
//            changeBackground();
        }else if(item.getItemId()==R.id.previousWallpaper){
//            position--;
//            changeBackground();

        }else if(item.getItemId()==R.id.btnFavorite){

        }
        return super.onOptionsItemSelected(item);
    }

    public  static Bitmap viewBitMap(View view, int with, int height){
        Bitmap bitmap = Bitmap.createBitmap(with,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
