// ClickableLink.java

package chopchop.ui;

import chopchop.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class ClickableLink extends Hyperlink {

    private final String url;

    /**
     * Constructs a new Clickable link; the label has the text, but clicking it takes you to
     * the url.
     */
    public ClickableLink(String text, String url) {
        super(text);

        this.url = url;

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                MainApp.the().getHostServices().showDocument(ClickableLink.this.url);
            }
        });
    }
}
