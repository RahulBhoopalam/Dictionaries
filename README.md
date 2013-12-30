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
