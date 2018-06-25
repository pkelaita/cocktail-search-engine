package iba;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores information about a single IBA cocktail.
 *
 * @author Pierce Kelaita
 * @since 6-24-2018
 */
@SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
public class IBAObject implements Comparable<IBAObject> {
    private String name;
    private String glass;
    private String category;
    private Map<String, Double> ingredients;
    private String garnish;
    private String preparation;
    private double abv;

    /**
     * Builder class accessed by GSON to populate data fields.
     */
    @SuppressWarnings("ConstantConditions")
    class IBABuilder {
        String name;
        String glass;
        String category;
        IngredientBuilder[] ingredients;
        String garnish;
        String preparation;

        /**
         * Accessed by GSON to populate ingredient information.
         */
        class IngredientBuilder {
            String unit;
            Double amount;
            String ingredient;
            String label;
            String special;
        }

        /**
         * Converts this builder to IBAObject
         *
         * @return IBAObject containing the deserialized data, with ingredients stored in
         * a HashMap. Also includes relative alcohol percentages.
         */
        IBAObject getIBAObject() {
            IBAObject result = new IBAObject();
            result.name = this.name;
            result.glass = this.glass; // name, glass are never null
            result.category = this.category != null ? this.category : "None";
            result.garnish = this.garnish != null ? this.garnish : "None";
            result.preparation = this.preparation != null ? this.preparation : "None";

            // populate ingredients and ABV
            double tQuant = 0, tABV = 0;
            result.ingredients = new HashMap<>();
            for (IngredientBuilder ib : this.ingredients) {

                // add special ingredients
                if (ib.amount == null)
                    ib.amount = 0.0;
                if (ib.ingredient == null)
                    ib.ingredient = ib.special;

                // calculate ABV
                tQuant += ib.amount;
                tABV += ABV.getABV(ib.ingredient) * ib.amount;

                // add all ingredients
                if (ib.label != null)
                    ib.ingredient = ib.label;
                result.ingredients.put(ib.ingredient, ib.amount);
            }
            result.abv = Math.round((tABV / tQuant) * 1e3) / 1e3;

            return result;
        }
    }

    /**
     * Sorts alphabetically
     *
     * @param other Other IBAObject
     * @return Result of comparison
     */
    @Override
    public int compareTo(IBAObject other) {
        return this.name.compareTo(other.name);
    }

    /**
     * @return String-formatted cocktail information
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(name + ":" +
                "\n\t" + "ABV: " + abv + " %" +
                "\n\t" + "Glass: " + glass +
                "\n\t" + "Category: " + category +
                "\n\t" + "Ingredients: ");
        for (String i : ingredients.keySet())
            out.append("\n\t\t")
                    .append(i).append(" : ")
                    .append(ingredients.get(i))
                    .append(" cl");
        out.append(new StringBuilder(
                "\n\t" + "Garnish: " + garnish +
                        "\n\t" + "Preparation: " + preparation
        ));
        return out.toString();
    }

    /// Getters ///

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getCategory() {
        return category;
    }

    public Map<String, Double> getIngredients() {
        return ingredients;
    }

    public String getGarnish() {
        return garnish;
    }

    public String getPreparation() {
        return preparation;
    }

    public double getAbv() {
        return abv;
    }
}
