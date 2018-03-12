package com.example.administrator.demo_wallpaper_android.async_system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.demo_wallpaper_android.interfacesystem.General_Interface;

/**
 * Created by Administrator on 11/13/2017.
 */

public abstract class BaseActyvity extends AppCompatActivity implements General_Interface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initView();
    }

    protected abstract int getLayoutID();

    protected abstract void initView();

    @Override
    public void erorr() {
        Toast.makeText(this,"Lỗi, xin kiểm tra lại đường truyền",Toast.LENGTH_SHORT).show();
    }

    protected void connect(String api){
        General_AsyncTask general_asyncTask = new General_AsyncTask(this,BaseActyvity.this);
        general_asyncTask.execute(api);
    }
}
