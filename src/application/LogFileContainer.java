package application;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LogFileContainer implements ILogContentUpdate {
	static int webViewIndex = 0;
	static WebView textArea;
	private WebEngine webEngine;
	private long lineNumber;
	CheckBox scrollLock = new CheckBox("Scroll Lock");
	private boolean engineLoaded;

	public LogFileContainer() {
		textArea = WebEngineCreator.textArea[webViewIndex];
		webEngine = textArea.getEngine();
		webViewIndex++;
	}

	public BorderPane getContainer(ConnectInfo connectInfo) {
		if (connectInfo.getEnvironment().equalsIgnoreCase("LOCAL")) {
			return localLogContainer(connectInfo);
		} else {
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
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue ov, State oldState, State newState) {

				if (newState == Worker.State.SUCCEEDED) {
					engineLoaded = true;
					updateString(null);
				}

			}
		});
		// String
		// temp=this.getClass().getResource("LogContent.html").toExternalForm();
		String temp = this.getClass().getResource("LogContent.html").toExternalForm();// new
																						// File("LogContent.html").toURI().toString();
		System.out.println("Loading HTML:" + temp);
		webEngine.load(temp);

		// webEngine.loadContent("<html><body>Loading</body></html>");
		logFileReader.readFileContent(new File(connectInfo.getFilePath()));
		logFileReader.startTailer();
		return pane;
	}

	private BorderPane remoteLogContainer(ConnectInfo connectInfo) {
		BorderPane pane = new BorderPane();
		RemoteLogFileReader logFileReader = new RemoteLogFileReader(connectInfo, this);

		pane.setCenter(textArea);
		logFileReader.startRead(logFileReader);
		return pane;
	}

	public ScrollPane fileReadMode() {
		ScrollPane scrollPane = new ScrollPane();

		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		final VBox sp1 = new VBox();
		sp1.getChildren().add(scrollLock);
		sp1.getChildren().add(new TextSeachContainer().getContainer(webEngine));
		scrollPane.setContent(sp1);
		return scrollPane;
	}

	private boolean gotData;
	ArrayList<String> failedList = new ArrayList<String>();
	ObservableList<String> myObservableList = FXCollections.observableList(failedList);;

	@Override
	public void updateString(final String line) {

		try {
			System.out.println("Updating...");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (line != null) {
						String pattern = "%08d : %s";
						String t = String.format(pattern, (++lineNumber), line);
						synchronized (myObservableList) {
							myObservableList.add(t);
						}

					}

					if (!engineLoaded) {
						return;
					}
					String executeQuery = "";
					synchronized (myObservableList) {
						for (String string : myObservableList) {
							if (!gotData) {
								executeQuery = "$( \"body\" ).html(\"" + string + "<br>\");";
								gotData = true;
							} else {
								executeQuery = "$( \"body\" ).append(\"" + string + "<br>\");";
							}
						}
						myObservableList.clear();
					}
					webEngine.executeScript(executeQuery);

					if (!scrollLock.isSelected()) {
						webEngine.executeScript(
								"if(document != null && document.body != null && document.body.scrollHeight != null){window.scrollTo(0, document.body.scrollHeight);}");
					}

				};
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
