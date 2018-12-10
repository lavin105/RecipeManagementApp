package lavin105.recipemanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/*RecipeDatabaseManager.java is the database helper class used to store recipes into an sqlite database.
* Database has 8 attributes id, name, image, youtube, web, instructions, ingredients, and rating.
* There are also methods to add, delete, edit, and query for data.*/

public class RecipeDatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="recipe.db";
    public static final String TABLE_NAME="recipes";
    public static final String COL0="ID";
    public static final String COL1="NAME";
    public static final String COL2="IMAGE";
    public static final String COL3="YOUTUBE";
    public static final String COL4="WEB";
    public static final String COL5="INSTRUCTIONS";
    public static final String COL6="INGREDIENTS";
    public static final String COL7="RATING";

    public RecipeDatabaseManager(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, IMAGE TEXT, YOUTUBE TEXT, WEB TEXT,INSTRUCTIONS TEXT,INGREDIENTS TEXT, RATING INT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public void addRecipe(Recipe r){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO recipes(NAME,IMAGE,YOUTUBE,WEB,INSTRUCTIONS,INGREDIENTS,RATING) VALUES(?,?,?,?,?,?,?)");
        stmt.bindString(1, r.getName());
        stmt.bindString(2, r.getImage_url());
        stmt.bindString(3, r.getYoutube_url());
        stmt.bindString(4, r.getWeb_url());
        stmt.bindString(5, r.getInstructions());
        stmt.bindString(6, r.getIngredients());
        stmt.bindDouble(7,Double.valueOf(r.getRating()));
        stmt.execute();


    }
    public Cursor getRecipeList(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor data =db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return data;
    }

    public Cursor getFavoritesList(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor data =db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL7+"=3",null);
        return data;
    }

    public void deleteRecipe(Recipe r){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("DELETE FROM recipes WHERE INSTRUCTIONS = ?");
        stmt.bindString(1, r.getInstructions());
        stmt.execute();
    }

    public void editRecipe(Recipe r, String oldInstructions){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("UPDATE recipes SET NAME=?,IMAGE=?,YOUTUBE=?,WEB=?,INSTRUCTIONS=?,INGREDIENTS=?,RATING=? WHERE INSTRUCTIONS=?");
        stmt.bindString(1, r.getName());
        stmt.bindString(2, r.getImage_url());
        stmt.bindString(3, r.getYoutube_url());
        stmt.bindString(4, r.getWeb_url());
        stmt.bindString(5, r.getInstructions());
        stmt.bindString(6, r.getIngredients());
        stmt.bindDouble(7,Double.valueOf(r.getRating()));
        stmt.bindString(8, oldInstructions);
        stmt.execute();

    }

    public void deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
