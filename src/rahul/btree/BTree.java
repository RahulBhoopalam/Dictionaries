package rahul.btree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Rahul
 * This class represents a BTree and supports the insert and search operations.
 */

public class BTree {

	private BTreeNode root;
	private int order;

	/* The tree-traversal methods write to an output file using this FileWriter object */
	private FileWriter fw_inSortedOrder ;
	private FileWriter fw_inLevelOrder ;
	private Queue<BTreeNode> levelOrderQ ;

	/* Constructor for random mode 
	 * order => order of the BTree
	 */
	public BTree(int order) {
		this.order = (order%2 == 0) ? order/2 : (order+1)/2;
	}

	/* Constructor for user-input mode 
	 * order			=>	order of the BTree
	 * f_inSortedOrder	=>	file to which the sorted-order traversal output must be written into
	 * f_inLevelOrder	=>	file to which the level-order traversal output must be written into
	 */
	public BTree(int order, File f_inSortedOrder, File f_inLevelOrder) {
		this(order);
		try {
			fw_inSortedOrder = new FileWriter(f_inSortedOrder, true);			
			fw_inLevelOrder = new FileWriter(f_inLevelOrder, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Searches starting from the 'root'. 
	 * Internally uses search(key, startingNode)
	 */
	public int search(int key) {
		return search(key, root);
	}

	/* Searches starting from the 'startNode', for the node containing 
	 * the value associated with the 'key' passed in as argument. 
	 * Found 		=> returns the value associated with the key. 
	 * Not found	=> returns -1
	 */
	public int search(int key, BTreeNode startNode) {
		int i = 0;
		int n = startNode.getNoOfKeys();
		for (; i < n && key > startNode.getKeyAt(i) ; i++ );
		if ( i < n && key == startNode.getKeyAt(i) )
			return startNode.getValueAt(i);
		else if ( startNode.isLeaf() )
			return -1;
		else 
			return search(key, startNode.getChildAt(i));
	}

	/* Splits the (splitIndex)th child of the 'parent' passed in as argument
	 * This method is invoked whenever the child is full and cannot hold more keys. 
	 */
	public void splitChildOfNode(BTreeNode parent, int splitIndex) {
		BTreeNode rightPart = new BTreeNode(order);
		BTreeNode leftPart = parent.getChildAt(splitIndex);
		rightPart.setAmIaLeaf(leftPart.isLeaf());
		rightPart.setNoOfKeys( order - 1 );
		for(int j=0 ; j < order-1 ; j++) {
			rightPart.setKeyAt(j, leftPart.getKeyAt(j+order));
			rightPart.setValueAt(j, leftPart.getValueAt(j+order));
		}

		if ( !leftPart.isLeaf() ) {
			for(int j=0 ; j < order ; j++) {
				rightPart.setChildAt(j, leftPart.getChildAt(j+order));
			}
		}
		leftPart.setNoOfKeys(order-1);

		for(int j=parent.getNoOfKeys() ; j >= splitIndex+1 ; j--) {
			parent.setChildAt(j+1, parent.getChildAt(j));
		}
		parent.setChildAt(splitIndex+1, rightPart);
		
		for( int j=parent.getNoOfKeys()-1 ; j >= splitIndex; j--) {
			parent.setKeyAt(j+1, parent.getKeyAt(j));
			parent.setValueAt(j+1, parent.getValueAt(j));
		}
		parent.setKeyAt(splitIndex, leftPart.getKeyAt(order-1));
		parent.setValueAt(splitIndex, leftPart.getValueAt(order-1));
		parent.setNoOfKeys(parent.getNoOfKeys() + 1);
	}

	/* Inserts a (key,value) pair into a non-full BTree node */
	public void insertNonFull(int key, int value, BTreeNode startNode) {
		int keyIndex = startNode.getNoOfKeys()-1;
		if (startNode.isLeaf()) {
			while( keyIndex >= 0 && key < startNode.getKeyAt(keyIndex)) {
				startNode.setKeyAt(keyIndex+1, startNode.getKeyAt(keyIndex));
				startNode.setValueAt(keyIndex+1, startNode.getValueAt(keyIndex));
				keyIndex--;
			}
			startNode.setKeyAt(keyIndex+1, key);
			startNode.setValueAt(keyIndex+1, value);
			startNode.setNoOfKeys(startNode.getNoOfKeys() + 1);
		}
		else {
			while( keyIndex >= 0 && key < startNode.getKeyAt(keyIndex)) {
				keyIndex--;
			}
			keyIndex++;
			if (startNode.getChildAt(keyIndex).getNoOfKeys() == 2*order - 1 ) {
				splitChildOfNode(startNode, keyIndex);
				if(key > startNode.getKeyAt(keyIndex))
					keyIndex++;
			}
			insertNonFull(key, value, startNode.getChildAt(keyIndex));
		}
	}

	/* Inserts a (key,value) pair into this BTree */
	public void insert(int key, int value) {
		if ( root == null ) {
			root = new BTreeNode(order);
			root.setAmIaLeaf(true);
			root.addKey(key);
			root.addValue(value);
			root.setNoOfKeys(1);
			levelOrderQ = new ConcurrentLinkedQueue<BTreeNode>();
		}
		else {
			if ( root.getNoOfKeys() == ( 2*order - 1 ) ) {
				BTreeNode originalRoot = root;
				BTreeNode newNode = new BTreeNode(order);
				root = newNode;
				newNode.setAmIaLeaf(false);
				newNode.setNoOfKeys(0);
				newNode.addChild(originalRoot);
				splitChildOfNode(newNode, 0);
				insertNonFull(key, value, newNode);			
			}
			else
				insertNonFull(key, value, root);
		}
	}

	/* Traverses the BTree rooted at 'root' and prints values in sorted order */
	public void sortedOrder() throws IOException {
		sortedOrder(root);
		fw_inSortedOrder.append(System.getProperty("line.separator"));
	}

	/* Traverses the BTree rooted at 'startingNode' and prints values in sorted order */
	public  void sortedOrder(BTreeNode startingNode) throws IOException { 
		if( startingNode.isLeaf() ) {
			int n = startingNode.getNoOfKeys();
			for(int i=0; i<n; i++) {
				fw_inSortedOrder.append(startingNode.getValueAt(i)+" ");
				//System.out.println(startingNode.getValueAt(i)+" ");
			}
		}
		else {
			int i;
			int c = startingNode.getNoOfKeys()+1; //Children();
			for(i=0; i<c-1 ; i++) {
				sortedOrder(startingNode.getChildAt(i));
				fw_inSortedOrder.append(startingNode.getValueAt(i)+" ");
				//System.out.println(startingNode.getValueAt(i)+" ");
			}
			sortedOrder(startingNode.getChildAt(i));
		}
	}
	
	/* Traverses the BTree rooted at 'root' level-wise */
	public void levelOrder() throws IOException {
		levelOrder(root);
		fw_inLevelOrder.append(System.getProperty("line.separator"));
	}

	/* Traverses the BTree rooted at 'startingNode', level-wise */
	public void levelOrder(BTreeNode startingNode) {
		levelOrderQ.add(startingNode);
		startingNode.setLevelOrderMark(true);
		BTreeNode temp;
		while ( !levelOrderQ.isEmpty() ) {
			temp = levelOrderQ.remove();
			/* Print all Values in this node */
			int n = temp.getNoOfKeys();
			if ( n != 0) {
				try {
					for(int i=0; i<n ; i++) {
						fw_inLevelOrder.append(temp.getValueAt(i)+" ");
						//System.out.println(temp.getValueAt(i)+" ");
					}
					//fw_inLevelOrder.append("    ");
				} catch (Exception e) { //IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int c=0; c<temp.getNoOfChildren() ; c++) {
				BTreeNode child = temp.getChildAt(c);
				if( !child.isLevelOrderMark() ) {
					child.setLevelOrderMark(true);
					levelOrderQ.add(child);
				}
			}
		}
	}

	/* Closes the FileWriter object that was previously opened to write the level-order output to */
	public void closeLevelOrderFileWriter() {
		try {
			fw_inLevelOrder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Closes the FileWriter object that was previously opened to write the sorted-order output to */
	public void closeSortedOrderFileWriter() {
		try {
			fw_inSortedOrder.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File f_inSortedOrder = new File(".\\BTree_sorted.out");
		File f_inLevelOrder = new File(".\\BTree_level.out");
		int order = 200;	//Integer.parseInt(args[0]);
		BTree bTree = new BTree(order, f_inSortedOrder, f_inLevelOrder);
		int n = 1000000; //Integer.parseInt(args[1]);

		// Generate a random permutation of the keys 1 through n
		List list = new ArrayList();
		for( int i=1; i<=n ; i++ )
			list.add(i);
		Collections.shuffle(list);
		
		// Insert these n keys and associated values in this order into the BTree
		long startTime = System.currentTimeMillis();
		for(int i=0 ; i < n; i++) {
			int k = (int)list.get(i);
			bTree.insert(k, 2*k);
			System.out.print(" "+k);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("\nTime taken for "+n+" inserts in BTree = "+(endTime-startTime));

		startTime = System.currentTimeMillis();
		for(int i=0 ; i < n; i++) {
			bTree.search((int)list.get(i));
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time taken for "+n+" searches in BTree = "+(endTime-startTime));
		
		try {
			bTree.sortedOrder();
			bTree.closeSortedOrderFileWriter();
			bTree.levelOrder();
			bTree.closeLevelOrderFileWriter();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
