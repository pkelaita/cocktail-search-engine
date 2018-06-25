package search;

import iba.IBADeserializer;
import iba.IBAObject;
import recipePuppy.RecipeDeserializer;
import recipePuppy.RecipeObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Connects the user with a suggested alternate recipes based on the RecipePuppy API for
 * many of the IBA cocktails.
 *
 * @author Pierce Kelaita
 * @see <a href="http://www.recipepuppy.com/about/api/">RecipePuppy API</a>
 * @since 6-24-2018
 */
public class RecipeSearchEngine {

    private RecipeDeserializer recipeDeserializer;

    /**
     * Initializes RecipeSearchEngine with a recipeDeserializer
     */
    public RecipeSearchEngine() {
        recipeDeserializer = new RecipeDeserializer();
    }

    /**
     * Finds the most relevant result for a given IBA cocktail
     *
     * @param iba Contains data about an IBA cocktail
     * @return The most relevant result, or an empty RecipeObject if no results were
     * found.
     */
    public RecipeObject search(IBAObject iba) {

        Set<String> keys = iba.getIngredients().keySet();
        String[] ingredients = keys.toArray(new String[keys.size()]);
        String query = iba.getName();
        RecipeObject[] results;

        try {
            results = recipeDeserializer.getResults(ingredients, query);
        } catch (IOException e) {
            results = new RecipeObject[0];
        }

        return results.length > 0 ? results[0] : new RecipeObject(true);
    }

    /**
     * Testing method
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Set<IBAObject> query = new IBADeserializer().getContents();
        RecipeSearchEngine rse = new RecipeSearchEngine();

        for (IBAObject q : query) {
            System.out.println("For: " + q.getName());
            System.out.println(rse.search(q) + "\n");
        }
    }
}
