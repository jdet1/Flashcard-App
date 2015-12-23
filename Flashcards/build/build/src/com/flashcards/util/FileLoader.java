package com.flashcards.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.flashcards.model.EditorSession;
import com.flashcards.model.Flashcard;
import com.flashcards.model.Session;


public class FileLoader {

	//TODO hack job for now... do this better
	public static boolean loadFile(EditorSession session, File file) throws Exception {
		Session temp = new Session();
		loadFile(temp, file);
		session.setFlashcards(temp.getFlashcards());
		session.setTitle(temp.getTitle());
		return true;
	}
	
	public static boolean loadFile(Session session, File file) throws Exception {
		
		session.setTitle(file.getName());
		
		List<Flashcard> cards = new ArrayList<Flashcard>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
		
			String fileContent = "";
			
			String temp = "";
			while ((temp = br.readLine()) != null) {
				fileContent += temp;
			}
			
			JSONObject obj = new JSONObject(fileContent);
			
			JSONArray arr = obj.getJSONArray("cards");
			
			for (int i=0;i<arr.length();i++) {
				JSONObject o = arr.getJSONObject(i);
				Flashcard card = new Flashcard();
				card.setTerm(o.getString("term"));
				card.setDefinition(o.getString("definition"));
				cards.add(card);
			}
			
			session.setFlashcards(cards);
			return true;
			
		} catch (Exception e) { //Should handle exceptions properly but...
			throw e;
		}
		
	}
	
}
