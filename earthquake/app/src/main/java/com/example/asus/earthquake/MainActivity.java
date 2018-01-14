package com.example.asus.earthquake;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView l;
    SampleJason obj;
    Context c;

     String urlstr="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        c=this;


        UtoS obj1=new UtoS();
        URL url1=null;
        try {
            url1=new URL(urlstr);
        } catch (MalformedURLException e) {
            Log.e("TAG",e.getMessage());
        }
        obj1.execute(url1);



    }
    public class UtoS extends AsyncTask<URL,Void,String> {




        @Override
        protected String doInBackground(URL... urls) {
            URL url=urls[0];
            InputStream inputStream=null;
            InputStreamReader inputStreamReader=null;
            BufferedReader stringBuffer=null;
            StringBuilder stringBuilder=new StringBuilder();

            HttpURLConnection urlConnection=null;
            try {
                urlConnection= (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                if(urlConnection.getResponseCode()==200)
                {
                    inputStream=urlConnection.getInputStream();
                    inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    stringBuffer=new BufferedReader(inputStreamReader);
                    String line=stringBuffer.readLine();
                    while(line!=null)
                    {
                        stringBuilder.append(line);
                        line=stringBuffer.readLine();
                    }
                    Log.e("String Print",stringBuilder.toString());
                    return stringBuilder.toString();
                }
            } catch (Exception e) {
                Log.e("TAG",e.getMessage());
            }
            finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            if(inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("TAG",e.getMessage());
                }
                Log.e("TAG","reaching zero");
            return "chomu";
        }
        @Override
        protected void onPostExecute(final String jstring1)
        {
final String jstring=jstring1;
            Log.e("String return",jstring);
             final SampleJason sampleJason=new SampleJason(c);

            l=findViewById(R.id.listView);
            l.setAdapter(new listClass(c,jstring));
            l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ArrayList<SampleJason.data> list;

                    list=sampleJason.list1(jstring);
                    String str=list.get(i).url;
                    Uri uri=Uri.parse(str);
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(websiteIntent);
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
