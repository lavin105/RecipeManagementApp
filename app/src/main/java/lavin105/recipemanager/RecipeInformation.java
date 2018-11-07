package lavin105.recipemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeInformation extends Activity {
    RadioGroup switchView;
    TextView recipeName, recipeInstructions;
    ListView ingredientsList;
    ArrayList<String> theIngredients;
    ArrayAdapter<String> adapter;
    String[] ingred;
    String ing;
    RatingBar theRating;
    ImageView recipePicture;
    FloatingActionButton toYoutube, toWeb, toShare;
    Recipe theRecipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_information);
        switchView=findViewById(R.id.toggle);
        recipeName=findViewById(R.id.recipe_name);
        recipeInstructions=findViewById(R.id.instruction_description);
        ingredientsList=findViewById(R.id.ingredients_list);
        theRating=findViewById(R.id.the_recipe_rating);
        theRating.setRating(2);
        recipePicture=findViewById(R.id.the_recipe_picture);
        toShare=findViewById(R.id.share);
        toWeb=findViewById(R.id.web);
        toYoutube=findViewById(R.id.youtube);

        //TODO
        //Add getIntent here
        Intent fromRecipeGrid = getIntent();
        theRecipe=(Recipe)fromRecipeGrid.getSerializableExtra("recipe");
        ing=theRecipe.getIngredients();
        theRating.setRating(theRecipe.getRating());
        recipeName.setText(theRecipe.getName());
        recipeInstructions.setText(theRecipe.getInstructions());

        if(theRecipe.getImage_url().equals("")){
            recipePicture.setImageResource(R.drawable.recipe_manager_logo);
        }else {
            Picasso.get()
                    .load(theRecipe.getImage_url())
                    .error(R.drawable.recipe_manager_logo)
                    .fit()
                    .into(recipePicture);
        }



        ingred=ing.split(",");
        theIngredients= new ArrayList<String>(Arrays.asList(ingred));
        adapter= new ArrayAdapter<>(RecipeInformation.this,android.R.layout.simple_list_item_1,theIngredients);
        ingredientsList.setAdapter(adapter);
        ingredientsList.setVisibility(View.INVISIBLE);
        recipeInstructions.setVisibility(View.VISIBLE);
        recipeInstructions.setMovementMethod(new ScrollingMovementMethod());

        switchView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              if (checkedId== 2131230808){
                  System.out.println(checkedId);
                  ingredientsList.setVisibility(View.INVISIBLE);
                  recipeInstructions.setVisibility(View.VISIBLE);
              }if(checkedId== 2131230805){
                  System.out.println(checkedId);
                  ingredientsList.setVisibility(View.VISIBLE);
                  recipeInstructions.setVisibility(View.INVISIBLE);

              }
            }
        });

        toYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Youtube");
            }
        });
        toWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Web");
            }
        });
        toShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Share");
            }
        });
    }
}
