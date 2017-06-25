package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.FragmentClasses.CakeRecipeFragment;
import com.example.android.bakingapp.FragmentClasses.VideoFragment;

/**
 * Created by AGWU SMART ELEZUO on 6/24/2017.
 */

public class StepActivity extends AppCompatActivity {

    private int Position;
    ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steplayout);

        FragmentManager fragmentManager = getSupportFragmentManager();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        CakeRecipeFragment cakeRecipeFragment = new CakeRecipeFragment();
        VideoFragment videoFragment = new VideoFragment();

        if (findViewById(R.id.videofragment) != null){
            fragmentManager.beginTransaction().add(R.id.videofragment, videoFragment).commit();
        }

        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        if (intent.hasExtra("position") && intent.hasExtra("name")) {
            bundle = intent.getExtras();
            Position = bundle.getInt("position");
            actionBar.setTitle(bundle.getString("name"));
        }
        cakeRecipeFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.cakerecipefragment, cakeRecipeFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void onClickSteps(String Description, String videoURL, String sDescription) {

        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("description", Description);
        bundle.putString("videoURL", videoURL);

        if (bundle != null) {
            if (!isFinishing()) {

                videoFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (findViewById(R.id.videofragment) != null){
                    transaction.replace(R.id.videofragment, videoFragment);
                }
                transaction.replace(R.id.cakerecipefragment, videoFragment).addToBackStack(null);
                transaction.commit();

            }
        }

        actionBar.setTitle(sDescription);
    }
}
