package iba;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ABV {

    public static void main (String[] args) {
        System.out.println(ABV.getABV("gin"));
    }

    public static Double getABV(String ing) {

        // TODO migrate to mongoDB

        try {
            FileReader reader = new FileReader(
                    "src/main/resources/ingredients_strength.json");
            JsonParser parser = new JsonParser();
            JsonObject jobj = (JsonObject) parser.parse(reader);
            return jobj.get(ing).getAsDouble();

        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            System.exit(1);
        } catch (NullPointerException npe) {
            return 0.0;
        }
        return null; // should never run
    }
}
