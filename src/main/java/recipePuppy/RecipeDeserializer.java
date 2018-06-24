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
 * Connects to recipePuppy API and standardizes information in RecipeObject object
 *
 * @author Pierce Kelaita
 * @since 6-23-2018
 */
public class RecipeDeserializer {

    /**
     * Testing method
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            RecipeObject[] arr = new RecipeDeserializer().getResults();
            for (RecipeObject r : arr)
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
    private RecipeObject[] getResults() throws IOException {

        Gson g = new Gson();

        // connect to recipePuppy API
        URL url = new URL(
                "http://www.recipepuppy.com/api"
        );
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
}
