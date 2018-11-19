package lavin105.recipemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuPopupHelper;
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
    int specialPosition;
    int REQUEST_CODE_ADD_RECIPE=1;
    int REQUEST_CODE_RECIPE_INFO=2;
    int REQUEST_CODE_EDIT_RECIPE=3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_grid);
        grid=findViewById(R.id.recipe_grid);
        nav=findViewById(R.id.nav);


        Recipe r1 = new Recipe("test recipe1","https://www.thewholesomedish.com/wp-content/uploads/2018/07/Best-Lasagna-550-500x375.jpg","www.test.com","www.","test1","test1,test1",1,1);
        Recipe r2= new Recipe("test recipe2","","www.test.com","www.","test2","test2,test2",2,1);
        Recipe r3 =new Recipe("test recipe3","test","www.test.com","www.","test3","test3,test3",3,1);
        Recipe r4 = new Recipe("test recipe4","test","www.test.com","www.","test4","test4,test4",2,1);
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
                    Intent toAdd=new Intent(RecipeGrid.this, AddRecipe.class);
                    startActivityForResult(toAdd,REQUEST_CODE_ADD_RECIPE);
                }
                if(id==R.id.favorites_menu){
                    System.out.println("Favorites");
                }
                if(id==R.id.logout_menu){
                    System.out.println("Logout");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD_RECIPE){
            if (resultCode==RESULT_OK){
                Recipe addedRecipe=(Recipe)data.getSerializableExtra("recipe");
                recipeList.add(addedRecipe);
                adapter= new GridAdapter(RecipeGrid.this,recipeList);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }
        if (requestCode==REQUEST_CODE_EDIT_RECIPE){
            if(resultCode==RESULT_OK){
                System.out.println("edited");
                Recipe editedRecipe=(Recipe)data.getSerializableExtra("recipe");
                String idx=data.getStringExtra("index");
                recipeList.set(Integer.parseInt(idx),editedRecipe);
                adapter= new GridAdapter(RecipeGrid.this,recipeList);
                grid.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }

    }
}
