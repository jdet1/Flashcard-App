package application;
	
import com.flashcards.Session;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	
	private static Session session;
	private static Stage stage;
	private static VBox root;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		session = new Session();
		try {
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
			primaryStage.setResizable(false);
			VBox page = FXMLLoader.load(Main.class.getResource("Main.fxml"));
			root = page;
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Flashcards");
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getStage(){
		return stage;
	}
	public static Session getSession(){
		return session;
	}
	public static VBox getRoot(){
		return root;
	}
}
