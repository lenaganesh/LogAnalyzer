package application;

import java.io.File;
import java.util.EventListener;

import javax.swing.text.Document;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LogFileContainer {
	public BorderPane getContainer() {
		BorderPane pane = new BorderPane();

		// textArea.setEditable(false);
		RemoteLogFileReader logFileReader = new RemoteLogFileReader();
		
		Main.scrollPane.setFitToHeight(true);
		Main.scrollPane.setFitToWidth(true);
		Main.scrollPane.setContent(Main.textArea);
		

		pane.setCenter(Main.scrollPane);
		//File file = new File("D:/LogFile.txt");
		logFileReader.readFileContent();
		logFileReader.startRead(logFileReader);
		return pane;
	}
	
}
