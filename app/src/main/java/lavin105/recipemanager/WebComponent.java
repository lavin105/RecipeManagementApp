package lavin105.recipemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.URLUtil;
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

        boolean load=URLUtil.isValidUrl(url);
        if(load){
            webView.loadUrl(url);

        }else{
            AlertDialog.Builder alert2= new AlertDialog.Builder(WebComponent.this);
            alert2.setTitle("Unable to load the specified website");
            alert2.setMessage("Please check that your url is valid.");
            alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   finish();
                }
            });


            final AlertDialog theAlert2=alert2.create();
            theAlert2.show();

        }



        webView.setWebViewClient(new WebViewClient(){
            @Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.stopLoading();
                webView.loadUrl("about:blank");
                AlertDialog.Builder alert2= new AlertDialog.Builder(WebComponent.this);
                alert2.setTitle("Unable to load the specified website");
                alert2.setMessage("Please check your connection.");
                alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });


                final AlertDialog theAlert2=alert2.create();
                theAlert2.show();            }
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
