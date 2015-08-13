package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Container {
	public SplitPane initializeGroup() {
		SplitPane sp = new SplitPane();
		final StackPane sp1 = new StackPane();
		sp1.getChildren().add(new Button("Lef To Choose Log Files"));
		BorderPane borderPane=new BorderPane();
		borderPane.setCenter(new LogFileContainer().getContainer());
		borderPane.setBottom(fileReadMode());
		
		sp.getItems().addAll(sp1, borderPane);
		sp.setStyle("-fx-background-color: #0099CC");
		borderPane.setStyle("-fx-background-color: #0099CC");
		//sp2.setBackground(new Background(new BackgroundFill(Color.web("#0099CC"), CornerRadii.EMPTY, Insets.EMPTY)));
		sp.setDividerPositions(0.3f, 0.6f, 0.9f);
		return sp;
	}
	public ScrollPane fileReadMode(){
		ScrollPane scrollPane=new ScrollPane();
		
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		final StackPane sp1 = new StackPane();
		sp1.getChildren().add(new Label("Reload Option"));
		scrollPane.setContent(sp1);
		return scrollPane;
	}
}
