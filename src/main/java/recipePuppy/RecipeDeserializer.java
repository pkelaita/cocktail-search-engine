package recipePuppy;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Connects to recipePuppy API and accesses information about recipes.
 *
 * @author Pierce Kelaita
 * @since 6-23-2018
 */
public class RecipeDeserializer {

    /**
     * Searches for recipes matching given parameters
     *
     * @param ingredients Array containging a recipe's ingredients
     * @param query       Search parameter
     * @return Results of the search
     * @throws IOException Typically due to malformed JSON
     */
    public RecipeObject[] getResults(String[] ingredients, String query)
            throws IOException {

        Gson g = new Gson();

        // connect to recipePuppy API
        URL url = new URL(getURL(ingredients, query));
        URLConnection request = url.openConnection();
        request.connect();

        // parse results
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(
                new InputStreamReader((InputStream) request.getContent()));
        JsonArray results = root.getAsJsonObject().getAsJsonArray("results");

        // standardize JSON objects
        RecipeObject[] result = new RecipeObject[results.size()];
        for (int i = 0; i < results.size(); i++) {
            RecipeObject.RecipeBuilder rb = g.fromJson(
                    results.get(i).toString(),
                    RecipeObject.RecipeBuilder.class
            );
            result[i] = rb.getRecipeObject();
        }
        return result;
    }

    /**
     * Generates RecipePuppy API URL from given parameters
     *
     * @param ingredients Array containging a recipe's ingredients
     * @param query       Search parameter
     * @return Formatted URL
     */
    public String getURL(String[] ingredients, String query) {
        StringBuilder ingredientsQuery = new StringBuilder();
        for (String i : ingredients)
            ingredientsQuery.append(
                    i.toLowerCase().replace(" ", "_")
            ).append(",");
        return "http://www.recipepuppy.com/api?" +
              //  "i=" + ingredientsQuery.toString() + "&" +
                "q=" + query.toLowerCase().replace(" ", "_");
    }

    /**
     * Testing method
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        String q = "";
        String[] i = {"dry vermouth"};
        try {
            RecipeObject[] arr = new RecipeDeserializer().getResults(i, q);
            for (RecipeObject r : arr)
                System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
