package application;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Container {
	public SplitPane initializeGroup(ConnectInfo connectInfo) {
		LogFileContainer logFileContainer=new LogFileContainer();
		SplitPane sp = new SplitPane();
		sp.setOrientation(Orientation.VERTICAL);
		final StackPane sp1 = new StackPane();
		sp1.getChildren().add(logFileContainer.fileReadMode());
		BorderPane borderPane=new BorderPane();
		
		borderPane.setCenter(logFileContainer.getContainer(connectInfo));
		
		
		sp.getItems().addAll( borderPane,sp1);
		
		//sp2.setBackground(new Background(new BackgroundFill(Color.web("#0099CC"), CornerRadii.EMPTY, Insets.EMPTY)));
		//sp.setDividerPositions(0.3f, 0.6f, 0.9f);
		return sp;
	}
	
}
