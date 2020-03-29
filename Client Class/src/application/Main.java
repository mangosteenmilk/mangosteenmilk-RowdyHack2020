package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
	public void start(Stage stage) throws IOException {
		 Parent root = FXMLLoader.load(getClass().getResource("Client.fxml"));    //load the GUI in 
	     Scene scene = new Scene(root, 300, 400);    //create scene
	     stage.setTitle("Class Chatroom");    //set title
	     stage.setScene(scene);    //set the scene onto the stage
	     scene.getStylesheets().add(getClass().getResource("Client.css").toExternalForm());    //load the .css file
	     stage.show();    //show the application
	    
	     
	     
	     //stage.setResizable(false);
         
	}
	
}
