package rahul.avl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rahul
 * This class uses a hash table with each slot containing an AVL tree (or a pointer to an AVL tree). 
 * The hash table, itself, is a one-dimensional array whose size, s, is specified by the user. 
 * The hash function used is (key mod s). 
 * Note that the 'search(key)' method does a search in the AVL tree stored in the slot “key mod s”.
 */

public class AVLHash {

	private AVLTree[] hashTable;
	private int size; 
	private File f_inorder, f_postorder;
	private FileWriter fw_inorder, fw_postorder;
	
	/* Constructor for random mode 
	 * size	=>	size of the hashtable which stores the underlying AVLTrees
	 */
	public AVLHash( int size ) {
		this.size = size;
		hashTable = new AVLTree[size];
	}
	
	/* Constructor for user-input mode 
	 * size		=>	size of the hashtable which stores the underlying AVLTrees
	 * f_in		=>	file to which the in-order traversal output must be written into
	 * f_post	=>	file to which the post-order traversal output must be written into
	 */
	public AVLHash( int size, File f_in, File f_post ) {
		this(size);
		f_inorder = f_in;
		f_postorder = f_post;
	}
	
	public void insert (int key, int value) {
		int index = key % size ;

		/* Create a new AVLTree if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			if (f_inorder != null && f_postorder != null)
				hashTable[index] = new AVLTree(f_inorder, f_postorder);
			else 
				hashTable[index] = new AVLTree();
		}
		
		hashTable[index].insert(key, value);		
	}
	
	/* Searches for the AVLnode containing the value associated with the 'key' passed in as argument. 
	 * Found 		=> returns the node containing the value. 
	 * Not found	=> returns null
	 */
	public AVLNode search (int key) {
		int index = key % size ;

		/* Create a new AVLTree if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			return null;
		}
		return hashTable[index].search(key);		
	}
	
	/* Does an inorder traversal of the AVL tree in each slot of the hashtable */
	public void all_inorder() {
		try {
			fw_inorder = new FileWriter(f_inorder, true);

			for( int i=0; i<hashTable.length; i++ ) {
				hashTable[i].inOrder();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Does a postorder traversal of the AVL tree in each slot of the hashtable */
	public void all_postorder() {
		try {
			fw_postorder = new FileWriter(f_postorder, true);

			for( int i=0; i<hashTable.length; i++ ) {
				hashTable[i].postOrder();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Closes the FileWriter objects that were previously opened to write the postorder output to */
	public void closePostOrderFileWriters() {
		try {
			fw_postorder.close();
			for(int i=0; i<size; i++) {
				hashTable[i].closePostOrderFileWriter();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Closes the FileWriter objects that were previously opened to write the inorder output to */
	public void closeInOrderFileWriters() {
		try {
			fw_inorder.close();
			for(int i=0; i<size; i++) {
				hashTable[i].closeInOrderFileWriter();
			}
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
		int size = 3;
		File f_avlHash_inorder = new File(".\\AVLHash_inorder.out");
		File f_avlHash_postorder = new File(".\\AVLHash_postorder.out");
		AVLHash avlHash = new AVLHash(size, f_avlHash_inorder, f_avlHash_postorder);		
		
		int n = 10;
		
		// Generate a random permutation of the keys 1 through n 
		List list = new ArrayList();
		for( int i=1; i<=n ; i++ )
			list.add(i);
		Collections.shuffle(list);

		// Insert these n keys and associated values in this order into the AVL Tree 
		for(int i = 0; i < n; i++) {
			int k = (int)list.get(i);
			avlHash.insert(k, 2*k);
		}
		
		avlHash.all_inorder();
		avlHash.closeInOrderFileWriters();
		avlHash.all_postorder();
		avlHash.closePostOrderFileWriters();		
	}

}
