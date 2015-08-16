package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.util.Callback;

public class WatchContainer {

	BorderPane pane;
	WebEngine engine;
	List<String> watchTextList = new ArrayList<>();
	ObservableList<String> myObservableList;
	Button clearButton = new Button("Delete");
	public WatchContainer(WebEngine webEngine) {
		this.engine=webEngine;
	}

	public ScrollPane getContainer() {
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		pane = new BorderPane();
		pane.setCenter(getRightRemotePane());
		pane.setRight(getListPane());
		flowPane.getChildren().addAll(pane);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(flowPane);
		return scrollPane;
	}

	private Pane getRightRemotePane() {
		BorderPane pane = new BorderPane();

		GridPane gridPane = new GridPane();

		gridPane.setPadding(new Insets(5, 5, 5, 5));

		gridPane.add(new Label("Watch What:"), 3, 2);
		final TextField searcTextField=new TextField("");
		gridPane.add(searcTextField, 4, 2);

		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER_LEFT);
		Button searchButton = new Button("Add");
		
		Button saveButton = new Button("Save");
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				myObservableList.add(searcTextField.getText());
			}
		});
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				new Serializer().storeWatchList(watchTextList);

			}
		});

		flowPane.setHgap(10);
		flowPane.setVgap(10);
		flowPane.setPadding(new Insets(5, 5, 5, 5));
		flowPane.getChildren().addAll(searchButton, clearButton,saveButton);
		pane.setCenter(gridPane);
		pane.setBottom(flowPane);

		FlowPane flowPane1 = new FlowPane();
		flowPane1.setAlignment(Pos.TOP_LEFT);
		flowPane1.getChildren().add(pane);
		return flowPane1;
	}
	private Pane getListPane(){
		BorderPane pane =new BorderPane();
		watchTextList = new Serializer().readWatchList();
		if (watchTextList == null) {
			watchTextList = new ArrayList<>();
		}
		myObservableList = FXCollections.observableList(watchTextList);
		final ListView<String> listView = new ListView<>();
		listView.setItems(myObservableList);
		listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> p) {
				System.out.println("Call:"+p.getSelectionModel());
				ListCell<String> cell = new ListCell<String>() {

					@Override
					protected void updateItem(String t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t);
						}else
						{
							setText("");
						}
					}

				};

				return cell;
			}
		});
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (listView.getSelectionModel() != null) {
					final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
					if (selectedIdx != -1) {
						listView.getSelectionModel().clearSelection();
						final int newSelectedIdx = (selectedIdx == listView.getItems().size() - 1) ? selectedIdx - 1
								: selectedIdx;

						listView.getItems().remove(selectedIdx);

						listView.getSelectionModel().select(newSelectedIdx);
						//new Serializer().storeConnectionList(ConnectInfoList);
					}
					
				}
			}
		});
		pane.setCenter(listView);
		return pane;
	}
	
}
