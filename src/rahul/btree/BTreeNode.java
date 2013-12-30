package rahul.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode {

	private List<Integer> keysArray;	// Sorted array of keys in this node
	private List<Integer> valuesArray;	// Sorted array of keys in this node
	private int noOfKeys;	// no. of keys currently stored in this node
	private int maxNoOfKeysICanContain, minNoOfKeysIMustContain;	// Limits of keysArray (by definition)
	private boolean amIaLeaf;	// true => this node is a leaf ; false if it isn't
	private List<BTreeNode> children;	//	An array to store references to this node's children
	
	private boolean levelOrderMark;	// Indicates if this node has already been traversed during the level-order traversal 

	BTreeNode(int order) {
		children = new ArrayList<BTreeNode>();
		keysArray = new ArrayList<Integer>();
		valuesArray = new ArrayList<Integer>();
		maxNoOfKeysICanContain = 2*order - 1 ;
		minNoOfKeysIMustContain = (order / 2) + 1  ;	// Ceiling(m/2)
		amIaLeaf = false;
	}
	
	/* Getter and Setter methods of all the fields of BTreeNode */
	public int getNoOfKeys() { return noOfKeys;	}
	public void setNoOfKeys(int k) { noOfKeys = k; }
	
	public int getKeyAt(int index) { return keysArray.get(index).intValue(); }
	public void setKeyAt(int index, int key) {
		try {
			keysArray.set(index, key);
		} catch (IndexOutOfBoundsException e) {
			addKey(key);
		}
	}
	public void addKey(int key) { keysArray.add(key); }
	
	public int getValueAt(int index) { return valuesArray.get(index).intValue(); }
	public void setValueAt(int index, int value) { 
		try {
			valuesArray.set(index, value);
		} catch (IndexOutOfBoundsException e) {
			addValue(value);
		} 
	}
	public void addValue(int value) { valuesArray.add(value); }
	
	public void setAmIaLeaf(boolean yesOrNo) { amIaLeaf = yesOrNo; }
	public boolean isLeaf() { return amIaLeaf; }
	
	public BTreeNode getChildAt(int index) { return children.get(index); }
	public void setChildAt(int index, BTreeNode child) { 
		try {
			children.set(index, child);
		} catch (IndexOutOfBoundsException e) {
			addChild(child);
		} 
	}
	public void addChild(BTreeNode child) { children.add(child); }
	public int getNoOfChildren() { return children.size(); }
	
	public boolean isLevelOrderMark() { return levelOrderMark; }
	public void setLevelOrderMark(boolean tOrf) { levelOrderMark = tOrf; }
	
	public static void main(String[] args) {
		/* Does nothing for now */
	}
}
