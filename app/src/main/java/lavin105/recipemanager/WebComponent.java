package lavin105.recipemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
/*Simple webview that loads the recipes link if it has one*/

public class WebComponent extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        webView=findViewById(R.id.webview);

        Intent i = getIntent();
        String url=i.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);



        webView.setWebViewClient(new WebViewClient(){
            @Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.stopLoading();
                webView.loadUrl("about:blank");
                Toast.makeText(WebComponent.this,"Unable to load webpage...",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }

    }

}
