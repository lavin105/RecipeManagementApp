package lavin105.recipemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {
    EditText recipeName,imageLink,webLink,youtubeLink,cookingInstructions,theIngredient;
    Button addRecipe,addIngredient;
    FloatingActionButton toIamges, toYoutube, toWeb;
    ListView ingredinetsList;
    ArrayList<String> ingredientsArrayList;
    ArrayAdapter<String> adapter;
    private static final String SEPARATOR = ",";
    RatingBar recipeRating;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);
        //------------------------------------------------------------------------------
        recipeName=findViewById(R.id.name_of_recipe);
        recipeRating=findViewById(R.id.recipe_rating);
        imageLink=findViewById(R.id.image_of_recipe);
        webLink=findViewById(R.id.weblink_of_recipe);
        youtubeLink=findViewById(R.id.youtubelink_of_recipe);
        cookingInstructions=findViewById(R.id.cooking_instructions_of_recipe);
        theIngredient=findViewById(R.id.ingredients_of_recipe);
        addRecipe=findViewById(R.id.add_recipe);
        addIngredient=findViewById(R.id.add_ingredient);
        toIamges=findViewById(R.id.camera_button);
        toWeb=findViewById(R.id.web_button);
        toYoutube=findViewById(R.id.youtube_button);
        ingredinetsList=findViewById(R.id.list_of_ingredients);
        ingredientsArrayList=new ArrayList<>();
        adapter=new ArrayAdapter<>(AddRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
        ingredinetsList.setAdapter(adapter);

        //------------------------------------------------------------------------------

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsArrayList.add(theIngredient.getText().toString());
                adapter=new ArrayAdapter<>(AddRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
                ingredinetsList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                theIngredient.setText(null);

            }
        });

        toIamges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=recipeName.getText().toString();
                String picture=imageLink.getText().toString();
                String web=webLink.getText().toString();
                String youtube=youtubeLink.getText().toString();
                String instructions=cookingInstructions.getText().toString();
                int rating=(int)recipeRating.getRating();
                StringBuilder csvBuilder = new StringBuilder();
                for(String ingredient : ingredientsArrayList){
                    csvBuilder.append(ingredient);
                    csvBuilder.append(SEPARATOR);
                }
                String csv = csvBuilder.toString();
                csv = csv.substring(0, csv.length() - SEPARATOR.length());
                String ingredients=csv;
                System.out.println(ingredients);
                Recipe r = new Recipe(name,picture,youtube,web,instructions,ingredients,rating,1);
                Intent giveRecipe = new Intent();
                giveRecipe.putExtra("recipe",r);
                setResult(RESULT_OK,giveRecipe);
                finish();




            }
        });



    }
}
