package lavin105.recipemanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recipe> r;
    private LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Recipe> r) {
        this.context=context;
        this.r=r;
    }

    @Override
    public int getCount() {
        return r.size();
    }

    @Override
    public Object getItem(int position) {
        return r.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview=convertView;
        if(convertView==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridview=inflater.inflate(R.layout.custom_recipe_grid,null);
        }

        ImageView picture = (ImageView) gridview.findViewById(R.id.icon);
        TextView recipe=(TextView) gridview.findViewById(R.id.recipe);
        RatingBar theRating=(RatingBar) gridview.findViewById(R.id.ratingBar);
        picture.setImageResource(R.mipmap.ic_launcher);
        recipe.setText(r.get(position).getName());
        theRating.setRating(r.get(position).getRating());

        gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,RecipeInformation.class);
                context.startActivity(i);
            }
        });



        return gridview;
    }

    public void filterList(ArrayList<Recipe> filteredList){

        r=filteredList;
        notifyDataSetChanged();
    }


}
