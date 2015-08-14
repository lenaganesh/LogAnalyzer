package application;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LogFileContainer {
	static int webViewIndex=0;
	static WebView textArea;
	private  WebEngine webEngine;
	private long lineNumber;
	CheckBox scrollLock=new CheckBox("Scroll Lock");
	public LogFileContainer(){
		textArea=WebEngineCreator.textArea[webViewIndex];
		webEngine = textArea.getEngine();
		webViewIndex++;
	}
	public BorderPane getContainer(ConnectInfo connectInfo) {
	
		return localLogContainer(connectInfo);
	}

	private BorderPane localLogContainer(ConnectInfo connectInfo) {

		BorderPane pane = new BorderPane();
		LogFileReader logFileReader = new LogFileReader(this);
		System.out.println(connectInfo);
		System.out.println(logFileReader);
		System.out.println(this);
		pane.setCenter(textArea);
		webEngine.loadContent("<html><body>Loading....</body></html>");
		
		logFileReader.readFileContent(new File(connectInfo.getCommand()));
		logFileReader.startTailer();
		return pane;
	}

	private BorderPane remoteLogContainer() {
		BorderPane pane = new BorderPane();
		RemoteLogFileReader logFileReader = new RemoteLogFileReader();


		pane.setCenter(textArea);
		logFileReader.startRead(logFileReader);
		return pane;
	}


	
	public ScrollPane fileReadMode(){
		ScrollPane scrollPane=new ScrollPane();
		
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		final VBox sp1 = new VBox();
		sp1.getChildren().add(scrollLock);
		sp1.getChildren().add(new TextSeachContainer().getContainer());
		scrollPane.setContent(sp1);
		return scrollPane;
	}
	public  void updateString(String line) {

		try {
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					String pattern = "%08d : %s";
				
					String t = String.format(pattern, (++lineNumber), line);
					
					webEngine.executeScript("document.write('" + t + "<br>');");
					if( ! scrollLock.isSelected()){
						webEngine.executeScript("window.scrollTo(0, document.body.scrollHeight);");
					}
					
				};
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
