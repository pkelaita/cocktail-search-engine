import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Recipe {
    private String title;
    private String href;
    private String thumbnail;
    private List<String> ingredients;

    public Recipe(String title, String href, String ingredients, String thumbnail) {
        this.title = title;
        this.href = href;
        this.thumbnail = thumbnail;
        this.ingredients = new ArrayList<>(
                Arrays.asList(
                        ingredients.split(" ,")
                )
        );
    }

    @Override
    public String toString() {
        return title + ":\n\t" +
                href + "\n\t" +
                thumbnail + "\n\t" +
                ingredients + "\n";
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    class RecipeBuilder {
        private String title;
        private String href;
        private String ingredients;
        private String thumbnail;

        public Recipe getRecipe(){
            return new Recipe(title, href, ingredients, thumbnail);
        }
    }
}
