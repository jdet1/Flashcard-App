package com.flashcards.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TabPane;

public class EditorSession {

	private List<Flashcard> flashcards;
	private String title;
	private int cardCount;
	
	public EditorSession(){
		flashcards = new ArrayList<Flashcard>();
		title = "";
		cardCount = 0;
	}
	
	public List<Flashcard> getFlashcards() {
		return flashcards;
	}
	public void setFlashcards(List<Flashcard> flashcards) {
		this.flashcards = flashcards;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCardCount() {
		return cardCount;
	}
	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}
	
	public void reset() {
		flashcards = new ArrayList<Flashcard>();
		title = "";
		cardCount = 0;
	}
	
}
