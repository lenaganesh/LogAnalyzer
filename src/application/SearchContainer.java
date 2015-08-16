package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;

public class SearchContainer {

	BorderPane pane;
	WebEngine engine;
	public SearchContainer(WebEngine webEngine) {
		this.engine=webEngine;
	}

	public ScrollPane getContainer() {
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		pane = new BorderPane();
		pane.setCenter(getRightRemotePane());
		flowPane.getChildren().addAll(pane);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(flowPane);
		return scrollPane;
	}

	private Pane getRightRemotePane() {
		BorderPane pane = new BorderPane();

		GridPane gridPane = new GridPane();

		gridPane.setPadding(new Insets(5, 5, 5, 5));

		gridPane.add(new Label("Find What:"), 3, 2);
		final TextField searcTextField=new TextField("");
		gridPane.add(searcTextField, 4, 2);

		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER_LEFT);
		Button searchButton = new Button("Search");
		Button clearButton = new Button("Clear");

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				highlight(searcTextField.getText());
			}
		});
		
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				removeHighlight();

			}
		});

		flowPane.setHgap(10);
		flowPane.setVgap(10);
		flowPane.setPadding(new Insets(5, 5, 5, 5));
		flowPane.getChildren().addAll(searchButton, clearButton);
		pane.setCenter(gridPane);
		pane.setBottom(flowPane);

		return pane;
	}

	private void highlight(String text) {
		engine.executeScript("$('body').removeHighlight().highlight('" + text + "')");
	}

	private void removeHighlight() {
		engine.executeScript("$('body').removeHighlight()");
	}
}
