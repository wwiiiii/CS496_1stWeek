package com.example.q.helloworld;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by q on 2016-06-29.
 */
public class Pager extends PagerAdapter {

    LayoutInflaterCompat inflater;
    public Pager(LayoutInflaterCompat inflater){
        this.inflater=inflater;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view=null;
        view=inflater.inflate(R.layout.viewpager_img, null);
        ImageView img=(ImageView) view.findViewById(R.id.imageView2);
    }
}
