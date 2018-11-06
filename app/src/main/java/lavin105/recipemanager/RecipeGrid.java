package lavin105.recipemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import java.util.ArrayList;

public class RecipeGrid extends AppCompatActivity{
    GridView grid;
    ArrayList<Recipe> recipeList;
    GridAdapter adapter;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    SearchView search;
    ArrayList<Recipe> filteredRecipeList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_grid);
        grid=findViewById(R.id.recipe_grid);
        nav=findViewById(R.id.nav);


        Recipe r1 = new Recipe("test recipe1","www.taco.com","www.test.com","www.","test1","test1,test1",1,1);
        Recipe r2= new Recipe("test recipe2","www.taco.com","www.test.com","www.","test2","test2,test2",2,1);
        Recipe r3 =new Recipe("test recipe3","www.taco.com","www.test.com","www.","test3","test3,test3",3,1);
        Recipe r4 = new Recipe("test recipe4","www.taco.com","www.test.com","www.","test4","test4,test4",2,1);
        recipeList= new ArrayList<>();
        recipeList.add(r1);
        recipeList.add(r2);
        recipeList.add(r3);
        recipeList.add(r4);



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
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(search.getQuery().toString().equals("")){
                    Intent i = new Intent(RecipeGrid.this,RecipeInformation.class);
                    i.putExtra("recipe",recipeList.get(position));
                    startActivity(i);

                }else{
                    Intent i = new Intent(RecipeGrid.this,RecipeInformation.class);
                    i.putExtra("recipe",filteredRecipeList.get(position));
                    startActivity(i);
                }


            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu menu = new PopupMenu(getApplicationContext(),view);
                menu.getMenuInflater().inflate(R.menu.details_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit:
                                System.out.println("edit");
                                break;
                            case R.id.menu_delete:
                                System.out.println("delete");
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



}
