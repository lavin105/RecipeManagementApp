package lavin105.recipemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public boolean addRecipe(Recipe r){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, r.getName());
        contentValues.put(COL2, r.getImage_url());
        contentValues.put(COL3, r.getYoutube_url());
        contentValues.put(COL4, r.getWeb_url());
        contentValues.put(COL5, r.getInstructions());
        contentValues.put(COL6, r.getIngredients());
        contentValues.put(COL7, r.getRating());

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result==-1){
            return false;
        }else{
            return true;
        }


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
        String query ="DELETE FROM "+TABLE_NAME+" WHERE "+COL5+" = '"+r.getInstructions()+"'";
        db.execSQL(query);

    }

    public void editRecipe(Recipe r, String oldInstructions){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="UPDATE "+ TABLE_NAME + " SET "+ COL1 + " = '"+r.getName()+"' ,"+COL2+"= '"+r.getImage_url()+"' ,"+COL3+"= '"+r.getYoutube_url()+"' ,"+COL4+"= '"+r.getWeb_url()+"' ,"+COL5+"= '"+r.getInstructions()+"' ,"+COL6+"= '"+r.getIngredients()+"' ,"+COL7+"= "+r.getRating()+ " WHERE " + COL5 + " = '"+oldInstructions+"'";
        db.execSQL(query);

    }


}
