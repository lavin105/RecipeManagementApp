package lavin105.recipemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    SearchView search;
    GridView grid;
    GridAdapter adapter;
    ArrayList<Recipe> recipeList;
    ArrayList<Recipe> favoritesList;
    FloatingActionButton toHome;
    ArrayList<Recipe> filteredRecipeList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_favorites);
        getSupportActionBar().setTitle("Your Favorites");

        grid=findViewById(R.id.recipe_grid);
        toHome=findViewById(R.id.to_home);
        recipeList=new ArrayList<Recipe>();
        favoritesList=new ArrayList<Recipe>();
        Intent fromHome=getIntent();
        favoritesList=(ArrayList<Recipe>)fromHome.getSerializableExtra("favorites");
        recipeList=(ArrayList<Recipe>)fromHome.getSerializableExtra("all_recipes");
        adapter= new GridAdapter(Favorites  .this,favoritesList);
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

}
