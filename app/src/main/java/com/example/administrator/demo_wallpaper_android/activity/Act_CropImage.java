package com.example.administrator.demo_wallpaper_android.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by Administrator on 11/22/2017.
 */

public class Act_CropImage extends AppCompatActivity {
    private CropImageView cropImageView;
    private ImageView btnOK;
    private ImageView btnCacel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crop_image);
        initView();
    }

    private void initView() {
        cropImageView=(CropImageView)findViewById(R.id.cropImageView);
        btnOK=(ImageView) findViewById(R.id.btnOK);
        btnCacel=(ImageView) findViewById(R.id.btnCancle);
        btnCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        Picasso.with(Act_CropImage.this).load("http://kiuerty.com/LeagueHD2/upload/"+intent.getStringExtra("linkimage")).into(cropImageView);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImageView.startCrop(Uri.parse("sdf")
                        ,
                        new CropCallback() {
                            @Override
                            public void onSuccess(Bitmap cropped) {
                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                try {
                                    wallpaperManager.setBitmap(cropped);
                                    Toast.makeText(Act_CropImage.this,"Cài hình nền thành công",Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(Act_CropImage.this,"Thao tác thất bại",Toast.LENGTH_SHORT).show();
                            }
                        },

                        new SaveCallback() {
                            @Override
                            public void onSuccess(Uri outputUri) {

                            }

                            @Override
                            public void onError() {

                            }
                        }
                );
            }
        });


    }
}
