package application;

import javafx.scene.web.WebView;

public class WebEngineCreator {
	public static WebView[] textArea;
	public static void create(int size) {
		size =size+1;
		textArea= new WebView[size];
		for (int i = 0; i < size; i++) {
			textArea[i] = new WebView();
		}
	}

}
