package lavin105.recipemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/*Favorites.java is an activity that displays the users favorite recipes. Favorites are 3 stars. Favorites can
* be edited and deleted from this activity as well as the user can search for them using the searchview in the appbar.*/

public class Favorites extends AppCompatActivity {
    SearchView search;
    GridView grid;
    GridAdapter adapter;
    ArrayList<Recipe> recipeList;
    ArrayList<Recipe> favoritesList;
    ArrayList<Recipe> filteredRecipeList;
    int REQUEST_CODE_RECIPE_INFO=1;
    RecipeDatabaseManager recipeDatabaseManager;
    Cursor recipeData;
    int REQUEST_CODE_EDIT_RECIPE=3;
    int specialPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_favorites);
        getSupportActionBar().setTitle("Your Favorites");
        recipeDatabaseManager=new RecipeDatabaseManager(Favorites.this);


        grid=findViewById(R.id.recipe_grid);
        recipeList=new ArrayList<Recipe>();
        favoritesList=new ArrayList<Recipe>();

        Intent fromHome=getIntent();
        recipeData=recipeDatabaseManager.getFavoritesList();
        if (recipeData.getCount()==0){
            adapter= new GridAdapter(Favorites.this,recipeList);
            grid.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(Favorites.this,"You do not have any favorites!",Toast.LENGTH_SHORT).show();
        }else{
            while (recipeData.moveToNext()){
                Recipe r= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                recipeList.add(r);
                adapter= new GridAdapter(Favorites.this,recipeList);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        }
        getSupportActionBar().setTitle("Favorites");



        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(search.getQuery().toString().equals("")){
                    Intent i = new Intent(Favorites.this,RecipeInformation.class);
                    i.putExtra("recipe",recipeList.get(position));
                    startActivityForResult(i,REQUEST_CODE_RECIPE_INFO);

                }else{
                    Intent i = new Intent(Favorites.this,RecipeInformation.class);
                    i.putExtra("recipe",recipeList.get(position));
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
                                    Intent i = new Intent(Favorites.this,EditRecipe.class);
                                    i.putExtra("index",Integer.toString(position));
                                    i.putExtra("recipe",recipeList.get(position));
                                    startActivityForResult(i,REQUEST_CODE_EDIT_RECIPE);

                                }else{
                                    Intent i = new Intent(Favorites.this,EditRecipe.class);

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

                                AlertDialog.Builder alert2= new AlertDialog.Builder(Favorites.this);
                                alert2.setTitle("Delete this Recipe");
                                alert2.setMessage("Are you sure you want to delete this recipe?");
                                alert2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try{
                                            recipeDatabaseManager.deleteRecipe(recipe);
                                            Toast.makeText(Favorites.this,"Recipe Deleted...",Toast.LENGTH_SHORT).show();


                                        }catch(SQLException e){
                                            Toast.makeText(Favorites.this,"Unable to delete recipe...",Toast.LENGTH_SHORT).show();
                                        }                                        recipeData=recipeDatabaseManager.getFavoritesList();
                                        if (recipeData.getCount()==0){
                                            recipeList.clear();
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            recipeList.clear();
                                            while (recipeData.moveToNext()){
                                                Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                                                recipeList.add(r2);
                                                adapter= new GridAdapter(Favorites.this,recipeList);
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
        return true;
    }

    private void filterList(String text){
        filteredRecipeList=new ArrayList<>();

        for(Recipe r :favoritesList){
            if(r.getName().toLowerCase().contains(text.toLowerCase())){
                filteredRecipeList.add(r);
            }

        }
        adapter.filterList(filteredRecipeList);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent();
        setResult(RESULT_OK,i);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_EDIT_RECIPE){
            if(resultCode==RESULT_OK){
                recipeData = recipeDatabaseManager.getFavoritesList();
                if (recipeData.getCount() == 0) {
                    recipeList.clear();
                    adapter.notifyDataSetChanged();
                } else {
                    recipeList.clear();
                    while (recipeData.moveToNext()) {
                        Recipe r2= new Recipe(recipeData.getString(1),recipeData.getString(2),recipeData.getString(3),recipeData.getString(4),recipeData.getString(5),recipeData.getString(6),recipeData.getInt(7));
                        recipeList.add(r2);
                        adapter= new GridAdapter(Favorites.this,recipeList);
                        grid.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }

            }
        }

    }
}
