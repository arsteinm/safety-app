package com.plusmobileapps.safetyapp.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.print.*;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.plusmobileapps.safetyapp.R;


public class exportPdf extends AppCompatActivity {
    WebView actionItems;
    ProgressBar progressBar;
    Button returnMain, print;
    Handler handler;
    int aniDuration = 500;

    final Runnable showWebView = () -> {
        AlphaAnimation fadeInAni = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeOutAni = new AlphaAnimation(1.0f, 0.0f);
        fadeInAni.setDuration(aniDuration);
        fadeOutAni.setDuration(aniDuration);
        fadeOutAni.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        progressBar.startAnimation(fadeOutAni);
        actionItems.startAnimation(fadeInAni);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_webview);
        actionItems = findViewById(R.id.webView);
        returnMain = findViewById(R.id.webViewBack);
        progressBar = findViewById(R.id.progressBar);
        print = findViewById(R.id.print);
        handler = new Handler();

        returnMain.setOnClickListener(v -> {
            this.finish();
        });
        print.setOnClickListener(v -> {
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printAdapter = actionItems.createPrintDocumentAdapter("ActionItemsDoc");
            String jobName = getString(R.string.app_name) + " Document";
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
            printManager.print(jobName, printAdapter, builder.build());
        });

        actionItems.setAlpha(0);
        actionItems.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d("Web View", "Finished loading");
                handler.postDelayed(showWebView, 0);
            }
        });
        actionItems.loadUrl("file:///data/data/com.plusmobileapps.safetyapp/files/SafetyAppExport.html");
        progressBar.setProgress(50);
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

}