package application;

import org.apache.commons.io.input.TailerListenerAdapter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	public static HTMLEditor textArea = new HTMLEditor();
	public static ScrollPane scrollPane = new ScrollPane();
	public static long lineNuber;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			hideHTMLEditorToolbars2(textArea);
			textArea.setStyle("-fx-background-color: #0099AA");
			
			root.setBottom(new Label("SDFSDFSDF"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			

			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
			root.setCenter(new Container().initializeGroup());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void hideHTMLEditorToolbars(final HTMLEditor editor) {
		textArea.lookup(".top-toolbar").setManaged(false);
		textArea.lookup(".top-toolbar").setVisible(false);

		textArea.lookup(".bottom-toolbar").setManaged(false);
		textArea.lookup(".bottom-toolbar").setVisible(false);
	}


public static void hideHTMLEditorToolbars2(final HTMLEditor editor)
{
	 editor.setVisible(false);
	    Platform.runLater(new Runnable()
	    {
	        @Override
	        public void run()
	        {
	            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
	            for(Node node : nodes)
	            {
		            System.out.println("J:"+node);	
	                node.setVisible(false);
	                node.setManaged(false);
	            }
	            editor.setVisible(true);
	        }
	    });
}

	double height=500;
	public static void updateString(String line) {
		
		try {
			WebView webView = (WebView) textArea.lookup("WebView");
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					String pattern ="%08d : %s";
					String t=String.format(pattern, (++lineNuber),line);
					System.out.println(t);
					webView.getEngine().executeScript("document.write('" +t.trim() + "<br>');");
					System.out.println(webView.getMaxHeight());
					;
				};

			});
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	class MyListener extends TailerListenerAdapter {
		@Override
		public void handle(String line) {
			System.out.println("==================");
			System.out.println("Listener:" + line);
			try {
				/*
				 * WebView webView = (WebView)textArea.lookup("WebView");
				 * WebPage webPage = Accessor.getPageFor(webView.getEngine());
				 */
				updateString(line);

				// webPage.executeCommand("insertText", line);

			} catch (Exception e) {
			}

		}
	}
}
