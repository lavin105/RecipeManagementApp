package lavin105.recipemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/*GetStarted.java is a landing page that is only ran if it is the first time the application is being ran.
* Uses shared preferences from RecipeGrid.java. The onclick method starts the RecipeGrid activity*/

public class GetStarted extends Activity {
    Button getStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_started_layout);
        getStarted=findViewById(R.id.get_started);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetStarted.this, RecipeGrid.class);
                startActivity(i);
            }
        });

    }
}
