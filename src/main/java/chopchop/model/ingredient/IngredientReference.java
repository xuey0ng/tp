package chopchop.model.ingredient;

import static java.util.Objects.requireNonNull;

import chopchop.model.Item;
import chopchop.model.attributes.Quantity;
import chopchop.util.Result;

/**
 * A reference to an ingredient. Not the actual {@code Ingredient}, and knows nothing about it.
 */
public class IngredientReference extends Item {
    private final Quantity quantity;

    /**
     * Constructs an ingredient reference with the given name and quantity.
     */
    public IngredientReference(String name, Quantity quantity) {
        super(name);
        requireNonNull(quantity);
        this.quantity = quantity;
    }

    /**
     * Parse an IngredientReference.
     * TODO: Why is this here?
     *
     * @param source String input.
     * @return the IngredientReference or an error message.
     */
    public static Result<IngredientReference> parse(String source) {
        String[] words = source.split(" \\(|\\)");
        if (words.length != 2) {
            return Result.error("Unable to parse string: %s", source);
        }
        Result<Quantity> qtyResult = Quantity.parse(words[1]);
        if (qtyResult.isError()) {
            return Result.error(qtyResult.getError());
        }
        return Quantity.parse(words[1]).map(qty -> new IngredientReference(words[0], qty));
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.name, this.quantity);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
            || (obj instanceof IngredientReference
                && ((IngredientReference) obj).name.equals(this.name)
                && ((IngredientReference) obj).quantity.equals(this.quantity)
            );
    }
}
