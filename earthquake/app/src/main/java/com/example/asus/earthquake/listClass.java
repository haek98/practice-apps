package com.example.asus.earthquake;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/27/2017.
 */

public class listClass extends BaseAdapter {
    Context c;
    SampleJason obj;
    ArrayList<SampleJason.data> list;
    public listClass(Context c,String jstring) {
        this.c=c;
        obj=new SampleJason(c);
        Log.e("String_in_list",jstring);
        list=obj.list1(jstring);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class holder{
        TextView t1,t2,t3,t4;
        public holder(View view)
        {
            t1=view.findViewById(R.id.textView);
            t2=view.findViewById(R.id.textView2);
            t3=view.findViewById(R.id.textView3);
            t4=view.findViewById(R.id.textView4);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

     View row=view;
     holder h=null;
     if(row==null)
     {
         LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         if (inflater != null) {
             row= inflater.inflate(R.layout.element,viewGroup,false);
         }
         h=new holder(row);
         row.setTag(h);
     }
     else{
         h= (holder) row.getTag();
     }
     h.t1.setText((( list.get(i)).magnitude).toString());
        h.t2.setText(((list.get(i)).place));
        h.t3.setText(((list.get(i)).date));
        h.t4.setText(((list.get(i)).time));
        return row;
    }
}
