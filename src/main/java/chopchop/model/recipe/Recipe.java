package chopchop.model.recipe;

import static chopchop.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import chopchop.model.Entry;
import chopchop.model.attributes.Step;
import chopchop.model.attributes.Tag;
import chopchop.model.ingredient.IngredientReference;

public class Recipe extends Entry {
    private final List<IngredientReference> ingredients = new ArrayList<>();
    private final List<Step> steps = new ArrayList<>();
    private final HashSet<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Recipe(String name, List<IngredientReference> ingredients, List<Step> steps) {
        this(name, ingredients, steps, null);
    }

    /**
     * Every field must be present and not null.
     */
    public Recipe(String name, List<IngredientReference> ingredients, List<Step> steps, Set<Tag> tags) {
        super(name);
        requireAllNonNull(name, ingredients, steps);
        this.ingredients.addAll(ingredients);
        this.steps.addAll(steps);
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Returns an immutable ingredient list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<IngredientReference> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    /**
     * Returns an immutable step list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Step> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public String getTagList() {
        if (this.tags.isEmpty()) {
            return "No tags attached";
        }
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (var tag : this.tags) {
            sb.append(index)
                .append(" : ")
                .append(tag.getTagName())
                .append("\n");
            index++;
        }
        return sb.toString();
    }

    @Override
    public boolean isSame(Entry other) {
        return other == this
                || (other instanceof Recipe
                && this.name.equals(((Recipe) other).name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Recipe
                && this.name.equals(((Recipe) other).name)
                && this.ingredients.equals(((Recipe) other).ingredients)
                && this.steps.equals(((Recipe) other).steps))
                && this.tags.equals(((Recipe) other).tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients, steps, tags);
    }

    @Override
    public String toString() {
        StringJoiner recipeJoiner = new StringJoiner(" ");
        StringJoiner ingredientJoiner = new StringJoiner(", ");
        StringJoiner stepJoiner = new StringJoiner(", ");
        StringJoiner tagJoiner = new StringJoiner(", ");
        AtomicInteger counter = new AtomicInteger(1);

        this.getIngredients().forEach(ingredient -> ingredientJoiner.add(ingredient.toString()));
        this.getSteps().forEach(step -> stepJoiner.add(counter.getAndIncrement() + ". " + step.toString()));

        recipeJoiner.add(this.getName())
                .add("Ingredients:")
                .add(ingredientJoiner.toString())
                .add("Steps:")
                .add(stepJoiner.toString())
                .add("Tags:")
                .add(getTagList());

        return recipeJoiner.toString();
    }
}

