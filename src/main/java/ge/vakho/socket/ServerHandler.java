package ge.vakho.socket;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import ge.vakho.model.ServerRequest;
import ge.vakho.model.ServerResponse;

public class ServerHandler extends Thread {

	public static boolean STOPPED = false;

	private ServerSocket serverSocket;
	private Socket socket;
	private Robot robot;
	private Rectangle rectangle;

	public ServerHandler(int port, Robot robot, Rectangle rectangle) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.robot = robot;
		this.rectangle = rectangle;
	}

	@Override
	public void run() {

		try {
			this.socket = serverSocket.accept();

			try (OutputStream sos = socket.getOutputStream(); // Get output stream at first
					InputStream sis = socket.getInputStream(); // Get input stream afterwards

					ObjectOutputStream oos = new ObjectOutputStream(sos);
					ObjectInputStream ois = new ObjectInputStream(sis)) {

				// First call requires to send back image!
				final ServerResponse firstResponse = createImageResponse();
				oos.writeObject(firstResponse);
				oos.flush();
				System.out.println("Server firstly sent: " + firstResponse);

				// Loop cycle if application is not stopped yet
				int i = 1;
				while (!STOPPED) {

					// Read request
					final ServerRequest request = (ServerRequest) ois.readObject();

					// TODO do stuff with request object
					System.out.println("Server got(" + i + "): " + request);

					// Send back the screen image
					final ServerResponse response = createImageResponse();
					oos.writeObject(response);
					oos.flush();
					System.out.println("Server sent(" + (i++) + "): " + firstResponse);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ServerResponse createImageResponse() throws IOException {
		BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
		File file = new File("screen-capture.png");
		boolean status = ImageIO.write(bufferedImage, "png", file);
		System.out.println("Screen Captured ? " + status + " File Path:- " + file.getAbsolutePath());

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			boolean status2 = ImageIO.write(bufferedImage, "png", baos);
			System.out.println("Screen flushed ? " + status2);

			return new ServerResponse(baos.toByteArray());
		}
	}
}