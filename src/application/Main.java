package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	//public static VBox textArea=new VBox();
	//public static Canvas textArea = new Canvas();
	//public static ScrollPane scrollPane = new ScrollPane();
	public static TabPane tabPane = new TabPane();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ConfigFileReader ConfigFileReader=new ConfigFileReader();
			ArrayList<ConnectInfo> connectInfoList=ConfigFileReader.getPropValues();
			if(connectInfoList !=  null){
				//Remote
				for (ConnectInfo connectInfo : connectInfoList) {
					
				}
			}else{
				//Local
				System.out.println("Local");
				connectInfoList=new ArrayList<ConnectInfo>();
				ConnectInfo ConnectInfo=new ConnectInfo();
				 
				ConnectInfo.setCommand("C:/LogFile.txt");
				connectInfoList.add(ConnectInfo);
				
				ConnectInfo ConnectInfo1=new ConnectInfo();
				ConnectInfo1.setCommand("C:/LogFile.txt");
				connectInfoList.add(ConnectInfo1);
				ConnectInfo ConnectInfo2=new ConnectInfo();
				 
				ConnectInfo2.setCommand("C:/LogFile.txt");
				connectInfoList.add(ConnectInfo2);
				
			}
			
			
			BorderPane root = new BorderPane();
			
			
			
			//root.setBottom(new Label("SDFSDFSDF"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.setCenter(new TabCreator().getContainer(connectInfoList));
			
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			//root.setCenter(new Container().initializeGroup());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	




	
	

	public static void main(String[] args) {
		launch(args);
	}

	
	
}
