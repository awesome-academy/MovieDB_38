package com.ptit.filmdictionary.ui.actor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ptit.filmdictionary.R;

public class ActorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.TransparentStatusTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
    }
}
