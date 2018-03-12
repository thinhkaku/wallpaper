package com.example.administrator.demo_wallpaper_android.activity;

import android.animation.FloatEvaluator;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.model.Item_General;
import com.example.administrator.demo_wallpaper_android.sqllite.SQLLiteData;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Administrator on 11/13/2017.
 */

public class Act_Zoom_General extends AppCompatActivity implements View.OnTouchListener {
    private ImageView imgGeneral;
    int position;
    private SQLLiteData sqlLiteData;
    private File file11;
    private CountDownTimer countDownTimer;
    private boolean ktSlideWallwapper=true;
    ArrayList<String>arrayList1;
    private ArrayList arrayList;
    int MIN_DISTANCE = 100;
    private float downX, upX;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_zoom_general);
        initActionBar();
        initView();
        addEvent();
        countDownTimer = new CountDownTimer(10000000,4000) {
            @Override
            public void onTick(long l) {
                position++;
                if (position==arrayList1.size()){
                    position=0;
                }
                changeBackground();
                cancel();
            }
            @Override
            public void onFinish() {
            }
        };

    }
    private void addEvent() {
        imgGeneral.setOnTouchListener(this);
    }

    private void initActionBar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initView() {
        imgGeneral =(ImageView) findViewById(R.id.imgGeneralZoom);
        getHeightWidth();
        Intent intent=getIntent();
        position=intent.getIntExtra("position",1);
        Bundle extras = getIntent().getExtras();
        arrayList = extras.getStringArrayList("image");
        arrayList1= new ArrayList<>();
        for (int i=0;i<arrayList.size();i++){
            Item_General item_general= (Item_General) arrayList.get(i);
            arrayList1.add(item_general.getImage());
        }

        Item_General item_general = (Item_General) arrayList.get(position);
        Picasso.with(Act_Zoom_General.this).load("http://kiuerty.com/LeagueHD2/upload/"+item_general.getImage()).into(imgGeneral);
    }
    public  static Bitmap viewBitMap(View view,int with, int height){
        Bitmap bitmap = Bitmap.createBitmap(with,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    private void getHeightWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = imgGeneral.getLayoutParams();
        params.width = width-10;
        params.height=height;
        imgGeneral.setLayoutParams(params);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==android.R.id.home){
            setResult(50);
            finish();
            overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
        }
        else if(item.getItemId()==R.id.setWallpaper){
            Item_General item_general = (Item_General) arrayList.get(position);
            Intent intent = new Intent(Act_Zoom_General.this,Act_CropImage.class);
            intent.putExtra("linkimage",item_general.getImage());
            startActivity(intent);
        }else if(item.getItemId()==R.id.slideShow){

            if (ktSlideWallwapper==true){
                countDownTimer.start();
                ktSlideWallwapper=false;
            }else if (ktSlideWallwapper==false){
                countDownTimer.cancel();
                ktSlideWallwapper=true;
            }

        }else if(item.getItemId()==R.id.share){
            startShare();
        }else if(item.getItemId()==R.id.save){
            saveFile();

        }else if(item.getItemId()==R.id.pinchZoom){

        }else if(item.getItemId()==R.id.nextWallpaper){
            position++;
            changeBackground();
        }else if(item.getItemId()==R.id.previousWallpaper){
            position--;
            changeBackground();

        }else if(item.getItemId()==R.id.btnFavorite){

            Item_General item_general111 = (Item_General) arrayList.get(position);
            int id=item_general111.getId();
            String name=item_general111.getName();
            String image=item_general111.getImage();
            int cid=item_general111.getCid();
            Item_General item_general =new Item_General(id,name,image,cid);
            sqlLiteData= new SQLLiteData(Act_Zoom_General.this);
            sqlLiteData.insert(item_general);
            Toast.makeText(Act_Zoom_General.this,"Thêm vào ưa thích thành công",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.translate_enter,R.anim.translate_edt);
    }

    private void changeBackground(){
        String item_general=arrayList1.get(position);
        Picasso.with(Act_Zoom_General.this).load("http://kiuerty.com/LeagueHD2/upload/"+item_general).into(imgGeneral);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();

                float deltaX = downX - upX;

                if(Math.abs(deltaX) > MIN_DISTANCE){
                    if(deltaX < 0) { this.onLeftToRightSwipe(); return true; }
                    if(deltaX > 0) { this.onRightToLeftSwipe(); return true; }
                }
                else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    public void onRightToLeftSwipe(){
        position++;

            if (position==arrayList1.size()){
                position=0;
            }
        changeBackground();

    }

    public void onLeftToRightSwipe(){
        position--;
        if (position==-1){
            position=arrayList1.size()-1;
        }
        changeBackground();

    }

    private void saveFile(){
        FileOutputStream outputStream = null;
        File file=getDisc();
        if (!file.exists()&&!file.mkdirs()){
            Toast.makeText(Act_Zoom_General.this,"không thể tạo thư mục lưu ảnh",Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date=simpleDateFormat.format(new Date());
        String name="Img"+date+".jpg";
        String file_name=file.getAbsolutePath()+"/"+name;
        File new_file=new File(file_name);
        try {
            outputStream=new FileOutputStream(new_file);
            Bitmap bitmap= viewBitMap(imgGeneral,imgGeneral.getWidth(),imgGeneral.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Toast.makeText(Act_Zoom_General.this,"Lưu ảnh thành công",Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refeshGallery(new_file);
    }


    private void refeshGallery(File new_file) {
        file11=new_file;
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new_file));
        sendBroadcast(intent);
    }

    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"Image");
    }

    private void  startShare(){
        Bitmap bitmap =  viewBitMap(imgGeneral,imgGeneral.getWidth(),imgGeneral.getHeight());
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        File file= new File(Environment.getExternalStorageDirectory()+File.separator+"ImageDemo.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream=new FileOutputStream(file);
            outputStream.write(byteArrayOutputStream.toByteArray());
        }catch (IOException e){
            e.fillInStackTrace();
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file:///sdcard/ImageDemo.jpg"));
        startActivity(Intent.createChooser(shareIntent,"Share Image"));
    }

}

