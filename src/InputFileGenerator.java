import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Rahul
 * This Class generates the input file containing the required (key,value) pairs. 
 * (keys and values are non-negative integers) 
 * The Input file starts with an integer 'n', followed by n lines. 
 * Each line includes a key and a value separated by a space.
 */

public class InputFileGenerator {
	public static void main(String[] args) {

		int n = 1000000;	// Represents the number of values to be inserted
		FileWriter out = null ;
		File inputFile = new File(".\\InputFile.txt");
		try {
			out = new FileWriter(inputFile);
			out.write(n+"\n");

			List list = new ArrayList();
			for(int i=1 ; i <= n ; i++) {
				list.add(i);
			}
			Collections.shuffle(list);

			for(int i = 0; i < n; i++) {
				int k = (int)list.get(i);
				out.append(k+" "+ (2*k)+"\n");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
