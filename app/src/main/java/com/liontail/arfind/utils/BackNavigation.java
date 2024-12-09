package com.liontail.arfind.utils;

import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BackNavigation {
    private final AppCompatActivity activity;
    public BackNavigation(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void setupToolbarWithBackButton(Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(view -> onBackButtonPressed());
    }
    public void setupLinearLayoutWithBackButton(LinearLayout linearLayout) {
        linearLayout.setOnClickListener(view -> onBackButtonPressed());
    }
    private void onBackButtonPressed() {
        activity.onBackPressed();
    }
}
