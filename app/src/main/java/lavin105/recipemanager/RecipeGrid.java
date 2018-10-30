package lavin105.recipemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeGrid extends AppCompatActivity{
    GridView grid;
    ArrayList<Recipe> recipeList;
    GridAdapter adapter;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_grid);
        grid=findViewById(R.id.recipe_grid);
        nav=findViewById(R.id.nav);


        Recipe r1 = new Recipe("test recipe1",1);
        Recipe r2= new Recipe("test recipe2",1);
        Recipe r3 = new Recipe("test recipe3",1);
        Recipe r4 = new Recipe("test recipe4",1);
        Recipe r5 = new Recipe("test recipe5",1);
        Recipe r6 = new Recipe("test recipe6",1);
        Recipe r7 = new Recipe("test recipe7",1);
        Recipe r8 = new Recipe("test recipe8",1);



        recipeList= new ArrayList<>();
        recipeList.add(r1);
        recipeList.add(r2);
        recipeList.add(r3);
        recipeList.add(r4);
        recipeList.add(r5);
        recipeList.add(r6);
        recipeList.add(r7);
        recipeList.add(r8);


        adapter= new GridAdapter(RecipeGrid.this,recipeList);
        grid.setAdapter(adapter);
        drawer=findViewById(R.id.drawerlayout);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.add_menu){
                    System.out.println("Clicked");
                }
                return false;
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView search = (SearchView) item.getActionView();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text){
        ArrayList<Recipe> filteredRecipeList=new ArrayList<>();

        for(Recipe r :recipeList){
            if(r.getName().toLowerCase().contains(text.toLowerCase())){
                filteredRecipeList.add(r);
            }

        }
        adapter.filterList(filteredRecipeList);
    }



}
