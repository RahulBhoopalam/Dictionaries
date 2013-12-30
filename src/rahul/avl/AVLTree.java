package rahul.avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AVLTree {

	private AVLNode root;
	/* The tree-traversal methods write to an output file using this FileWriter object */
	private FileWriter fw_inorder ;
	private FileWriter fw_postorder ;

	/* Constructs a tree with no nodes */
	public AVLTree() {
		root = null;
	}

	/* Constructs a tree with the root node that has key = rootKey, value = rootValue.
	 * Used for random mode */
	public AVLTree(int rootKey, int rootValue) {
		//this();
		root = new AVLNode(rootKey, rootValue); 
	}

	/* Constructs a tree with no nodes -> for user-input mode 
	 * f_in		=>	file to which the in-order traversal output must be written into
	 * f_post	=>	file to which the post-order traversal output must be written into
	 */
	public AVLTree(File f_inorder, File f_postorder) {
		this();
		try {
			fw_inorder = new FileWriter(f_inorder, true);			
			fw_postorder = new FileWriter(f_postorder, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Constructs a tree with the root node that has key = rootKey, value = rootValue */
	public AVLTree(int rootKey, int rootValue, File f_inorder, File f_postorder) {
		this(f_inorder, f_postorder);
		root = new AVLNode(rootKey, rootValue); 
	}

	/* Deletes the subtree rooted at the 'root' */
	public void deleteAllNodes() {
		deleteAllNodesStartingFrom(root);
	}

	/* Deletes the subtree rooted at the 'current' node */
	public AVLNode deleteAllNodesStartingFrom(AVLNode current) {
		if(current != null) {
			deleteAllNodesStartingFrom(current.getLeft());
			deleteAllNodesStartingFrom(current.getRight());
			current = null;
		}
		return null;
	}

	/* Searches starting from the 'root'. 
	 * Internally uses search(key, startingNode)
	 */
	public AVLNode search(int key) {
		return search(key, root);
	}

	/* Searches starting from the 'startingNode' for the node containing 
	 * the value associated with the 'key' passed in as argument. 
	 * Found 		=> returns the node containing the value. 
	 * Not found	=> returns null
	 */
	public AVLNode search(int key, AVLNode startNode) {
		if (startNode == null) {
			return null;
		}

		if (key < startNode.getKey()) {
			return search(key, startNode.getLeft());
		}
		else if(key > startNode.getKey()) {
			return search(key, startNode.getRight());
		}
		else
			return startNode;
	}

	/* Returns the height of the passed-in node */
	public int getHeight (AVLNode node) {
		if (node == null)
			return -1;
		else
			return node.getHeight();
	}

	/* AVL Balancing - this method right-rotates the subtree rooted at 'gp' (the passed-in 'grand-parent' node) */
	public AVLNode rotateRightOnce (AVLNode gp) {
		AVLNode p;
		p = gp.getLeft();
		gp.setLeft(p.getRight());
		p.setRight(gp);
		gp.setHeight( Math.max(getHeight(gp.getLeft()), getHeight(gp.getRight())) + 1 );
		p.setHeight( Math.max(getHeight(p.getLeft()),gp.getHeight()) + 1 );
		return p;
	}

	/* AVL Balancing - this method left-rotates the subtree rooted at 'gp' (the passed-in 'grand-parent' node) */
	public AVLNode rotateLeftOnce (AVLNode gp) {
		AVLNode p;
		p = gp.getRight();
		gp.setRight(p.getLeft());
		p.setLeft(gp);
		gp.setHeight(Math.max(getHeight(gp.getLeft()), getHeight(gp.getRight()))+1);
		p.setHeight(Math.max(getHeight(p.getRight()),gp.getHeight())+1);
		return p;
	}

	/* Balances the subtree rooted at 'gp' - called when there is LR-type of imbalance */
	public AVLNode balanceLRCase (AVLNode gp) {
		gp.setLeft(rotateLeftOnce(gp.getLeft()));
		return rotateRightOnce(gp);
	}

	/* Balances the subtree rooted at 'gp' - called when there is RL-type of imbalance */
	public AVLNode balanceRLCase (AVLNode gp) {
		gp.setRight(rotateRightOnce(gp.getRight()));
		return rotateLeftOnce(gp);
	}

	/* Inserts a node into the tree */
	public void insert(int key, int value) {
		root = insert(key, value, root);
	}
	
	/* Inserts a node into the (sub)tree rooted at 'startNode' */
	public AVLNode insert (int key, int value, AVLNode startNode) {
		if(startNode == null) {
			startNode = new AVLNode(key, value);
		}

		else if(key < startNode.getKey()) {
			startNode.setLeft(insert( key, value, startNode.getLeft() ));
			startNode.getLeft().setParent(startNode);
			if( getHeight(startNode.getLeft()) - getHeight(startNode.getRight()) == 2 )
				if(key < startNode.getLeft().getKey())
					startNode = rotateRightOnce(startNode);
				else
					startNode = balanceLRCase(startNode);
		}

		else  if(key > startNode.getKey()) {
			startNode.setRight(insert( key, value, startNode.getRight() ));
			startNode.getRight().setParent(startNode);
			if ( getHeight(startNode.getLeft()) - getHeight(startNode.getRight()) == -2 )
				if(key > startNode.getRight().getKey())
					startNode = rotateLeftOnce(startNode);
				else
					startNode = balanceRLCase(startNode);
		}
		startNode.setHeight( Math.max(getHeight(startNode.getLeft()), getHeight(startNode.getRight())) + 1 );
		root = startNode;
		return startNode;
	}

	/* Does a pre-order traversal of the AVL tree rooted at 'startingNode' */
	/*
	public void preOrder(AVLNode startingNode) {
		out.print(startingNode.getKey()+" ");
		if(startingNode.getLeft() != null) {
			preOrder(startingNode.getLeft());
		}
		if(startingNode.getRight() != null) {
			preOrder(startingNode.getRight());
		}
	}
	 */

	/* Does an in-order traversal of the AVL tree rooted at 'root' */
	public void inOrder() throws IOException {
		inOrder(root);
		fw_inorder.append(System.getProperty("line.separator"));
	}

	/* Does an in-order traversal of the AVL tree rooted at 'startingNode' */
	public  void inOrder(AVLNode startingNode) {
		if(startingNode.getLeft() != null) {
			inOrder(startingNode.getLeft());
		}
		try {
			fw_inorder.append(startingNode.getValue()+" ");
			//System.out.print(startingNode.getValue()+" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(startingNode.getRight() != null) {
			inOrder(startingNode.getRight());
		}
	}

	/* Does an post-order traversal of the AVL tree rooted at 'root' */
	public void postOrder() throws IOException {
		postOrder(root);
		fw_postorder.append(System.getProperty("line.separator"));
	}

	/* Does an post-order traversal of the AVL tree rooted at 'startingNode' */
	public void postOrder(AVLNode startingNode) {
		if(startingNode.getLeft() != null) {
			postOrder(startingNode.getLeft());
		}		
		if(startingNode.getRight() != null) {
			postOrder(startingNode.getRight());
		}
		try {
			fw_postorder.append(startingNode.getValue()+" ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.print(startingNode.getKey()+" ");
	}

	/* Closes the FileWriter object that was previously opened to write the postorder output to */
	public void closePostOrderFileWriter() {
		try {
			fw_postorder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Closes the FileWriter object that was previously opened to write the inorder output to */
	public void closeInOrderFileWriter() {
		try {
			fw_inorder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		File f_inorder = new File(".\\AVL_inorder.out");
		File f_postorder = new File(".\\AVL_postorder.out");
		AVLTree avlTree1 = new AVLTree(f_inorder, f_postorder);
		AVLNode root = avlTree1.deleteAllNodesStartingFrom(null);
		int n = 2048;

		// Generate a random permutation of the keys 1 through n 
		List list = new ArrayList();
		for( int i=1; i<=n ; i++ )
			list.add(i);
		Collections.shuffle(list);

		// Insert these n keys and associated values in this order into the AVL Tree 
		for(int i = 0; i < n; i++) {
			//System.out.print((int)list.get(i));
			avlTree1.insert((int)list.get(i), 2*i);
		}

		try {
			avlTree1.postOrder();
			avlTree1.closePostOrderFileWriter();
			System.out.println();
			avlTree1.inOrder();
			avlTree1.closeInOrderFileWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//root = avlTree1.deleteAllNodesStartingFrom(null);
	}
}