package application.test;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.*;
import javafx.stage.Stage;

public class WebViewSearch extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();
        engine.load("http://johannburkard.de/blog/programming/javascript/highlight-javascript-text-higlighting-jquery-plugin.html");

        final TextField searchField = new TextField("light");
        searchField.setPromptText("Enter the text you would like to highlight and press ENTER to highlight");
        searchField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (engine.getDocument() != null) {
                    highlight(
                            engine,
                            searchField.getText()
                    );
                }
            }
        });

        final Button highlightButton = new Button("Highlight");
        highlightButton.setDefaultButton(true);
        highlightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                searchField.fireEvent(new ActionEvent());
            }
        });
        final Button removeHighlightButton = new Button("Remove Highlight");
        removeHighlightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                removeHighlight(
                        engine
                );

            }
        });
        removeHighlightButton.setCancelButton(true);

        HBox controls = new HBox(10);
        controls.getChildren().setAll(
                highlightButton,
                removeHighlightButton
        );

        VBox layout = new VBox(10);
        layout.getChildren().setAll(searchField, controls, webView);
        searchField.setMinHeight(Control.USE_PREF_SIZE);
        controls.setMinHeight(Control.USE_PREF_SIZE);

        controls.disableProperty().bind(webView.getEngine().getLoadWorker().runningProperty());
        searchField.disableProperty().bind(webView.getEngine().getLoadWorker().runningProperty());

        primaryStage.setScene(new Scene(layout));
        primaryStage.show();

        webView.requestFocus();
    }

    private void highlight(WebEngine engine, String text) {
        engine.executeScript("$('body').removeHighlight().highlight('" + text + "')");
    }

    private void removeHighlight(WebEngine engine) {
        engine.executeScript("$('body').removeHighlight()");
    }

}