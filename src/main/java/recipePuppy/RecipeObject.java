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

    /**
     * Inner class accessed by GSON in order to deserialize JSON data into Java objects.
     * Takes in all fields as Strings.
     */
    @SuppressWarnings("unused")
    class RecipeBuilder {
        private String title;
        private String href;
        private String ingredients;
        private String thumbnail;

        /**
         * @return A RecipeObject object containing the deserialized data, with the ingredients
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
            return result;
        }
    }

    /**
     * @return Deserialized recipe data formatted as a String
     */
    @Override
    public String toString() {
        return title + ":\n\t" +
                href + "\n\t" +
                thumbnail + "\n\t" +
                ingredients + "\n";
    }
}
