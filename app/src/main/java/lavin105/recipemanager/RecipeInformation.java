package lavin105.recipemanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeInformation extends AppCompatActivity {
    RadioGroup switchView2;
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
    int REQUEST_CODE_WEB=1;
    int REQUEST_CODE_VIDEO=2;
    FloatingActionButton convertMeasuremnt;
    Dialog customDialog;
    Button convert;
    EditText theAmount;
    Spinner unit1, unit2;
    ImageView closePopup;
    TextView theResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_information);
        recipeName=findViewById(R.id.recipe_name);
        recipeInstructions=findViewById(R.id.instruction_description);
        ingredientsList=findViewById(R.id.ingredients_list);
        theRating=findViewById(R.id.the_recipe_rating);
        theRating.setRating(2);
        recipePicture=findViewById(R.id.the_recipe_picture);
        toShare=findViewById(R.id.share);
        convertMeasuremnt=findViewById(R.id.convert);
        toWeb=findViewById(R.id.web);
        toYoutube=findViewById(R.id.youtube);
        switchView2=findViewById(R.id.toggle2);
        getSupportActionBar().setTitle("Recipe Information");
        switchView2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println(checkedId);
                if (checkedId== R.id.instructions2){
                    System.out.println(checkedId);
                    ingredientsList.setVisibility(View.INVISIBLE);
                    recipeInstructions.setVisibility(View.VISIBLE);
                }if(checkedId== R.id.ingredients2){
                    System.out.println(checkedId);
                    ingredientsList.setVisibility(View.VISIBLE);
                    recipeInstructions.setVisibility(View.INVISIBLE);

                }
            }
        });

        customDialog=new Dialog(RecipeInformation.this);


        convertMeasuremnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                customDialog.show();            }
        });





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
        System.out.println(ing);

        toYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecipeInformation.this,VideoComponent.class);
                if(theRecipe.getYoutube_url().equals("")){
                    Toast.makeText(RecipeInformation.this,"Your recipe is not linked to a YouTube video",Toast.LENGTH_SHORT).show();
                }else{
                i.putExtra("url",theRecipe.getYoutube_url());
                startActivityForResult(i,REQUEST_CODE_VIDEO);

                }

            }
        });
        toWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecipeInformation.this,WebComponent.class);
                if(theRecipe.getWeb_url().equals("")){
                    Toast.makeText(RecipeInformation.this,"Your recipe is not linked to a recipe page",Toast.LENGTH_SHORT).show();
                }else{
                    i.putExtra("url",theRecipe.getWeb_url());
                    startActivityForResult(i,REQUEST_CODE_WEB);
                }
            }
        });
        toShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent share = new Intent(Intent.ACTION_SEND);
             share.putExtra(Intent.EXTRA_SUBJECT,"Check out this recipe!");

             String message="Recipe Name:\n\n"+theRecipe.getName()+"\n\n"+"Cooking Instructions:"+"\n\n"+theRecipe.getInstructions()+"\n\n"+"Ingredients:\n\n";

             String[] breakup=theRecipe.getIngredients().split(",");
             for(int i=0;i<breakup.length;i++){
                 message+=breakup[i]+"\n";
             }
             message+="\n\n\n"+"Youtube Link:\n\n"+theRecipe.getYoutube_url()+"\n\n"+"Web Link:\n\n"+theRecipe.getWeb_url()+"\n\n\n\n\nThis email was provided to you by RecipeManager";

             share.putExtra(Intent.EXTRA_TEXT,message);
             share.setType("message/rfc822");
             startActivity(Intent.createChooser(share,"Please chose your preferred email client."));

            }
        });
    }
}
