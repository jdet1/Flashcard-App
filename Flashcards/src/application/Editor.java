package application;

import com.flashcards.model.EditorSession;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Editor {

	
	private static Stage stage;
	private static EditorSession session;
	
	public static void openEditor() {
		
		try {
			
			session = new EditorSession();
			
			VBox root1 = FXMLLoader.load(Main.class.getResource("Editor.fxml"));
	        stage = new Stage();
	        stage.setTitle("Editor");
	        stage.setScene(new Scene(root1));  
	        stage.show();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStage() {
		return stage;
	}
	public static EditorSession getSession(){
		return session;
	}
}
