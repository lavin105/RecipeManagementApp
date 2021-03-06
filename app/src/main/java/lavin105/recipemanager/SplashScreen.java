package lavin105.recipemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    public static int TIME=2500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, RecipeGrid.class);
                startActivity(i);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();

                }else{
                    finish();

                }

            }
        },TIME);
    }
}
