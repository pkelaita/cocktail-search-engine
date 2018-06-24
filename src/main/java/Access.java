import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.lang.reflect.Type;
import java.util.List;


public class Access {

    public static void main(String[] args) {
        try {
            Recipe[] arr = new Access().getResults();
            for (Recipe r : arr)
                System.out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Recipe[] getResults() throws IOException {

        Gson g = new Gson();

        URL url = new URL("http://www.recipepuppy.com/api");
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(
                new InputStreamReader((InputStream) request.getContent()));
        JsonArray results = root.getAsJsonObject().getAsJsonArray("results");

        Recipe[] result = new Recipe[results.size()];
        for (int i = 0; i < results.size(); i++) {
            Recipe.RecipeBuilder rb = g.fromJson(
                    results.get(i).toString(),
                    Recipe.RecipeBuilder.class
            );
            System.out.println(rb.getRecipe());
            result[i] = rb.getRecipe();
        }
        return result;
    }
}
