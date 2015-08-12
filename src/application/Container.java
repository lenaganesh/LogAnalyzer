package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Container {
	public SplitPane initializeGroup() {
		SplitPane sp = new SplitPane();
		final StackPane sp1 = new StackPane();
		sp1.getChildren().add(new Button("Lef To Choose Log Files"));
		final StackPane sp2 = new StackPane();
		sp2.getChildren().add(new LogFileContainer().getContainer());
		
		sp.getItems().addAll(sp1, sp2);
		sp.setStyle("-fx-background-color: #0099CC");
		sp2.setStyle("-fx-background-color: #0099CC");
		//sp2.setBackground(new Background(new BackgroundFill(Color.web("#0099CC"), CornerRadii.EMPTY, Insets.EMPTY)));
		sp.setDividerPositions(0.3f, 0.6f, 0.9f);
		return sp;
	}
}
