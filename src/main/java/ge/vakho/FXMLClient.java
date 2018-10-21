package ge.vakho;

import ge.vakho.helper.ErrorHandler;
import ge.vakho.socket.ClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLClient extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Set default alert dialog error handler 
		Thread.setDefaultUncaughtExceptionHandler(ErrorHandler::showError);
		
		// Load FXML
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/client.fxml"));

		// Create Scene
		Scene scene = new Scene(root, 640, 480);

		primaryStage.setTitle("Remote Desktop Client Application");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			ClientHandler.STOPPED = true;
	    });
	}

	
}
