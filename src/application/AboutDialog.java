package application;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutDialog extends Stage {

    public AboutDialog(Stage owner) {
        super();
        initOwner(owner);
        setTitle("About...");
        initModality(Modality.APPLICATION_MODAL);
     
		Scene scene = new Scene(new AboutContainer().getContainer(this), 750, 450);
        setScene(scene);
    }
}