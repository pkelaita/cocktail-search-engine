package iba;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Access {

    private List<IBAObject> cocktails;

    public static void main(String[] args) throws FileNotFoundException {
        Access a = new Access();
        for (IBAObject i : a.cocktails)
            System.out.println(i + "\n");
    }

    // TODO migrate to MongoDB

    private Access() throws FileNotFoundException {
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new FileReader("src/main/resources/recipes.json"));

        Gson g = new Gson();
        IBAObject.IBAObjectBuilder[] ibaArr = g.fromJson(
                root, IBAObject.IBAObjectBuilder[].class
        );
        cocktails = new ArrayList<>();
        for (IBAObject.IBAObjectBuilder iob : ibaArr) {
            cocktails.add(iob.getIBAObject());
        }
    }
}
