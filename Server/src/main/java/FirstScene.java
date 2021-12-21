import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FirstScene {
	private Scene sceneOne;
	private Text enterPort;
	private Button send;
	private TextField text;
	
	public FirstScene() {
		enterPort = new Text("Enter Your Port Number to Connect");
		send = new Button("Send");
		text = new TextField();
		
		VBox vbox = new VBox(enterPort, text, send);
		vbox.setAlignment(Pos.CENTER);
		BackgroundFill backgroundFill = new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
		vbox.setBackground(background);
		sceneOne = new Scene(vbox, 700,700);
	}
	
	public Button getButton() {
		return send;
	}
	
	public Scene getScene() {
		return sceneOne;
	}
	
	public TextField getText() {
		return text;
	}
	
}
