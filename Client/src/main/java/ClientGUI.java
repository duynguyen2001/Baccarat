
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;


public class ClientGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		 try {
	            // Read file fxml and draw interface.
	            Parent root = FXMLLoader.load(getClass()
	                    .getResource("/FXML/ClientFXML.fxml"));
	 
	            primaryStage.setTitle("My Application");
	            Scene s1 = new Scene(root, 1050, 700);
	            primaryStage.setScene(s1);
	            primaryStage.show();
	         
	        } catch(Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
