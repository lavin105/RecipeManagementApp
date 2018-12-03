package lavin105.recipemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/*VideoComponent is essentially a custom webview that allows for an embedded youtube video.
* Uses a custom layout to be able to render the video within the application.*/

public class VideoComponent extends AppCompatActivity {

    private CustomWebView customWebView = null;
    private View newView;
    private RelativeLayout relativeLayout;
    private FrameLayout frameLayout;
    private WebChromeClient.CustomViewCallback customCall;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_layout);


        Intent intent= getIntent();
        String url=intent.getStringExtra("url");

        myWebView = (WebView) findViewById(R.id.video);
        customWebView = new CustomWebView();
        myWebView.setWebChromeClient(customWebView);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);

        myWebView.setWebViewClient(new WebViewClient(){
            @Override public void onReceivedError(WebView view, WebResourceRequest request,WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(VideoComponent.this,"Unable to load video...",Toast.LENGTH_LONG).show();
            }
        });
    }

    public class CustomWebView extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (newView != null) {
                callback.onCustomViewHidden();
                return;
            }
            relativeLayout = findViewById(R.id.video_view);
            relativeLayout.setVisibility(View.GONE);
            frameLayout = new FrameLayout(VideoComponent.this);
            frameLayout.setLayoutParams(LayoutParameters);
            frameLayout.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            frameLayout.addView(view);
            newView = view;
            customCall = callback;
            frameLayout.setVisibility(View.VISIBLE);
            setContentView(frameLayout);
        }

        @Override
        public void onHideCustomView() {
            if (newView == null) {
                return;
            } else {
                newView.setVisibility(View.GONE);
                frameLayout.removeView(newView);
                newView = null;
                frameLayout.setVisibility(View.GONE);
                customCall.onCustomViewHidden();
                relativeLayout.setVisibility(View.VISIBLE);
                setContentView(relativeLayout);
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (frameLayout != null)
            customWebView.onHideCustomView();
        else if (myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }
}
