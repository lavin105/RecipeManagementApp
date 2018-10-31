package lavin105.recipemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends Activity {
    Button signUp;
    EditText email, password;
    TextView goToLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signUp=findViewById(R.id.sign_up);
        email=findViewById(R.id.email_input);
        password=findViewById(R.id.password_input);
        goToLogin=findViewById(R.id.to_login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, RecipeGrid.class);
                startActivity(i);
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(SignUp.this, Login.class);
                startActivity(toLogin);
            }
        });
    }
}
