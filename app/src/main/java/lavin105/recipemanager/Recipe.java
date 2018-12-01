package lavin105.recipemanager;

import java.io.Serializable;

public class Recipe implements Serializable {
    String name;
    String image_url;
    String youtube_url;
    String web_url;
    String instructions;
    String ingredients;
    int rating;


    public Recipe(String name, String image_url, String youtube_url, String web_url, String instructions, String ingredients, int rating) {
        this.name = name;
        this.image_url = image_url;
        this.youtube_url = youtube_url;
        this.web_url=web_url;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.rating=rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getYoutube_url() {
        return youtube_url;
    }

    public void setYoutube_url(String youtube_url) {
        this.youtube_url = youtube_url;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
