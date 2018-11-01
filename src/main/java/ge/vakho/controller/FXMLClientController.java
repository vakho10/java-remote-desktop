package ge.vakho.controller;

import ge.vakho.socket.ClientHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class FXMLClientController {

	@FXML
	private TextField portField;

	@FXML
	private TextField hostField;

	@FXML
	private PasswordField secretField;

	@FXML
	private Button connectBtn;

	@FXML
	private Button disconnectBtn;

	@FXML
	private ImageView imageView;
	
	@FXML
	private BorderPane imageViewParentLayout;	

	@FXML
	public void initialize() {
		// Force numbers only for port field
		portField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				portField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
		// bind imageView's width to parent componenet's width
		imageView.fitWidthProperty().bind(imageViewParentLayout.widthProperty());
		imageView.fitHeightProperty().bind(imageViewParentLayout.heightProperty());
	}

	@FXML
	protected void handleClientConnectButtonAction(ActionEvent event) throws Exception {

		toggleButtons();

		try {
			// Parse port number and start server
			final int port = Integer.valueOf(portField.getText());

			new ClientHandler(hostField.getText(), port, imageView).start();

		} catch (Exception e) {
			toggleButtons();

			// Re-throw to show error dialog
			throw e;
		}
	}

	@FXML
	protected void handleClientDisconnectButtonAction(ActionEvent event) {
		// Stop client
		ClientHandler.STOPPED = true;
		toggleButtons();
	}

	private void toggleButtons() {
		connectBtn.setDisable(!connectBtn.isDisable());
		disconnectBtn.setDisable(!disconnectBtn.isDisable());
	}
}