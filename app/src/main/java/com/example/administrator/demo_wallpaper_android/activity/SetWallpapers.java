package com.example.administrator.demo_wallpaper_android.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by Administrator on 11/14/2017.
 */

public class SetWallpapers extends AppCompatActivity {
    private ImageView quick_start_cropped_image;

    private Button btnCrop;
    private Bitmap mBitmap1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_wallpapers);
        initView();
    }

    private void initView() {
        quick_start_cropped_image = (ImageView)findViewById(R.id.quick_start_cropped_image);
        btnCrop =(Button) findViewById(R.id.btnCrop);

        BitmapDrawable drawable = (BitmapDrawable) quick_start_cropped_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}

