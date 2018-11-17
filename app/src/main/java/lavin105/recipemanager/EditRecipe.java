package lavin105.recipemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class EditRecipe extends AppCompatActivity {
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
        setContentView(R.layout.edit_recipe);
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
        adapter=new ArrayAdapter<>(EditRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
        ingredinetsList.setAdapter(adapter);

        //------------------------------------------------------------------------------

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsArrayList.add(theIngredient.getText().toString());
                adapter=new ArrayAdapter<>(EditRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
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
        ingredinetsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu menu = new PopupMenu(getApplicationContext(),view);
                menu.getMenuInflater().inflate(R.menu.details_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                AlertDialog.Builder alert= new AlertDialog.Builder(EditRecipe.this);
                                alert.setTitle("Edit An Ingredient");
                                alert.setMessage("Please enter the new ingredient.");
                                final EditText alert1input=new EditText(EditRecipe.this);
                                alert.setView(alert1input);
                                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String newClassName=alert1input.getText().toString();
                                        ingredientsArrayList.set(position,newClassName);
                                        adapter=new ArrayAdapter<>(EditRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
                                        ingredinetsList.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                final AlertDialog theAlert=alert.create();
                                theAlert.show();
                                break;
                            case R.id.menu_delete:
                                AlertDialog.Builder alert2= new AlertDialog.Builder(EditRecipe.this);
                                alert2.setTitle("Delete An Ingredient");
                                alert2.setMessage("Are you sure you want to delete this ingredient?");
                                alert2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ingredientsArrayList.remove(position);
                                        adapter=new ArrayAdapter<>(EditRecipe.this,android.R.layout.simple_list_item_1,ingredientsArrayList);
                                        ingredinetsList.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                                alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                final AlertDialog theAlert2=alert2.create();
                                theAlert2.show();
                                break;
                        }
                        return false;
                    }
                });

                menu.show();
                return false;
            }
        });

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recipeName.getText().toString().equals("")){
                    recipeName.setError("A recipe name is needed");
                    Toast.makeText(EditRecipe.this,"Something must be missing check fields above", Toast.LENGTH_SHORT).show();
                }if(imageLink.getText().toString().equals("")){
                    imageLink.setError("Please find an image link");
                    Toast.makeText(EditRecipe.this,"Something must be missing check fields above", Toast.LENGTH_SHORT).show();
                }if(cookingInstructions.getText().toString().equals("")){
                    cookingInstructions.setError("Cooking instructions necessary");
                    Toast.makeText(EditRecipe.this,"Something must be missing check fields above", Toast.LENGTH_SHORT).show();
                }if(ingredientsArrayList.isEmpty()==true){
                    theIngredient.setError("Please add an ingredient");
                    Toast.makeText(EditRecipe.this,"Something must be missing check fields above", Toast.LENGTH_SHORT).show();
                }if(recipeName.getText().toString().length()>0&&imageLink.getText().length()>0&&cookingInstructions.getText().length()>0&&ingredientsArrayList.isEmpty()==false){
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


            }
        });



    }
}
