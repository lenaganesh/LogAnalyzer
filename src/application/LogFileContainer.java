package application;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LogFileContainer implements ILogContentUpdate{
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
		if(connectInfo.getEnvironment().equalsIgnoreCase("LOCAL")){
			return localLogContainer(connectInfo);
		}else{
			return remoteLogContainer(connectInfo);
		}
		
	}

	private BorderPane localLogContainer(ConnectInfo connectInfo) {

		BorderPane pane = new BorderPane();
		LogFileReader logFileReader = new LogFileReader(this);
		System.out.println(connectInfo);
		System.out.println(logFileReader);
		System.out.println(this);
		pane.setCenter(textArea);
		//String temp=this.getClass().getResource("LogContent.html").toExternalForm();
		String temp=this.getClass().getResource("LogContent.html").toExternalForm();//new File("LogContent.html").toURI().toString();
		System.out.println("Loading HTML:"+temp);
		webEngine.load(temp);
		
		//webEngine.loadContent("<html><body>Loading</body></html>");
		logFileReader.readFileContent(new File(connectInfo.getFilePath()));
		logFileReader.startTailer();
		return pane;
	}

	private BorderPane remoteLogContainer(ConnectInfo connectInfo) {
		BorderPane pane = new BorderPane();
		RemoteLogFileReader logFileReader = new RemoteLogFileReader(connectInfo,this);


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
		sp1.getChildren().add(new TextSeachContainer().getContainer(webEngine));
		scrollPane.setContent(sp1);
		return scrollPane;
	}
	private boolean gotData;
	ArrayList<String> failedList=new ArrayList<String>();
	@Override
	public  void updateString(final String line) {

		try {
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					String pattern = "%08d : %s";
				
					String t = String.format(pattern, (++lineNumber), line);
					if (!gotData) {
						
						try {
							webEngine.executeScript("$( \"body\" ).html(\"" + t + "<br>\");");
							gotData = true;
						} catch (Exception e) {
							e.printStackTrace();
							//failedList.add(t);
						}

					} else {
						try {
							webEngine.executeScript("$( \"body\" ).append(\"" + t + "<br>\");");
						} catch (Exception e) {
							e.printStackTrace();
							//failedList.add(t);
						}
					}
					
					//webEngine.executeScript("document.write('" + t + "<br>');");
					if( ! scrollLock.isSelected()){
						webEngine.executeScript("if(document != null && document.body != null && document.body.scrollHeight != null){window.scrollTo(0, document.body.scrollHeight);}");
					}
					
				};
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
