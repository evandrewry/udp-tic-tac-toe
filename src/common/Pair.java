package common;

public class Pair<LEFT, RIGHT> {
	private LEFT left;
	private RIGHT right;

	public Pair(LEFT left, RIGHT right) {
		this.left = left;
		this.right = right;
	}

	public LEFT getLeft() {
		return this.left;
	}

	public void setLeft(LEFT left) {
		this.left = left;
	}

	public RIGHT getRight() {
		return this.right;
	}

	public void setRight(RIGHT right) {
		this.right = right;
	}

}
