package chopchop.ui;

import chopchop.model.recipe.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.awt.event.ActionEvent;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends UiPart<Region> {

    private static final String FXML = "RecipeCard.fxml";

    public final Recipe recipe;

    @FXML
    private Button cardPane;

    @FXML
    private Label name;

    /**
     * Creates a {@code RecipeCard} with the given {@code Recipe}.
     */
    public RecipeCard(Recipe recipe) {
        super(FXML);
        this.recipe = recipe;
        name.setText(recipe.getName().toString());
    }

    @FXML
    public void handleSelectRecipe(ActionEvent event) {

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }
        // state check
        RecipeCard card = (RecipeCard) other;
        return recipe.equals(card.recipe);
    }
}