package iba;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Finds alcohol percentages (ABV) of various ingredients, as defined by IBA.
 *
 * @author Pierce Kelaita
 * @since 6-24-2018
 */
class ABV {

    /**
     * Queries <a href="../../resources/ingredients.json">resources/ingredients.json</a>
     * for the ABV of a single ingredient.
     *
     * @param ing Ingredient to query
     * @return ABV strenth of ingredient, or {@code 0.0} if not found in JSON document.
     */
    static Double getABV(String ing) {

        // TODO migrate to mongoDB

        try {
            FileReader reader = new FileReader(
                    "src/main/resources/ingredients.json");
            JsonParser parser = new JsonParser();
            JsonObject jobj = (JsonObject) parser.parse(reader);

            return jobj.get(ing).getAsJsonObject().get("abv").getAsDouble();

        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            System.exit(1);
        } catch (NullPointerException npe) {
            return 0.0;
        }
        return null; // should never return this
    }
}
