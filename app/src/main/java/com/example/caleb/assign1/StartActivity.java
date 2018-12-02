package com.example.caleb.assign1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Caleb on 3/6/2018.
 */

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);





    }
    //onclick for the button
    //moves to the next activity
    public void Play(View view){
        startActivity(new Intent(StartActivity.this, MainActivity.class));

    }
}
