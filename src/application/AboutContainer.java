package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AboutContainer {
	
	BorderPane pane;
	
	Stage primaryStage;
	ListView<ConnectInfo> listView;

	public Pane getContainer(Stage primaryStage) {
		this.primaryStage = primaryStage;

		pane = new BorderPane();
		pane.setCenter(getRightRemotePane());
		
		pane.setBottom(getButtonPanel());

		return pane;
	}

	

	private Pane getRightRemotePane() {
		 BorderPane pane = new BorderPane();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		
		gridPane.add(new Label("Log File Analyzer"), 3, 2);
		Image aboutImage = new Image(this.getClass().getResource("king-julien.jpg").toExternalForm(),425,314,false,false);
		
		final ImageView imv = new ImageView();
		imv.setImage(aboutImage);
		
		pane.setTop(gridPane);
		pane.setCenter(imv);
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		Label Label=new Label("Log File Analyzer is R&D  implementation. Feel free to contact xxx@yahoo.com for any USE CASES "
				+ "and suggestions");
		flowPane.getChildren().add(Label);
		pane.setBottom(flowPane);

		return pane;
	}

	private Pane getButtonPanel() {
		
		Button closeButton = new Button("OK");
	
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				primaryStage.close();
			}
		});
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		GridPane gridPane = new GridPane();
		// GridPane.setMargin(gridPane, new Insets(100, 100, 100, 100));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));
		
		gridPane.add(closeButton, 6, 0);

		flowPane.getChildren().add(gridPane);
		return flowPane;
	}

	
}
