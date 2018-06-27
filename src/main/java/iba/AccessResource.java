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
class AccessResource {

    // TODO migrate to mongoDB

    /**
     * Queries <a href="../../resources/ingredients.json">resources/ingredients.json</a>
     * for the AccessResource of a single ingredient.
     *
     * @param ing Ingredient to query
     * @return AccessResource strenth of ingredient, or {@code 0.0} if not found in JSON document.
     */
    static double getABV(String ing) {
        try {
            return query().get(ing).getAsJsonObject().get("abv").getAsDouble();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            System.exit(1);
        } catch (NullPointerException npe) {
            return 0.0;
        }
        return 0.0;
    }

    static String getTaste(String ing) {
        try {
            return query().get(ing).getAsJsonObject().get("taste").getAsString();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            System.exit(1);
        } catch (NullPointerException | java.lang.UnsupportedOperationException npe) {
            return null;
        }
        return null;
    }

    /**
     * TODO javadoc
     * @return Parsed JSON file
     * @throws FileNotFoundException Should never throw
     */
    private static JsonObject query() throws FileNotFoundException {
        FileReader reader = new FileReader(
                "src/main/resources/ingredients.json");
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(reader);
    }
}
