package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FileOpenContainer {
	List<ConnectInfo> ConnectInfoList = new ArrayList<>();
	TextField sessionNameText = new TextField();
	TextField hostNameText = new TextField();
	TextField userNameText = new TextField();
	TextField passwordText = new TextField();
	TextField pathText = new TextField();
	TextField localPathText = new TextField();
	TextField localSessionNameText = new TextField();
	ObservableList<ConnectInfo> myObservableList;
	BorderPane pane;
	BorderPane outerPane=new BorderPane();
	Pane remoteContainer, localContainer;
	RadioButton localButton = new RadioButton("Local");
	RadioButton remoteButton = new RadioButton("Remote");
	final ToggleGroup group = new ToggleGroup();
	Stage primaryStage;
	ListView<ConnectInfo> listView;
	Label saveSatus=new Label("");
	public Pane getContainer(Stage primaryStage) {
		this.primaryStage = primaryStage;

		pane = new BorderPane();
		pane.setTop(getSystemPane());
		pane.setLeft(getLeftPane());
		getRightPane();
		pane.setBottom(getButtonPanel());

		return pane;
	}

	private Pane getLeftPane() {

		BorderPane pane = new BorderPane();
		pane.setTop(new Label("Stored Session"));
		ConnectInfoList = new Serializer().readConnectionList();
		if (ConnectInfoList == null) {
			ConnectInfoList = new ArrayList<>();
		}
		myObservableList = FXCollections.observableList(ConnectInfoList);
		
		listView = new ListView<>();
		listView.setItems(myObservableList);
		listView.setCellFactory(new Callback<ListView<ConnectInfo>, ListCell<ConnectInfo>>() {

			@Override
			public ListCell<ConnectInfo> call(ListView<ConnectInfo> p) {
				System.out.println("Call:"+p.getSelectionModel());
				ListCell<ConnectInfo> cell = new ListCell<ConnectInfo>() {

					@Override
					protected void updateItem(ConnectInfo t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText("["+t.getEnvironment().toUpperCase().substring(0,1)+"]"+t.getTitle());
						}else
						{
							setText("");
						}
					}

				};

				return cell;
			}
		});
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				System.out.println(newValue);
				loadObjectInfo((ConnectInfo) newValue);
			}

		});
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(listView);
		pane.setCenter(scrollPane);
		return pane;
	}

	private Pane getRightPane() {
		
		remoteContainer = getRightRemotePane();
		localContainer = getRightLocalPane();

		System.out.println("SSS:" + group.getSelectedToggle().getUserData());
		if (((String) group.getSelectedToggle().getUserData()).equalsIgnoreCase("LOCAL")) {
			outerPane.setCenter(localContainer);
		} else {
			outerPane.setCenter(remoteContainer);
		}
		
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		flowPane.getChildren().add(saveSatus);
		outerPane.setBottom(flowPane);
		pane.setCenter(outerPane);
		return pane;
	}

	private Pane getSystemPane() {
		GridPane gridPane = new GridPane();

		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		gridPane.add(localButton, 2, 0);
		gridPane.add(remoteButton, 3, 0);
		localButton.setUserData("LOCAL");
		remoteButton.setUserData("REMOTE");
		localButton.setToggleGroup(group);
		remoteButton.setToggleGroup(group);

		localButton.setSelected(true);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					resetFields();
					if (((String) group.getSelectedToggle().getUserData()).equalsIgnoreCase("LOCAL")) {

						outerPane.setCenter(localContainer);
					} else {

						outerPane.setCenter(remoteContainer);
					}
				}
			}
		});
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		flowPane.getChildren().addAll(gridPane);
		return flowPane;
	}

	private Pane getRightLocalPane() {

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		gridPane.add(new Label("Session Name"), 2, 2);
		gridPane.add(localSessionNameText, 3, 2);

		gridPane.add(new Label("File Path"), 2, 3);
		gridPane.add(localPathText, 3, 3);

		final Button openButton = new Button("Choose");

		final FileChooser fileChooser = new FileChooser();
		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					if (((String) group.getSelectedToggle().getUserData()).equalsIgnoreCase("LOCAL")) {
						localPathText.setText(file.getAbsolutePath());
					} else {
						pathText.setText(file.getAbsolutePath());
					}
					
				}
			}
		});

		gridPane.add(openButton, 4, 3);

		return gridPane;
	}

	private Pane getRightRemotePane() {
		// BorderPane pane = new BorderPane();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		gridPane.add(new Label("Session Name"), 2, 2);
		gridPane.add(sessionNameText, 3, 2);

		gridPane.add(new Label("Host Name/IP Address"), 2, 4);
		gridPane.add(hostNameText, 3, 4);

		gridPane.add(new Label("User Name"), 2, 5);
		gridPane.add(userNameText, 3, 5);

		gridPane.add(new Label("Password"), 2, 6);
		gridPane.add(passwordText, 3, 6);

		gridPane.add(new Label("Path of File"), 2, 7);
		gridPane.add(pathText, 3, 7);


		return gridPane;
	}

	private Pane getButtonPanel() {
		Button pingButton = new Button("Ping");

		// Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
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
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveConnectionInfo();
			}

		});
		Button connectButton = new Button("Connect");
		Button closeButton = new Button("Close");
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				loadFile();
			}

			
		});
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				primaryStage.close();
			}
		});
		FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		GridPane gridPane = new GridPane();
		// GridPane.setMargin(gridPane, new Insets(100, 100, 100, 100));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(5, 5, 5, 5));
		gridPane.add(pingButton, 1, 0);
		// gridPane.add(loadButton, 2, 0);

		gridPane.add(saveButton, 3, 0);
		gridPane.add(deleteButton, 4, 0);
		gridPane.add(connectButton, 5, 0);
		gridPane.add(closeButton, 6, 0);

		flowPane.getChildren().add(gridPane);
		return flowPane;
	}

	private void saveConnectionInfo() {
		if (((String) group.getSelectedToggle().getUserData()).equalsIgnoreCase("LOCAL")) {
			saveLocaConnectionInfo();
		} else {
			saveRemoteConnectionInfo();
		}
		System.out.println(ConnectInfoList.size());
		saveSatus.setFont(new Font("Arial", 15));
		
		try {
			new Serializer().storeConnectionList(ConnectInfoList);
			saveSatus.setText("Saved");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			saveSatus.setText("Failed");
		}
		
		
	}

	private void saveRemoteConnectionInfo() {
		ConnectInfo connectInfo = getConnectInfo(sessionNameText.getText());
		getConnectInfo(connectInfo);

	}
	private ConnectInfo getConnectInfo(ConnectInfo connectInfo){
		if(connectInfo == null){
			connectInfo=new ConnectInfo();
		}
		if (((String) group.getSelectedToggle().getUserData()).equalsIgnoreCase("LOCAL")) {
			connectInfo.setEnvironment("LOCAL");
			connectInfo.setTitle(localSessionNameText.getText());

			connectInfo.setFilePath(localPathText.getText());
		} else {
			connectInfo.setEnvironment("REMOTE");
			connectInfo.setTitle(sessionNameText.getText());
			connectInfo.setHost(hostNameText.getText());
			connectInfo.setUserName(userNameText.getText());
			connectInfo.setPassword(passwordText.getText());
			connectInfo.setFilePath(pathText.getText());
		}
		return connectInfo;
	}
	private void saveLocaConnectionInfo() {

		ConnectInfo connectInfo = getConnectInfo(localSessionNameText.getText());
		getConnectInfo(connectInfo);
	}

	private ConnectInfo getConnectInfo(String title) {
		for (ConnectInfo connectInfo : myObservableList) {
			if (connectInfo.getTitle().equals(title)) {
				return connectInfo;
			}
		}
		ConnectInfo ConnectInfo = new ConnectInfo();
		myObservableList.add(ConnectInfo);
		return ConnectInfo;
	}

	private void loadObjectInfo(ConnectInfo newValue) {
		if(newValue != null){
			if (newValue.getEnvironment().equalsIgnoreCase("LOCAL")) {

				localButton.setSelected(true);
				outerPane.setCenter(localContainer);
			} else {
				remoteButton.setSelected(true);
				outerPane.setCenter(remoteContainer);
			}
			localSessionNameText.setText(newValue.getTitle());
			sessionNameText.setText(newValue.getTitle());
			hostNameText.setText(newValue.getHost());
			userNameText.setText(newValue.getUserName());
			passwordText.setText(newValue.getPassword());
			pathText.setText(newValue.getFilePath());
			localPathText.setText(newValue.getFilePath());	
		}
		

	}

	private void resetFields() {
		sessionNameText.setText("");
		hostNameText.setText("");
		userNameText.setText("");
		passwordText.setText("");
		pathText.setText("");
		localPathText.setText("");
		localSessionNameText.setText("");
	}
	private void loadFile() {
		ConnectInfo connectInfo=getConnectInfo(new ConnectInfo());
		new TabCreator().createTab(connectInfo);		
	}
}
