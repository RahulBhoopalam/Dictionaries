package rahul.avl;

public class AVLNode {

	private int key, value;
	private int height;
	private AVLNode left;
	private AVLNode right;
	private AVLNode parent;

	public AVLNode() {
		left = null; right = null;
		height = -1;
	}
	
	/* Constructor for the Node */
	public AVLNode(int key, int value) {
		this.key = key;
		this.value = value;
		left = null; right = null;
		height = 0;
	}

	/* Getter and setter methods for all of Node's fields */ 
	public int getKey() { return key; }
	public void setKey(int key) { this.key = key; }

	public int getValue() {	return value; }
	public void setValue(int value) { this.value = value; }
	
	public AVLNode getLeft() {	return left; }
	public void setLeft(AVLNode left) { this.left = left; }
	
	public AVLNode getRight() {	return right; }
	public void setRight(AVLNode right) { this.right = right; }
	
	public AVLNode getParent() { return parent; }
	public void setParent(AVLNode node) { parent = node; }
	
	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height; }
	
	public static void copy ( AVLNode from, AVLNode to ) {
		to.setKey(from.getKey());
		to.setValue(from.getValue());
		to.setHeight(from.getHeight());
		to.setLeft(from.getLeft());
		to.setRight(from.getRight());
		to.setParent(from.getParent());		
	}
}