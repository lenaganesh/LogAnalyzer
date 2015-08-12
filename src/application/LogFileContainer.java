package application;



import java.io.File;

import javafx.scene.control.ScrollPane;
import javafx.scene.web.HTMLEditor;

public class LogFileContainer {
	public ScrollPane getContainer(){
		//BorderPane pane=new BorderPane();
		
		//textArea.setEditable(false);
		LogFileReader logFileReader=new LogFileReader();
		ScrollPane scrollPane=new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(Main.textArea);
		//Main.textArea.setEditable(false);
		
		
		//pane.setCenter(scrollPane);'
		File file = new File("C:/LogFile.txt");
		logFileReader.readFileContent(Main.textArea,file);
		logFileReader.startRead();
		return scrollPane;
	}

}
