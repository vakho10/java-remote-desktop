package ge.vakho.socket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ge.vakho.model.ServerRequest;
import ge.vakho.model.ServerResponse;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClientHandler extends Thread {

	public static boolean STOPPED = false;

	private Socket socket;

	private ImageView imageView;

	public ClientHandler(String host, int port, ImageView imageView) throws UnknownHostException, IOException {
		this.socket = new Socket(host, port);
		this.imageView = imageView;
	}

	@Override
	public void run() {

		try (OutputStream sos = socket.getOutputStream(); // Get output stream at first
				InputStream sis = socket.getInputStream(); // Get input stream afterwards

				ObjectOutputStream oos = new ObjectOutputStream(sos);
				ObjectInputStream ois = new ObjectInputStream(sis)) {

			// Get first image response from server
			final ServerResponse firstResponse = (ServerResponse) ois.readObject();

			// TODO do stuff with request object
			System.out.println("Client firstly got: " + firstResponse);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(firstResponse.getImageBytes())) {
				imageView.setImage(new Image(bais));
			}
			
			// Loop cycle if application is not stopped yet
			int i = 1;
			while (!STOPPED) {

				// Send coordinates and clicks
				final ServerRequest request = new ServerRequest(110, 220, false, false);
				oos.writeObject(request);

				System.out.println("Client sent(" + i +"): " + request);

				// Get image response
				ServerResponse response = (ServerResponse) ois.readObject();
				System.out.println("Client got(" + i++ +"): " + response);
				try (ByteArrayInputStream bais = new ByteArrayInputStream(response.getImageBytes())) {
					imageView.setImage(new Image(bais));
				}				
			}
		} catch (Exception e) {

			// Stop application
			ClientHandler.STOPPED = true;

			// Re-throw error as runtime exception
			throw new RuntimeException(e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}
}