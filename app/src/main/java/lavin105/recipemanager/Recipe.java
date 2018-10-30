package lavin105.recipemanager;

import java.io.Serializable;

public class Recipe implements Serializable {
    String name;
    int icons ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcons() {
        return icons;
    }

    public void setIcons(int icons) {
        this.icons = icons;
    }

    public Recipe(String name, int icons) {
        this.name=name;
        this.icons=icons;
    }
}
