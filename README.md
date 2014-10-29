Dictionaries
============

The purpose of this project is to compare the relative performance of AVL, Red-Black, B-trees and their counterparts with a hash table front end. The focus is on the search and insert operations. All keys used are required to be distinct.


Programming Environment
-----------------------

* Language used: Java

* Compiler and Runtime Environment: JDK 1.7.0_05, JRE 1.7.0_05

* Command to run the application:


    java –jar Dictionary.jar &lt;mode&gt; &lt;arguments&gt;

    &lt;mode&gt; : 
    * -r => Random mode </br>
    * -u => User input mode

    For example:

    java –jar Dictionary.jar -r <hashTableSize> <b-tree-order>
    java –jar Dictionary.jar -u <input-filename>


* Output

    The following files:

    * AVL_inorder.out
    * AVL_postorder.out
    * AVLHash_inorder.out
    * AVLHash_postorder.out
    * BTree_level.out
    * BTree_sorted.out
    * BTreeHash_level.out
    * BTreeHash_sorted.out

    will be created. They represent the nodes of the corresponding trees in **inorder**, **postorder** or **level**, **sorted** orders.
