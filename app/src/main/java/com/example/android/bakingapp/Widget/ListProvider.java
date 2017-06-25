package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.R;

import org.json.JSONArray;

/**
 * Created by AGWU SMART ELEZUO on 6/24/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private JSONArray  mArray;
    private static final int[] mImages = {R.drawable.nutellapie1, R.drawable.brownies1, R.drawable.yellowcake1, R.drawable.cheesecake1};
    private static final String[] s = {"Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};

    public ListProvider(Context context, Intent i){
        mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return s.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
       RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widgetlistitem);

        views.setImageViewResource(R.id.imgIcon, mImages[position]);
        views.setTextViewText(R.id.cakeType, s[position]);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
