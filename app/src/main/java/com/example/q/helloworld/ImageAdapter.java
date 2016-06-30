package com.example.q.helloworld;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Context;

import java.util.ArrayList;


/**
 * Created by q on 2016-06-28.
 */

public class ImageAdapter extends BaseAdapter {
    private MainActivity mContext;
    private ArrayList<Bitmap> mResized;

    public ImageAdapter(MainActivity c) {
        mContext = c;
        mResized = new ArrayList<>();
        for (int i = 0; i < this.getCount(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), mContext.mThumbIds[i]);
            mResized.add(bitmap.createScaledBitmap(bitmap, 500, 500, true));
        }
    }
    public int getCount() {
        return mContext.mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mResized.get(position));
        return imageView;
    }
}
