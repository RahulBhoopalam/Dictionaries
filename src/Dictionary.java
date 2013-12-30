import rahul.avl.*;
import rahul.btree.BTree;
import rahul.btree.BTreeHash;
import rahul.redblack.RedBlackHash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rahul
 * COP 5536 Fall 2012 - Advanced Data Structures Programming Project  
 * Submitted by Rahul Bhoopalam
 */

public class Dictionary { 

	public static long startTime, endTime;	// To measure the time taken for some operations.

	/* Gets the 'number of key-value pairs' from the user */
	public static boolean getnFromUser(AtomicInteger arg) {
		System.out.println("Please input a POSITIVE INTEGER for n (number of key,value pairs in the dictionary)");
		Scanner reader = new Scanner(System.in);
		String input = reader.next();
		try {
			arg.set(Integer.parseInt(input));
			return false;
		}
		catch (NumberFormatException e) {
			return true;
		}
	}

	public static void main(String[] args) {

		if( args.length != 0 ) {
			if (args[0].equals("-r")) {
				/* The application was run in the RANDOM mode */
				
				AtomicInteger input = new AtomicInteger(0);
				while ( getnFromUser(input) ) ;
				//System.out.println("Here's the input = "+n);
				int n = input.get();
				/* Generate a random permutation of the keys 1 through n*/
				List list = new ArrayList();
				for(int i=1 ; i <= n ; i++) {
					list.add(i);
				}
				Collections.shuffle(list);

				/* Insert these n (key,value) pairs in the same order into each of the data structures */

				/* Create an AVLTree and insert the shuffled (key,value) pairs */ 
				AVLTree avlTree = new AVLTree();
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					avlTree.insert(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in AVL Tree = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					avlTree.search(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in AVL Tree = "+(endTime-startTime));

				/* size of the hashTable for AVLHash, RedBlackHash and BTreeHash */
				int size = Integer.parseInt(args[1]);

				/* Create an AVLHash structure and insert the shuffled (key,value) pairs */
				AVLHash avlHash = new AVLHash(size);
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					avlHash.insert(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in AVLHash structure = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					avlHash.search(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in AVLHash structure = "+(endTime-startTime));

				/* Create a RedBlack Tree and insert the shuffled (key,value) pairs */
				TreeMap redBlackTree = new TreeMap();
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					redBlackTree.put(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in Red-Black Tree = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					redBlackTree.get(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in Red-Black Tree = "+(endTime-startTime));

				/* Create a RedBlackHash structure and insert the shuffled (key,value) pairs */
				RedBlackHash redBlackHash = new RedBlackHash(size);
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					redBlackHash.insert(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in Red-Black Hash structure = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					redBlackHash.search(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in Red-Black Hash structure = "+(endTime-startTime));

				/* Create a BTree and insert the shuffled (key,value) pairs */
				int order = 3;
				BTree bTree = new BTree(order);
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					bTree.insert(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in a BTree = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					bTree.search(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in a BTree = "+(endTime-startTime));

				/* Create a BTreeHash structure and insert the shuffled (key,value) pairs */
				BTreeHash bTreeHash = new BTreeHash(size, order);
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					bTreeHash.insert(k, 2*k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" inserts in a BTreeHash structure = "+(endTime-startTime));

				/* Search for the values of all the keys (in the inserted order) */
				startTime = System.currentTimeMillis();
				for(int i = 0; i < n; i++) {
					int k = (int)list.get(i);
					bTreeHash.search(k);
				}
				endTime = System.currentTimeMillis();
				System.out.println("Time taken for "+n+" searches in a BTreeHash structure = "+(endTime-startTime));
			}

			/* User input mode */
			else if (args[0].equals("-u")) {
				/* Get the (key, value) pairs from the input file. */
				String inputFileName = args[1]; 
				File inputFile = new File(inputFileName);	//".\\InputFile.txt");
				BufferedReader input = null;
				try {
					input = new BufferedReader(new FileReader(inputFile));
					/* Input file starts with an integer 'n', followed by n lines. */
					int n = Integer.parseInt(input.readLine());

					/* Create an AVL Tree and insert the (key,value) pairs */
					File f_inorder = new File(".\\AVL_inorder.out");
					File f_postorder = new File(".\\AVL_postorder.out");
					AVLTree avlTree = new AVLTree(f_inorder, f_postorder);

					/* size of the hashTable for AVLHash, RedBlackHash and BTreeHash */
					int size = 3;

					/* Create an AVLHash structure and insert the (key,value) pairs read from file */
					File f_avlHash_inorder = new File(".\\AVLHash_inorder.out");
					File f_avlHash_postorder = new File(".\\AVLHash_postorder.out");
					AVLHash avlHash = new AVLHash(size, f_avlHash_inorder, f_avlHash_postorder);

					/* Create a BTree and insert the (key,value) pairs read from file */
					File f_inSortedOrder = new File(".\\BTree_sorted.out");
					File f_inLevelOrder = new File(".\\BTree_level.out");
					int order = 3;
					BTree bTree = new BTree(order, f_inSortedOrder, f_inLevelOrder);

					/* Create a BTreeHash structure and insert the (key,value) pairs read from file */
					File f_BTreeHash_inSortedOrder = new File(".\\BTreeHash_sorted.out");
					File f_BTreeHash_inLevelOrder = new File(".\\BTreeHash_level.out");
					BTreeHash bTreeHash = new BTreeHash(size, order, f_BTreeHash_inSortedOrder, f_BTreeHash_inLevelOrder);

					/* Each line includes a key and a value separated by a space. */
					String line ;
					while( (line = input.readLine() ) != null ) {
						String[] key_value = line.split(" ");
						int k = Integer.parseInt(key_value[0]), v = Integer.parseInt(key_value[1]);

						avlTree.insert(k, v);
						avlHash.insert(k, v);

						bTree.insert(k, v);
						bTreeHash.insert(k, v);
					}
					input.close();

					/* Traverse the AVL trees in inorder and postorder and write them to separate files */
					avlTree.inOrder();
					avlTree.closeInOrderFileWriter();
					avlTree.postOrder();
					avlTree.closePostOrderFileWriter();

					avlHash.all_inorder();
					avlHash.closeInOrderFileWriters();
					avlHash.all_postorder();
					avlHash.closePostOrderFileWriters();

					/* Traverse the BTrees in sorted-order and level-order and write them to separate files */
					bTree.sortedOrder();
					bTree.closeSortedOrderFileWriter();
					bTree.levelOrder();
					bTree.closeLevelOrderFileWriter();

					bTreeHash.all_sortedOrder();
					bTreeHash.closeSortedOrderFileWriters();
					bTreeHash.all_levelOrder();
					bTreeHash.closeLevelOrderFileWriters();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Please check if \""+args[1]+"\" is in \n"+System.getProperty("user.dir")+".\nI could not find that file there!\n");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error closing the BufferedReader.");
					e.printStackTrace();
				}			
			}
		}
		/* Incorrect arguments were passed to the application, so show the right way */
		else {
			System.out.println("The right way to run this application is:\n\tjava Dictionary <mode>\n" +
					"\t<mode> : -r => Random mode \n\t\t -u => User input mode\n" +
					"\tEg: java Dictionary -r <hashTableSize> <b-tree-order>\n" +
					"\t    java Dictionary -u <input-filename>");
			System.exit(0);
		}
	}
}