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
 * Connects to RecipePuppy API and standardizes information in Recipe object
 *
 * @author Pierce Kelaita
 * @since 6-23-2018
 */
public class Access {

    /**
     * Testing method
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            Recipe[] arr = new Access().getResults();
            for (Recipe r : arr)
                System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for recipes matching given parameters
     *
     * @return Results of a search
     * @throws IOException Typically due to malformed JSON
     */
    private Recipe[] getResults() throws IOException {

        Gson g = new Gson();

        // connect to RecipePuppy API
        URL url = new URL("http://www.recipepuppy.com/api");
        URLConnection request = url.openConnection();
        request.connect();

        // parse results
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(
                new InputStreamReader((InputStream) request.getContent()));
        JsonArray results = root.getAsJsonObject().getAsJsonArray("results");

        // standardize JSON objects
        Recipe[] result = new Recipe[results.size()];
        for (int i = 0; i < results.size(); i++) {
            Recipe.RecipeBuilder rb = g.fromJson(
                    results.get(i).toString(),
                    Recipe.RecipeBuilder.class
            );
            result[i] = rb.getRecipe();
        }
        return result;
    }
}
