package iba;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class IBAObject {
    private String name;
    private String type;
    private Map<String, Double> ingredients;
    private double abv;

    @SuppressWarnings("ConstantConditions")
    class IBABuilder {
        String name;
        IngredientBuilder[] ingredients;
        String type;

        class IngredientBuilder {
            String name;
            Double quantity;
        }

        IBAObject getIBAObject() {
            IBAObject result = new IBAObject();
            result.name = this.name;
            result.type = this.type;

            // Calculate alcohol percentage
            double tQuant = 0, tABV = 0;
            result.ingredients = new HashMap<>();
            for (IngredientBuilder ib : this.ingredients) {
                if (ib.quantity == null) // don't add garnish quantities
                    ib.quantity = 0.0;
                result.ingredients.put(ib.name, ib.quantity);
                tQuant += ib.quantity;
                tABV += ABV.getABV(ib.name) * ib.quantity;
            }
            result.abv = Math.round((tABV / tQuant) * 1e4) / 1e2;

            return result;
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(name + ":" +
                "\n\t" + "Type: " + type +
                "\n\t" + "ABV: " + abv +
                "\n\t" + "Ingredients: ");
        for (String i : ingredients.keySet())
            out.append("\n\t\t").append(i).append(" : ").append(ingredients.get(i));
        return out.toString();
    }
}
