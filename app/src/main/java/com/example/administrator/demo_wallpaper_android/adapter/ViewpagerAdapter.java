package com.example.administrator.demo_wallpaper_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/22/2017.
 */

public class ViewpagerAdapter extends PagerAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private int positionimage;



    private ImageView imageView;
    private  ArrayList<String> images;

    public ViewpagerAdapter(Activity activity, ArrayList<String> images,int positionimage) {
        this.activity = activity;
        this.images = images;
        this.positionimage=positionimage;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position=positionimage;
        inflater=(LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.item_viewpager,container,false);
        imageView=(ImageView)itemView.findViewById(R.id.imgView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = width-10;
        params.height=height;
        imageView.setLayoutParams(params);
        Toast.makeText(activity,""+position,Toast.LENGTH_SHORT).show();
            Picasso.with(activity.getApplicationContext()).load("http://kiuerty.com/LeagueHD2/upload/"+images.get(position)).into(imageView);

        container.addView(itemView);
        return itemView;
    }
    public ArrayList<String> getImages() {
        return images;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
