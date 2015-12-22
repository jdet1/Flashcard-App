package com.flashcards;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.flashcards.model.Flashcard;
import com.flashcards.util.FileExporter;
import com.flashcards.util.FileLoader;

import application.Editor;
import application.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;

public class EditorController {

	@FXML
	private TabPane tabPane;

	@FXML
	private void addTab() {
		addTab("FRONT OF CARD (TERM)", "BACK OF CARD (DEFINITION)");
	}

	@FXML
	private void newFile() {
		
		if (Editor.getSession().getCardCount() > 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
	
			alert.setTitle("Are you sure?");
			alert.setContentText("Are you sure you want to start a new file?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				Editor.getSession().reset();
				update();
			}
			
		} else {
			Editor.getSession().reset();
			update();
		}
		
		
	}

	@FXML
	private void saveAs() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Flashcard File");

		fileChooser.setInitialDirectory(new File(Main.getDefaultDirectory()));

		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter("FlashCard files (*.flashcard)", "*.flashcard");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(Editor.getStage());

		if (file != null) {
			List<Flashcard> cards = FileExporter.processFlashcardData(tabPane);
			String content = FileExporter.convertToJSON(cards);
			FileExporter.saveFile(content, file);
		}
	}
	
	@FXML
	private void open(){

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Flashcard File");

		fileChooser.setInitialDirectory(new File(Main.getDefaultDirectory()));

		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter("FlashCard files (*.flashcard)", "*.flashcard");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(Editor.getStage());

		if (file != null) {
			Editor.getSession().reset();
			try {
				FileLoader.loadFile(Editor.getSession(), file);
				Editor.getStage().setTitle("Editor: " +Editor.getSession().getTitle());
				update();
			} catch (Exception e) {
				openLoadError();
				update();
			}

		}

	}

	@FXML
	private void quit(){
		Editor.getStage().close();
	}

	@FXML
	private void openAbout() {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("About");
		
		alert.setHeaderText("About Flashcards");
		alert.setContentText("An actual Help menu will be here eventually... \n\n - Created by James DeTizio");
		
		alert.showAndWait();
	}
	
	private void update() {

		tabPane.getTabs().clear();

		if (!Editor.getSession().getFlashcards().isEmpty()) {
			for (Flashcard card : Editor.getSession().getFlashcards()) {
				addTab(card.getTerm(), card.getDefinition());
			}
		} else {
			addTab();
		}

	}

	private void addTab(String frontCardText, String backCardText) {

		int cardCount = Editor.getSession().getCardCount();
		cardCount++;
		Editor.getSession().setCardCount(cardCount);
		ObservableList<Tab> tabs = tabPane.getTabs();
		int newTabPos = tabs.size();
		Tab tab = new Tab();
		
		ContextMenu contextMenu = new ContextMenu();
		
		MenuItem addTabBtn = new MenuItem(" Add");
		Image addImg = new Image(Main.class.getResourceAsStream("icons/add.png"), 20, 20, true, true);
		addTabBtn.setGraphic(new ImageView(addImg));
		addTabBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				addTab("", "");
			}
		});

		MenuItem deleteTabBtn = new MenuItem(" Delete");
		Image deleteImg = new Image(Main.class.getResourceAsStream("icons/delete.png"), 20, 20, true, true);
		deleteTabBtn.setGraphic(new ImageView(deleteImg));
		deleteTabBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				if (tabPane.getTabs().size() > 1) {
					tabPane.getTabs().remove(tab);
				}
			}
		});
		
		MenuItem moveLftBtn = new MenuItem(" Move Back");
		Image leftImg = new Image(Main.class.getResourceAsStream("icons/left.png"), 20, 20, true, true);
		moveLftBtn.setGraphic(new ImageView(leftImg));
		moveLftBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				
				int pos = 0;
				for (Tab t : tabPane.getTabs()) { 
					if (t.equals(tab)) {
						break;
					} else {
						pos++;
					}
				}
				if (pos != 0) {
					tabPane.getTabs().remove(tab);
					tabPane.getTabs().add(pos-1, tab);
				}
			}
		});
		
		MenuItem moveRgtBtn = new MenuItem(" Move Forward");
		Image rightImg = new Image(Main.class.getResourceAsStream("icons/right.png"), 20, 20, true, true);
		moveRgtBtn.setGraphic(new ImageView(rightImg));
		moveRgtBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				
				int pos = 0;
				for (Tab t : tabPane.getTabs()) { 
					if (t.equals(tab)) {
						break;
					} else {
						pos++;
					}
				}
				if (pos < tabPane.getTabs().size()-1) {
					tabPane.getTabs().remove(tab);
					tabPane.getTabs().add(pos+1, tab);
				}
			}
		});
		
		contextMenu.getItems().add(addTabBtn);
		contextMenu.getItems().add(moveLftBtn);
		contextMenu.getItems().add(moveRgtBtn);
		contextMenu.getItems().add(deleteTabBtn);
		

		tab.setContextMenu(contextMenu);

		tab.setText("Card "+cardCount);
		tab.setClosable(false);
		
		HTMLEditor frontEditor = new HTMLEditor();
		frontEditor.setId("front");
		frontEditor.setHtmlText(frontCardText);
		HTMLEditor backEditor = new HTMLEditor();
		backEditor.setId("back");
		backEditor.setHtmlText(backCardText);

		HBox main = new HBox();
		main.getChildren().add(frontEditor);
		main.getChildren().add(backEditor);

		tab.setContent(main);

		tabs.add(newTabPos, tab);

		tabPane.getSelectionModel().select(newTabPos);
	}

	private void openLoadError(){
		Alert alert = new Alert(AlertType.ERROR);

		alert.setTitle("Error");
		alert.setHeaderText("Error Loading File!");
		alert.setContentText("Error loading flashcard file. Make sure the file is in valid JSON format.");
		alert.showAndWait();
	}


}
