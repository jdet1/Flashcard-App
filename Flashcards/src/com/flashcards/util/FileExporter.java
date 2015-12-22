package com.flashcards.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flashcards.model.Flashcard;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;

public class FileExporter {

	public static List<Flashcard> processFlashcardData(TabPane tabPane) {
		
		ObservableList<Tab> tabs = tabPane.getTabs();
		
		List<Flashcard> cards = new ArrayList<Flashcard>();
		
		for (Tab tab : tabs) {

			Flashcard card = new Flashcard();
			HBox content = (HBox) tab.getContent();
			HTMLEditor term = (HTMLEditor)content.getChildren().get(0);
			HTMLEditor definition = (HTMLEditor)content.getChildren().get(1);
			card.setTerm(term.getHtmlText());
			card.setDefinition(definition.getHtmlText());
			cards.add(card);

		}
		
		
		return cards;
	}

	public static String convertToJSON(List<Flashcard> cards) {
		
		JSONObject obj = new JSONObject();
		
		JSONArray arr = new JSONArray();
				
		for (Flashcard c : cards) {
			JSONObject o = new JSONObject();
			o.put("term", c.getTerm());
			o.put("definition", c.getDefinition());
			arr.put(o);
			
		}
		
		obj.put("cards", arr);
		
		return obj.toString();
		
	}
	
	public static void saveFile(String content, File file) {
		
		try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}
	
}
