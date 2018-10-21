package ge.vakho.model;

import java.io.Serializable;
import java.util.Base64;

public class ServerResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imageBase64;

	public ServerResponse() {
	}

	public ServerResponse(byte[] imageBytes) {
		this.imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
	}

	public ServerResponse(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public byte[] getImageBytes() {
		return Base64.getDecoder().decode(imageBase64);
	}
	
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	@Override
	public String toString() {
		return "ServerResponse [imageBase64=" + imageBase64 + "]";
	}
}