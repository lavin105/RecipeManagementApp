package lavin105.recipemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*Login.java is currently unused but may be used later if the app expands*/
public class Login extends Activity {
    Button loginBtn;
    EditText password, email;
    TextView toSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginBtn=findViewById(R.id.login);
        password=findViewById(R.id.password_input_login);
        email=findViewById(R.id.email_input_login);
        toSignUp=findViewById(R.id.to_signup);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain= new Intent(Login.this,RecipeGrid.class);
                startActivity(toMain);
            }
        });

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignup= new Intent(Login.this,SignUp.class);
                startActivity(toSignup);
            }
        });

    }
}
