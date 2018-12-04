package lavin105.recipemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*RecipeGrid.java is the home page for the application it has a hamburger menu where you can add, view favorits, set the cooking alarm/timer
* and convert measurements. Uses a gridview to display all the recipes as well as a searchview so the user can find the recipe they are
* looking for easily. This activity uses the database helper class to add, edit, delete, and query data. This activity is essentially linked to all others
* it is the hub of the application.*/

public class RecipeGrid extends AppCompatActivity{
    GridView grid;
    ArrayList<Recipe> recipeList;
    ArrayList<Recipe> favoritesList;
    GridAdapter adapter;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    SearchView search;
    ArrayList<Recipe> filteredRecipeList;
    Dialog customDialog;
    Button convert;
    EditText theAmount;
    Spinner unit1, unit2;
    ImageView closePopup;
    TextView theResult;
    int specialPosition;
    int REQUEST_CODE_ADD_RECIPE=1;
    int REQUEST_CODE_RECIPE_INFO=2;
    int REQUEST_CODE_EDIT_RECIPE=3;
    int REQUEST_CODE_FAVORITES=4;
    int REQUEST_CODE_TIMER=5;
    public int count=0;
    int tempInt = 0;
    String theAlarm;
    RecipeDatabaseManager recipeDatabaseManager;
    Cursor recipeData;
    FloatingActionButton addRecipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_grid);
        customDialog=new Dialog(RecipeGrid.this);
        recipeList= new ArrayList<>();
        favoritesList=new ArrayList<>();
        addRecipe=findViewById(R.id.add_recipe_button);
        count = readSharedPreferenceInt("cntSP","cntKey");
        if(count==0){
            Intent intent = new Intent();
            intent.setClass(RecipeGrid.this, GetStarted.class);
            startActivity(intent);
            count++;
            writeSharedPreference(count,"cntSP","cntKey");
        }

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAdd=new Intent(RecipeGrid.this, AddRecipe.class);
                startActivityForResult(toAdd,REQUEST_CODE_ADD_RECIPE);
            }
        });


        grid=findViewById(R.id.recipe_grid);
        nav=findViewById(R.id.nav);
        theAlarm="";
        recipeDatabaseManager=new RecipeDatabaseManager(RecipeGrid.this);
        recipeData=recipeDatabaseManager.getRecipeList();

        if (recipeData.getCount()==0){
            adapter= new GridAdapter(RecipeGrid.this,recipeList);
            grid.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            AlertDialog.Builder alert2= new AlertDialog.Builder(RecipeGrid.this);
            alert2.setTitle("Welcome to RecipeManager you currently have no recipes!");
            alert2.setMessage("Would you like to add one now?");
            alert2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent addIfEmpty=new Intent(RecipeGrid.this,AddRecipe.class);
                    startActivityForResult(addIfEmpty,REQUEST_CODE_ADD_RECIPE);
                }
            });
            alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            final AlertDialog theAlert2=alert2.create();
            theAlert2.show();
        }else{
            while (recipeData.moveToNext()){
                Recipe r= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                recipeList.add(r);
                adapter= new GridAdapter(RecipeGrid.this,recipeList);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        }
        drawer=findViewById(R.id.drawerlayout);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.add_menu){
                    Intent toAdd=new Intent(RecipeGrid.this, AddRecipe.class);
                    startActivityForResult(toAdd,REQUEST_CODE_ADD_RECIPE);
                }
                if(id==R.id.favorites_menu){
                    Intent toFavorites=new Intent(RecipeGrid.this,Favorites.class);

                    for(int i=0;i<recipeList.size();i++){
                        if (recipeList.get(i).getRating()==3){
                            favoritesList.add(recipeList.get(i));
                        }

                    }

                    toFavorites.putExtra("favorites",favoritesList);
                    toFavorites.putExtra("all_recipes",recipeList);
                    startActivityForResult(toFavorites,REQUEST_CODE_FAVORITES);
                }
                if(id==R.id.conversion){

                    customDialog.setContentView(R.layout.conversion_popup);
                    convert =customDialog.findViewById(R.id.convert_units);
                    theAmount=customDialog.findViewById(R.id.amount);
                    unit1=customDialog.findViewById(R.id.units_1);
                    unit2=customDialog.findViewById(R.id.units_2);
                    closePopup=customDialog.findViewById(R.id.close_popup);
                    theResult=customDialog.findViewById(R.id.result);

                    closePopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                        }
                    });


                    convert.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if(theAmount.getText().toString().equals("")){
                                theResult.setTextColor(Color.RED);
                                theResult.setText("You did not enter a value to convert...");

                            }else{
                                try {
                                    theResult.setTextColor(Color.BLACK);
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);

                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 0.125;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 16;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 2;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 6;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 29.574;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Ounces") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 8;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 16;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 48;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 236.588;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Cups") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 8;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 16;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 453.59;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("Pounds") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 15.34;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 2;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 16;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 3;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 14.787;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 2;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tbsp") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 6;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 6;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 48;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 3;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("tsp") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 4.929;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 29.574;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 236.588;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText("Not a valid conversion");
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 14.787;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 4.929;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("mL") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 29.574;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("Ounces") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);

                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("Cups") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 0.125;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("Pounds") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double / 16;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("tbsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 2;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("tsp") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 6;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("mL") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        amt_double = amt_double * 29.574;
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                    if (unit1.getSelectedItem().equals("fl oz") && unit2.getSelectedItem().equals("fl oz") && !theAmount.getText().toString().equals("0")
                                            ) {
                                        String amt = theAmount.getText().toString();
                                        Double amt_double = Double.parseDouble(amt);
                                        String res = Double.toString(amt_double);
                                        theResult.setText(res);
                                    }
                                }catch (NumberFormatException e){
                                    theResult.setTextColor(Color.RED);
                                    theResult.setText("You did not enter a number...");
                                }
                            }



                        }
                    });
                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.45);

                    customDialog.getWindow().setLayout(width, height);
                    customDialog.show();




                }



                if(id==R.id.cook_timer){
                Intent timer = new Intent(RecipeGrid.this,Timer.class);
                timer.putExtra("theAlarm",theAlarm);
                startActivityForResult(timer,REQUEST_CODE_TIMER);
                }
                if(id==R.id.logout_menu){
                    finish();
                }
                if(id==R.id.delete_account_menu){
                    System.out.println("Delete Account");

                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(search.getQuery().toString().equals("")){
                    Intent i = new Intent(RecipeGrid.this,RecipeInformation.class);
                    i.putExtra("recipe",recipeList.get(position));
                    startActivityForResult(i,REQUEST_CODE_RECIPE_INFO);

                }else{
                    Intent i = new Intent(RecipeGrid.this,RecipeInformation.class);
                    i.putExtra("recipe",filteredRecipeList.get(position));
                    startActivityForResult(i,REQUEST_CODE_RECIPE_INFO);
                }


            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Recipe recipe=(Recipe)parent.getItemAtPosition(position);

                PopupMenu menu = new PopupMenu(getApplicationContext(),view);
                menu.getMenuInflater().inflate(R.menu.details_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                if(search.getQuery().toString().equals("")){
                                    Intent i = new Intent(RecipeGrid.this,EditRecipe.class);
                                    i.putExtra("index",Integer.toString(position));
                                    i.putExtra("recipe",recipeList.get(position));
                                    startActivityForResult(i,REQUEST_CODE_EDIT_RECIPE);

                                }else{
                                    Intent i = new Intent(RecipeGrid.this,EditRecipe.class);

                                    for(int x=0; x<recipeList.size();x++){
                                        for(int y=0;y<filteredRecipeList.size();y++){
                                            if (recipeList.get(x).getInstructions()==filteredRecipeList.get(y).getInstructions()){
                                                specialPosition=x;
                                            }
                                        }
                                    }

                                    i.putExtra("recipe",recipeList.get(specialPosition));
                                    i.putExtra("index",Integer.toString(specialPosition));
                                    startActivityForResult(i,REQUEST_CODE_EDIT_RECIPE);
                                }
                                break;
                            case R.id.menu_delete:

                                AlertDialog.Builder alert2= new AlertDialog.Builder(RecipeGrid.this);
                                alert2.setTitle("Delete this Recipe");
                                alert2.setMessage("Are you sure you want to delete this recipe?");
                                alert2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recipeDatabaseManager.deleteRecipe(recipe);
                                        recipeData=recipeDatabaseManager.getRecipeList();
                                        if (recipeData.getCount()==0){
                                            recipeList.clear();
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            recipeList.clear();
                                            while (recipeData.moveToNext()){
                                                Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                                                recipeList.add(r2);
                                                adapter= new GridAdapter(RecipeGrid.this,recipeList);
                                                grid.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }

                                        }
                                    }
                                });
                                alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        search = (SearchView) item.getActionView();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        toggle= new ActionBarDrawerToggle(RecipeGrid.this,drawer,R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }



    public int readSharedPreferenceInt(String spName,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(spName,Context.MODE_PRIVATE);
        return tempInt = sharedPreferences.getInt(key, 0);
    }

    public void writeSharedPreference(int ammount,String spName,String key ){

        SharedPreferences sharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(key, ammount);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text){
        filteredRecipeList=new ArrayList<>();

        for(Recipe r :recipeList){
            if(r.getName().toLowerCase().contains(text.toLowerCase())){
                filteredRecipeList.add(r);
            }

        }
        adapter.filterList(filteredRecipeList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD_RECIPE){
            if (resultCode==RESULT_OK){
                Recipe addedRecipe=(Recipe)data.getSerializableExtra("recipe");
                addData(addedRecipe);
                recipeData = recipeDatabaseManager.getRecipeList();
                if (recipeData.getCount() == 0) {
                    recipeList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    recipeList.clear();
                    while (recipeData.moveToNext()) {
                        Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                        recipeList.add(r2);
                        adapter= new GridAdapter(RecipeGrid.this,recipeList);
                        grid.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        }
        if (requestCode==REQUEST_CODE_EDIT_RECIPE){
            if(resultCode==RESULT_OK){
                recipeData = recipeDatabaseManager.getRecipeList();
                if (recipeData.getCount() == 0) {
                    recipeList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    recipeList.clear();
                    while (recipeData.moveToNext()) {
                        Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                        recipeList.add(r2);
                        adapter= new GridAdapter(RecipeGrid.this,recipeList);
                        grid.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }

            }
        }
        if(requestCode==REQUEST_CODE_FAVORITES){
            if (resultCode==RESULT_OK){
                recipeData = recipeDatabaseManager.getRecipeList();
                if (recipeData.getCount() == 0) {
                    recipeList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    recipeList.clear();
                    while (recipeData.moveToNext()) {
                        Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                        recipeList.add(r2);
                        adapter= new GridAdapter(RecipeGrid.this,recipeList);
                        grid.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        }
        if (requestCode==REQUEST_CODE_TIMER){
            if(resultCode==RESULT_OK){
                theAlarm = data.getStringExtra("alarm");
            }
        }

    }
    public void addData(Recipe r){
        boolean insertData=recipeDatabaseManager.addRecipe(r);
        if (insertData==true){
            Toast.makeText(RecipeGrid.this,"Recipe was successfully added!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(RecipeGrid.this,"Your recipe was not added, something must have gone wrong please try again",Toast.LENGTH_SHORT).show();

        }

    }
}
