package rahul.redblack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Rahul
 * This class uses a hash table with each slot containing a red-black tree (or a pointer to red-black tree). 
 * The hash table, itself, is a one-dimensional array whose size, s, is specified by the user. 
 * The hash function used is (key mod s). 
 * Note that the search method for RedBlackHash simply does a search in the red-black tree stored in the slot “key mod s”.
 */

public class RedBlackHash {

	private TreeMap[] hashTable;
	private int size; 
	
	/* Constructor for random mode 
	 * size	=>	size of the hashtable which stores the underlying Red-Black Trees
	 */
	public RedBlackHash( int size ) {
		this.size = size;
		hashTable = new TreeMap[size];
	}
	
	/* Inserts a (key,value) pair into a Red-Black Tree in the hashtable */
	public void insert (int key, int value) {
		int index = key % size ;

		/* Create a new TreeMap if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			TreeMap redBlackTree = new TreeMap();
			hashTable[index] = redBlackTree;
		}
		hashTable[index].put(key, value);		
	}
	
	/* Searches for the value associated with the 'key' passed in as argument. 
	 * Found 		=> returns the value associated with the key 
	 * Not found	=> returns -1
	 */
	public Object search (int key) {
		int index = key % size ;

		/* Create a new TreeMap if there isn't already one at the required index */
		if ( hashTable[index] == null ) {
			return null;
		}
		return hashTable[index].get(key);		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 10 ;
		RedBlackHash redBlackHash = new RedBlackHash(size);
		
		int n = 100;
		
		// Generate a random permutation of the keys 1 through n 
		List list = new ArrayList();
		for( int i=1; i<=n ; i++ )
			list.add(i);
		Collections.shuffle(list);

		// Insert these n keys and associated values in this order into the Red-Black Tree 
		for(int i = 0; i < n; i++) {
			int k = (int)list.get(i);
			redBlackHash.insert(k, 2*k);
		}
		
		//Search for the values associated to these n keys and in the same order
		for(int i = 1; i <= n; i++) {
			int index = i % size ;
			System.out.print(redBlackHash.hashTable[index].get(i)+" ");
		}
	}

}
