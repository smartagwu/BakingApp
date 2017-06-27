package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AGWU SMART ELEZUO on 6/24/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private JSONArray  mArray;
    private static final int[] mImages = {R.drawable.nutellapie1, R.drawable.brownies1, R.drawable.yellowcake1, R.drawable.cheesecake1};

    public ListProvider(Context context, Intent i){
        mContext = context;
        if (i.hasExtra("arrayString")) {
            String s = i.getStringExtra("arrayString");
            try {
                JSONArray jsonArray = new JSONArray(s);
                mArray = jsonArray;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
        if (mArray == null) return 0;
        return mArray.length();
    }

    @Override
    public RemoteViews getViewAt(int position) {
       RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widgetlistitem);

        try {
            JSONObject object = mArray.getJSONObject(position);
            views.setImageViewResource(R.id.imgIcon, mImages[position]);
            views.setTextViewText(R.id.cakeType, object.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.putExtra("position", position);
        views.setOnClickFillInIntent(R.id.cakeType, intent);

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
