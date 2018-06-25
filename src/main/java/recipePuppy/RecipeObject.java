package recipePuppy;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Stores information about a single recipe from recipePuppy API
 *
 * @author Pierce Kelaita
 * @since 6-23-2018
 */
public class RecipeObject {
    private String title;
    private String href;
    private String thumbnail;
    private List<String> ingredients;
    private boolean isEmpty;

    private RecipeObject() {
    } // prevent regular instantiation outside of RecipeBuilder class

    /**
     * Creates an empty RecipeObject. This constructor should only be used when a call to
     * RecipePuppy's API yeilds no results
     *
     * @param isEmpty Should always be {@code true}
     */
    public RecipeObject(boolean isEmpty) {
        this.isEmpty = isEmpty;

        // prevent NullPointerException
        title = "NULL";
        href = "NULL";
        thumbnail = "NULL";
        ingredients = new ArrayList<>();
    }

    /**
     * Builder class acessed by GSON to populate data fields.
     */
    @SuppressWarnings("unused")
    class RecipeBuilder {
        private String title;
        private String href;
        private String ingredients;
        private String thumbnail;

        /**
         * Converts this builder to RecipeOjbect.
         *
         * @return RecipeObject containing the deserialized data, with the ingredients
         * stored as a List rather than a String.
         */
        RecipeObject getRecipeObject() {
            RecipeObject result = new RecipeObject();
            result.title = title;
            result.href = href;
            result.thumbnail = thumbnail;
            result.ingredients = new ArrayList<>(
                    Arrays.asList(
                            ingredients.split(", +")
                    )
            );
            result.isEmpty = false;
            return result;
        }
    }

    /**
     * @return String-formatted recipe
     */
    @Override
    public String toString() {
        if (isEmpty)
            return "No results found!";
        return title + ":\n\t" +
                href + "\n\t" +
                thumbnail + "\n\t" +
                ingredients + "\n";
    }

    /// Getters ///

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
}
