package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ToolBarContainer {
	ToolBar toolBar = new ToolBar(); // Creates our tool-bar to hold the
										// buttons.
	Button openFileBtn = new Button();
	Button aboutBtn = new Button();
	Button snapshotBtn = new Button();

	public ToolBar getContainer(final Stage primaryStage) {
		Image openImage = new Image(this.getClass().getResource("open_folder_add_256.png").toExternalForm(), 16, 16, false, false);
		openFileBtn.setGraphic(new ImageView(openImage));
		snapshotBtn.setGraphic(new ImageView(this.getClass().getResource("open_folder_add_256.png").toExternalForm()));

		Image aboutImage = new Image(this.getClass().getResource("about-icon.png").toExternalForm(), 16, 16, false, false);
		aboutBtn.setGraphic(new ImageView(aboutImage));
		toolBar.getItems().addAll(openFileBtn, aboutBtn);


		openFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage myDialog = new MyDialog(primaryStage);
				myDialog.sizeToScene();
				myDialog.show();
			}
		});

		aboutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage myDialog = new AboutDialog(primaryStage);
				myDialog.sizeToScene();
				myDialog.show();
			}
		});
		return toolBar;
	}
}
