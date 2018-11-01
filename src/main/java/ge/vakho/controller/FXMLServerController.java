package ge.vakho.controller;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import ge.vakho.socket.ServerHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLServerController {

	private Robot robot;
	private Rectangle rectangle;
	
	@FXML
	private Button startBtn;

	@FXML
	private Button stopBtn;

	@FXML
	private TextField portField;

	@FXML
	private PasswordField secretField;


	@FXML
	public void initialize() throws AWTException {
		
		// Initialize robot and rectangle classes and pass them...
		robot = new Robot();
		rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());;
		
		// Force numbers only for port field
		portField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				portField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}
	
	@FXML
	protected void handleServerStartButtonAction(ActionEvent event) throws Exception {
		toggleButtons();

		try {
			// Parse port number and start server
			final int port = Integer.valueOf(portField.getText());

			new ServerHandler(port, robot, rectangle).start();

		} catch (Exception e) {
			toggleButtons();
			
			// Re-throw to show error dialog
			throw e;
		}
	}

	@FXML
	protected void handleServerStopButtonAction(ActionEvent event) {
		// Stop server
		ServerHandler.STOPPED = true;
		toggleButtons();
	}

	private void toggleButtons() {
		startBtn.setDisable(!startBtn.isDisable());
		stopBtn.setDisable(!stopBtn.isDisable());
	}
}
