package ge.vakho.model;

import java.io.Serializable;

public class ServerRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int x, y;
	private boolean leftButtonClicked, rightButtonClicked;

	public ServerRequest() {
	}

	public ServerRequest(int x, int y, boolean leftButtonClicked, boolean rightButtonClicked) {
		this.x = x;
		this.y = y;
		this.leftButtonClicked = leftButtonClicked;
		this.rightButtonClicked = rightButtonClicked;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isLeftButtonClicked() {
		return leftButtonClicked;
	}

	public void setLeftButtonClicked(boolean leftButtonClicked) {
		this.leftButtonClicked = leftButtonClicked;
	}

	public boolean isRightButtonClicked() {
		return rightButtonClicked;
	}

	public void setRightButtonClicked(boolean rightButtonClicked) {
		this.rightButtonClicked = rightButtonClicked;
	}

	@Override
	public String toString() {
		return "ServerRequest [x=" + x + ", y=" + y + ", leftButtonClicked=" + leftButtonClicked
				+ ", rightButtonClicked=" + rightButtonClicked + "]";
	}
}