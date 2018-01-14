package com.example.asus.earthquake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ASUS on 12/27/2017.
 */

public class SampleJason {
    private Context context;



    public SampleJason(Context context) {
        this.context = context;

    }

    class data {

        Double magnitude;
        String place;
        String date;
        String time;
        String url;
        public data(Double magnitude, String place, String date,String time,String url) {
            this.magnitude = magnitude;
            this.place = place;
            this.date = date;
            this.time=time;
            this.url=url;

        }


    }

    public ArrayList<data> list1(String jstring) {
        ArrayList<data> temp = new ArrayList<data>();
        try {
                 Log.e("String received",jstring);
            JSONObject jsonObject = new JSONObject(jstring);
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject prop=jsonObject1.getJSONObject("properties");

                Long time = (prop.getLong("time"));
                Date date=new Date(time);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("LLL dd,yyyy");
                String d=simpleDateFormat.format(date);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleTimeFormat=new SimpleDateFormat("h:mm a");
                String t=simpleTimeFormat.format(date);
                temp.add(new data(prop.getDouble("mag"), prop.getString("place"), d,t,prop.getString("url")));
            }
        } catch (JSONException e) {
            Log.e("TAG","exception caught");
            e.printStackTrace();
        }
        return temp;
    }


}
