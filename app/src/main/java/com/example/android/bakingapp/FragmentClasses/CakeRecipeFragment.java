package com.example.android.bakingapp.FragmentClasses;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.Utils.NetworkUtils;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.StepActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class CakeRecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<JSONArray>, CakeRecipeAdapter.ClickOnVideosListener{

    private RecyclerView mIngRecyclerView;
    private RecyclerView mRecyclerView;
    private IngredientAdapter mAdapter;
    private CakeRecipeAdapter adapter;
    private static final int LOADERMANAGER_ID = 1;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private LinearLayout includeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels/displayMetrics.density;

        }


    public void onError(){
        includeLayout.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
    }

    public void displayRecyclerView(){
        includeLayout.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<JSONArray>(getContext()) {

            JSONArray mArray;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (mArray != null){
                    deliverResult(mArray);
                }else {

                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public JSONArray loadInBackground() {
                URL mUrl = NetworkUtils.getUrl();
                String jsonString = null;
                JSONArray cakeArray = null;

                try {
                    jsonString = NetworkUtils.getResponseFromHttpUrl(mUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    cakeArray = new JSONArray(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return cakeArray;
            }

            @Override
            public void deliverResult(JSONArray data) {
                super.deliverResult(data);
                mArray = data;
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cake_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mIngRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mTextView = (TextView) view.findViewById(R.id.errorText);
        includeLayout = (LinearLayout) view.findViewById(R.id.includeLayout);

        adapter = new CakeRecipeAdapter(getContext(), this);
        mAdapter = new IngredientAdapter(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);


        mIngRecyclerView.setHasFixedSize(true);
        mIngRecyclerView.setLayoutManager(mManager);
        mIngRecyclerView.setAdapter(mAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
            getLoaderManager().initLoader(LOADERMANAGER_ID, null, this);
        } else {
            onError();
            mTextView.setText(R.string.errorText);
        }
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {

        JSONArray ingredient = null;
        JSONArray steps = null;
        int position = 0;

        mProgressBar.setVisibility(View.INVISIBLE);
        if (data == null){
            onError();
            mTextView.setText(R.string.retrival_error);
        }else {

            try {

                Bundle bundle = getArguments();
                if (bundle != null) {
                    position = bundle.getInt("position");
                }
                JSONObject ingredientObject = data.getJSONObject(position);
                ingredient = ingredientObject.getJSONArray("ingredients");
                steps = ingredientObject.getJSONArray("steps");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.swapArray(steps);
            mAdapter.swapArray(ingredient);

        }
    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

        getLoaderManager().initLoader(LOADERMANAGER_ID, null, this);
    }

    @Override
    public void onClickVideos(String Description, String videoURL, String sDescription) {
        ((StepActivity) getActivity()).onClickSteps(Description, videoURL, sDescription);
    }
}