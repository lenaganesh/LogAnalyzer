package application;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebEngine;

public class TextSeachContainer {

	public Node getContainer(WebEngine webEngine) {
		final Accordion accordion = new Accordion();
		TitledPane t1 = new TitledPane("Search", new SearchContainer(webEngine).getContainer());
	    TitledPane t2 = new TitledPane("Monitor Text", new Button("B2"));
	    TitledPane t3 = new TitledPane("T3", new Button("B3"));
		
	    accordion.getPanes().addAll(t1, t2, t3);
		accordion.setExpandedPane(t1);
		return accordion;
	}

}
