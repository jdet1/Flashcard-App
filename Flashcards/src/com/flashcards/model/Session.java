package com.flashcards.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Session {
	
	private String title;
	private List<Flashcard> flashcards;
	private int index = 0;
	private int cardSide = 0; //0 = term, 1 = definition
	private int defaultCardSide = 0;
	
	public Session(){
		flashcards = new ArrayList<Flashcard>();
		title = "";
	}
	public String getTitle() {
		return title.replace(".flashcard", "");
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Flashcard> getFlashcards() {
		return flashcards;
	}
	public void setFlashcards(List<Flashcard> flashcards) {
		this.flashcards = flashcards;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		if (index > flashcards.size()-1) {
			this.index = 0;
		} else if (index < 0){
			this.index = flashcards.size()-1;
		} else { 
			this.index = index;
		}
	}
	public int getDefaultCardSide() {
		return defaultCardSide;
	}
	public void setDefaultCardSide(int defaultCardSide) {
		this.defaultCardSide = defaultCardSide;
	}
	public Flashcard getCurrentCard() {
		if (flashcards.size() > 0) {
			return flashcards.get(index);
		} else {
			return new Flashcard();
		}
	}
	public int getCardSide() {
		return cardSide;
	}
	public void setCardSide(int cardSide) {
		this.cardSide = cardSide;
	}
	public boolean cardsLoaded(){
		return !flashcards.isEmpty();
	}
	public String getCardText() {
		if (cardSide==0){
			return getCurrentCard().getTerm();
		} else {
			return getCurrentCard().getDefinition();
		}
	}
	public String getCardCountLabelText() {
		return index + 1 + " of "+flashcards.size();
	}
	
	public void shuffle() {
		if (cardsLoaded()) {
			List<Flashcard> shuffled = new ArrayList<Flashcard>();
			
			int size = flashcards.size();
			List<Integer> oldVals = new ArrayList<Integer>();
			
			for (int i=0; i< size; i++) {
				Random rand = new Random();
				int x = rand.nextInt(size);
				while (oldVals.contains(x)) {
					x = rand.nextInt(size);
				}
				oldVals.add(x);
				shuffled.add(flashcards.get(x));
			}
					
			flashcards = shuffled;
		}
	}
	
	public void reset() {
		this.flashcards = new ArrayList<Flashcard>();
		this.index = 0;
		this.cardSide = defaultCardSide;
		this.title = "";
	}
}
