package iba;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about all listed IBA cocktails.
 *
 * @author Pierce Kelaita
 * @see <a href="https://en.wikipedia.org/wiki/List_of_IBA_official_cocktails"> List of
 * IBA Official Cocktails </a>
 * @since 6-24-2018
 */
public class IBADeserializer {

    private List<IBAObject> cocktails;

    // TODO migrate to MongoDB

    /**
     * Deserializes the JSON file <a href="../../resources/recipes.json">
     * resources/recipes.json </a>
     *
     * @throws FileNotFoundException Should never be thrown
     */
    private IBADeserializer() throws FileNotFoundException {
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(
                new FileReader("src/main/resources/recipes.json")
        );

        Gson g = new Gson();
        IBAObject.IBABuilder[] ibaArr = g.fromJson(
                root, IBAObject.IBABuilder[].class
        );
        cocktails = new ArrayList<>();
        for (IBAObject.IBABuilder iob : ibaArr) {
            cocktails.add(iob.getIBAObject());
        }
    }

    /**
     * Testing method
     *
     * @param args Command-line arguments
     * @throws FileNotFoundException Should never be thrown
     */
    public static void main(String[] args) throws FileNotFoundException {
        IBADeserializer a = new IBADeserializer();
        for (IBAObject i : a.cocktails)
            System.out.println(i + "\n");
    }
}
