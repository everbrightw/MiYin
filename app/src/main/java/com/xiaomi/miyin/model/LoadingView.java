package com.xiaomi.miyin.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LoadingView {

    ProgressBar progressBar;
    ImageView imageView;
    TextView hint;
    MaterialButton refreshButton;

    public LoadingView(ProgressBar progressBar, ImageView imageView, TextView hint, MaterialButton refreshButton) {
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.hint = hint;
        this.refreshButton = refreshButton;
    }

    public void showOfflineInfo() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        hint.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
    }

    public void hideOfflineInfo() {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        hint.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
    }
}
