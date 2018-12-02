package lavin105.recipemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        recipe.setText(r.get(position).getName());
        theRating.setRating(r.get(position).getRating());

        if(r.get(position).getImage_url().equals("")){
            picture.setImageResource(R.drawable.recipe_manager_logo);
        }else{

                Picasso.get()
                        .load(r.get(position).getImage_url())
                        .error(R.drawable.recipe_manager_logo)
                        .fit()
                        .into(picture, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(context,"Image link invalid, loaded default image...",Toast.LENGTH_SHORT).show();
                            }
                        });

        }




        return gridview;
    }

    public void filterList(ArrayList<Recipe> filteredList){

        r=filteredList;
        notifyDataSetChanged();
    }


}
