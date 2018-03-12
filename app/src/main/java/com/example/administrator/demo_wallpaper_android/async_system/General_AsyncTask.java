package com.example.administrator.demo_wallpaper_android.async_system;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.administrator.demo_wallpaper_android.R;
import com.example.administrator.demo_wallpaper_android.interfacesystem.General_Interface;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 11/13/2017.
 */

public class General_AsyncTask extends AsyncTask<String,Void,String> {
    private General_Interface general_interface;
    private Context context;
    private AlertDialog progressDialog;
    public General_AsyncTask(General_Interface general_interface, Context context) {
        this.general_interface = general_interface;
        this.context = context;
        progressDialog = new SpotsDialog(context, R.style.Custom);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
    }

    @Override
    protected String doInBackground(String... strings) {
        //String link = strings[0];
        //URL url = null;
        try {
            //url = new URL(link);

            URL url = new URL(strings[0]);
            InputStream inputStream = url.openConnection().getInputStream();
            //URLConnection connection =url.openConnection();
            //InputStream inputStream = connection.getInputStream();
            byte[]b = new byte[1024];
            int count = inputStream.read(b);
            String s ="";
            while (count!=-1){
                s+=new String(b,0,count,"utf-8");
                count =inputStream.read(b);

            }
            inputStream.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null ||s.isEmpty()){
            general_interface.erorr();
        }else {
            general_interface.suscess(s);
        }
        progressDialog.dismiss();
    }
}
