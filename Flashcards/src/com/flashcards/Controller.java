package com.flashcards;

import java.io.File;

import com.flashcards.model.Session;
import com.flashcards.util.FileLoader;

import application.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class Controller {
	
	@FXML
	Label titleLabel;
	@FXML
	TextArea cardText;
	@FXML
	Label cardCountLabel;
	
	
	public void update() {
		Session session = Main.getSession();
		cardText.setText(session.getCardText());
		
		cardCountLabel.setText(session.getCardCountLabelText());
		cardCountLabel.setAlignment(Pos.CENTER);
	}
	
	
	@FXML
	private void loadFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Flashcard File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+"/Desktop"));
		
		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter("FlashCard files (*.flashcard)", "*.flashcard");
        fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(Main.getStage());
		
		if (file != null) {
			Main.getSession().reset();
			try {
				FileLoader.loadFile(Main.getSession(), file);
				titleLabel.setText(Main.getSession().getTitle());
				update();
			} catch (Exception e) {
				openLoadError();
				update();
			}
			
		}
	}
	
	@FXML
	private void showDefinitionFirst(){
		Session session = Main.getSession();
		
		if (session.getDefaultCardSide() == 0) {
			session.setDefaultCardSide(1);
			session.setCardSide(1);
		} else {
			session.setDefaultCardSide(0);
			session.setCardSide(0);
		}
		update();
	}
	
	@FXML
	private void flipCard(){
		Session session = Main.getSession();
		if (session.cardsLoaded()) {
			if (session.getCardSide() == 0) {
				session.setCardSide(1);
			} else {
				session.setCardSide(0);
			}
			update();
		}
	}
	
	@FXML
	private void nextCard(){
		Session session = Main.getSession();
		if (session.cardsLoaded()) {
			session.setCardSide(session.getDefaultCardSide());
			session.setIndex(session.getIndex()+1);
			update();
		}
	}
	@FXML
	private void prevCard(){
		Session session = Main.getSession();
		if (session.cardsLoaded()) {
			session.setCardSide(session.getDefaultCardSide());
			session.setIndex(session.getIndex()-1);
			update();
		}
	}
	@FXML
	private void doShuffle() {
		Session session = Main.getSession();
		if (session.cardsLoaded()) {
			session.shuffle();
			session.setIndex(0);
			session.setCardSide(session.getDefaultCardSide());
			update();
		}
	}
	
	private void openLoadError(){
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setTitle("Error");
		alert.setHeaderText("Error Loading File!");
		alert.setContentText("Error loading flashcard file. Make sure the file is in valid JSON format.");
		alert.showAndWait();
	}
	
	@FXML
	private void openAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("About");
		
		alert.setHeaderText("About Flashcards");
		alert.setContentText("Created by James DeTizio");
		
		alert.showAndWait();
	}
	
	@FXML
	private void quit(){
		System.exit(0);
	}
}	
