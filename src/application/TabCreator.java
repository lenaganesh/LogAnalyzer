package application;

import java.util.ArrayList;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class TabCreator {
	public Pane getContainer(ArrayList<ConnectInfo> connectInfoList) {
		BorderPane mainPane = new BorderPane();
		System.out.println(connectInfoList.size());
		WebEngineCreator.create(connectInfoList.size());
		for (ConnectInfo connectInfo : connectInfoList) {
			Tab tabA = new Tab();
			tabA.setText(connectInfo.getCommand());
			Container container=new Container();;
			tabA.setContent(container.initializeGroup(connectInfo));
			Main.tabPane.getTabs().add(tabA);
			mainPane.setCenter(Main.tabPane);
		}
		
		
		return mainPane;
	}
}
