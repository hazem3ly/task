package com.example.hazem.task.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hazem.task.Fragments.MainFragment;
import com.example.hazem.task.R;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle data = new Bundle();
        data.putString("email",getIntent().getStringExtra("email"));
        data.putString("password",getIntent().getStringExtra("password"));


        MainFragment mMainFragment = new MainFragment();
        mMainFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain,mMainFragment,"").commit();

    }

}
