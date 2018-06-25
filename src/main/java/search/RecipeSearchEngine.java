package search;

import iba.IBADeserializer;
import iba.IBAObject;
import recipePuppy.RecipeDeserializer;
import recipePuppy.RecipeObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RecipeSearchEngine {

    private RecipeDeserializer recipeDeserializer;

    public RecipeSearchEngine() {
        recipeDeserializer = new RecipeDeserializer();
    }

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
        List<IBAObject> query = new IBADeserializer().getContents();
        RecipeSearchEngine rse = new RecipeSearchEngine();

        for (IBAObject q : query) {
            System.out.println("For: " + q.getName());
            System.out.println(rse.search(q) + "\n");
        }
    }
}
