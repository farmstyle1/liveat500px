package com.wind.liveat500px.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wind.liveat500px.R;
import com.wind.liveat500px.fragment.MoreInfoFragment;

public class MoreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MoreInfoFragment.newInstance())
                    .commit();
        }
    }
}
