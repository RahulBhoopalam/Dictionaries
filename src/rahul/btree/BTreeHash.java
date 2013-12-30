package rahul.btree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rahul
 * This class uses a hash table with each slot containing a Bree (or a pointer to an BTree). 
 * The hash table, itself, is a one-dimensional array whose size, s, is specified by the user. 
 * The hash function used is (key mod s). 
 * Note that the search method for BTreeHash simply does a search in the BTree stored in the slot “key mod s”.
 */
public class BTreeHash {

	private BTree[] hashTable;
	private int size; 
	private File f_inSortedOrder, f_inLevelOrder;
	private FileWriter fw_inSortedOrder, fw_inLevelOrder;
	private int order;
	
	/* Constructor for random mode 
	 * size			=>	size of the hashtable which stores the underlying BTrees
	 * order		=>	order of the BTrees in the hashtable
	 */
	public BTreeHash( int size, int order ) {
		this.size = size;
		this.order = order;
		hashTable = new BTree[size];
	}
	
	/* Constructor for user-input mode 
	 * size			=>	size of the hashtable which stores the underlying BTrees
	 * order		=>	order of the BTrees in the hashtable
	 * f_inSorted	=>	file to which the sorted-order traversal output must be written into
	 * f_inLevel	=>	file to which the level-order traversal output must be written into
	 */
	public BTreeHash( int size, int order, File f_inSorted, File f_inLevel ) {
		this(size,order);
		f_inSortedOrder = f_inSorted;
		f_inLevelOrder = f_inLevel;
	}
	
	/* Inserts a (key,value) pair into a BTree in the hashtable */
	public void insert (int key, int value) {
		int index = key % size ;

		/* Create a new AVLTree if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			if (f_inSortedOrder != null && f_inLevelOrder != null)
				hashTable[index] = new BTree(order, f_inSortedOrder, f_inLevelOrder);
			else 
				hashTable[index] = new BTree(order);
		}
		
		hashTable[index].insert(key, value);		
	}
	
	/* Searches for the value associated with the 'key' passed in as argument. 
	 * Found 		=> returns the value associated with the key 
	 * Not found	=> returns -1
	 */
	public int search (int key) {
		int index = key % size ;

		/* Create a new AVLTree if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			return -1;
		}
		return hashTable[index].search(key);
	}
	
	/* Does an sorted-order traversal of the BTree in each slot of the hashtable */
	public void all_sortedOrder() {
		try {
			fw_inSortedOrder = new FileWriter(f_inSortedOrder, true);

			for( int i=0; i<hashTable.length; i++ ) {
				hashTable[i].sortedOrder();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Does a level-order traversal of the BTree in each slot of the hashtable */
	public void all_levelOrder() {
		try {
			fw_inLevelOrder = new FileWriter(f_inLevelOrder, true);

			for( int i=0; i<hashTable.length; i++ ) {
				hashTable[i].levelOrder();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Closes the FileWriter objects that were previously opened to write the level-order output to */
	public void closeLevelOrderFileWriters() {
		try {
			fw_inLevelOrder.close();
			for(int i=0; i<size; i++) {
				hashTable[i].closeLevelOrderFileWriter();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Closes the FileWriter objects that were previously opened to write the sorted-order output to */
	public void closeSortedOrderFileWriters() {
		try {
			fw_inSortedOrder.close();
			for(int i=0; i<size; i++) {
				hashTable[i].closeSortedOrderFileWriter();
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
		int size = 101;	//Integer.parseInt(args[0]);	//3;
		int order = 100;	//Integer.parseInt(args[1]);	//10;
		File f_BTreeHash_inSortedOrder = new File(".\\BTreeHash_sorted.out");
		File f_BTreeHash_inLevelOrder = new File(".\\BTreeHash_level.out");
		BTreeHash bTreeHash = new BTreeHash(size, order, f_BTreeHash_inSortedOrder, f_BTreeHash_inLevelOrder);
		
		int n = 1000000;	//Integer.parseInt(args[2]);
		
		// Generate a random permutation of the keys 1 through n 
		List list = new ArrayList();
		for( int i=1; i<=n ; i++ )
			list.add(i);
		Collections.shuffle(list);

		// Insert these n keys and associated values in this order into the AVL Tree
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < n; i++) {
			int k = (int)list.get(i);
			bTreeHash.insert(k, 2*k);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for "+n+" inserts in BTreeHash = "+(endTime-startTime));
		
		startTime = System.currentTimeMillis();
		for(int i = 0; i < n; i++) {
			int k = (int)list.get(i);
			bTreeHash.search(k);
		}
		endTime = System.currentTimeMillis();
		System.out.println("Time taken for "+n+" searches in BTreeHash = "+(endTime-startTime));
		
		bTreeHash.all_sortedOrder();
		bTreeHash.closeSortedOrderFileWriters();
		bTreeHash.all_levelOrder();
		bTreeHash.closeLevelOrderFileWriters();		
	}
}
