package application;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

class MyDialog extends Stage {

    public MyDialog(Stage owner) {
        super();
        initOwner(owner);
        setTitle("Connection Session");
        initModality(Modality.APPLICATION_MODAL);
     
		Scene scene = new Scene(new FileOpenContainer().getContainer(this), 600, 300);
        setScene(scene);
    }
}