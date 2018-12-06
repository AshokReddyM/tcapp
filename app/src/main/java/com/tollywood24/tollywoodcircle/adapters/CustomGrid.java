package com.tollywood24.tollywoodcircle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tollywood24.tollywoodcircle.R;


public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final String[] imageid;

    public CustomGrid(Context c, String[] web, String[] Imageid) {
        mContext = c;
        this.imageid = Imageid;
        this.web = web;
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);

            Picasso.with(mContext).load(imageid[position]).fit().centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);


            textView.setText(web[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}