package application;

import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

public class TextSeachContainer {

	public Node getContainer() {
		final Accordion accordion = new Accordion();
		TitledPane t1 = new TitledPane("Search", new Button("B1"));
	    TitledPane t2 = new TitledPane("Monitor Text", new Button("B2"));
	    TitledPane t3 = new TitledPane("T3", new Button("B3"));
		
	    accordion.getPanes().addAll(t1, t2, t3);
		accordion.setExpandedPane(t1);
		return accordion;
	}

}
