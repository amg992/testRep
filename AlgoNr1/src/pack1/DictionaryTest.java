/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 6.7.2017
 */
package pack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Scanner;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class DictionaryTest {

	/**
	 * @param args not used.
	 */
	public static void main(String[] args)  throws FileNotFoundException{
		
		testSortedArrayDictionary();
		testPerformanceSortedArray();
		testHashDictionary();
//		testBinaryTreeDictionary();
	}

	private static void testSortedArrayDictionary() {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}
	
	private static void testPerformanceSortedArray() throws FileNotFoundException {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		pt(dict);
	}
	
	private static void testHashDictionary() {
		Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}
	
//	private static void testBinaryTreeDictionary() {
//		Dictionary<String, String> dict = new BinaryTreeDictionary<>();
//		testDict(dict);
//        
//        // Test für BinaryTreeDictionary mit prettyPrint 
//        // (siehe Aufgabe 10; Programmiertechnik 2).
//        // Pruefen Sie die Ausgabe von prettyPrint auf Papier nach.
//        BinaryTreeDictionary<Integer, Integer> btd = new BinaryTreeDictionary<>();
//        btd.insert(10, 0);
//        btd.insert(20, 0);
//        btd.insert(30, 0);
//        System.out.println("insert:");
//        btd.prettyPrint();
//
//        btd.insert(40, 0);
//        btd.insert(50, 0);
//        System.out.println("insert:");
//        btd.prettyPrint();
//
//        btd.insert(21, 0);
//        System.out.println("insert:");
//        btd.prettyPrint();
//
//        btd.insert(35, 0);
//        btd.insert(60, 0);
//        System.out.println("insert:");
//        btd.prettyPrint();
//
//        System.out.println("For Each Loop:");
//        for (Dictionary.Entry<Integer, Integer> e : btd) {
//            System.out.println(e.getKey() + ": " + e.getValue());
//        }
//
//        btd.remove(30);
//        System.out.println("remove:");
//        btd.prettyPrint();
//
//        btd.remove(35);
//        btd.remove(40);
//        btd.remove(50);
//        System.out.println("remove:");
//        btd.prettyPrint();
//    }
	
	private static void testDict(Dictionary<String, String> dict) {
		System.out.println("===== New Test Case ========================");
		System.out.println("test " + dict.getClass());
		System.out.println(dict.insert("gehen", "go") == null);		// true
		String s = new String("gehen");
		System.out.println(dict.search(s) != null);					// true
		System.out.println(dict.search(s).equals("go"));			// true
		System.out.println(dict.insert(s, "walk").equals("go"));	// true
		System.out.println(dict.search("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen") == null);  // true
		dict.insert("starten", "start");
		dict.insert("gehen", "go");
		dict.insert("schreiben", "write");
		dict.insert("reden", "say");
		dict.insert("arbeiten", "work");
		dict.insert("lesen", "read");
		dict.insert("singen", "sing");
		dict.insert("schwimmen", "swim");
		dict.insert("rennen", "run");
		dict.insert("beten", "pray");
		dict.insert("tanzen", "dance");
		dict.insert("schreien", "cry");
		dict.insert("tauchen", "dive");
		dict.insert("fahren", "drive");
		dict.insert("spielen", "play");
		dict.insert("planen", "plan");
		for (Dictionary.Entry<String, String> e : dict) {
			System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
		}
	}
	private static void pt(Dictionary<String, String> dict) throws FileNotFoundException {
		String myfile = "/home/reif/eclipse-workspace/AlgoNr1/src/pack1/dtengl.txt";
		File file1 = new File(myfile);
		FileReader fr = new FileReader(myfile);
		Scanner scanner = new Scanner(fr);
		long startTime = System.currentTimeMillis();
		while (scanner.hasNext()) {
			String[] tokens1 = scanner.nextLine().split(" ");
			String key = tokens1[0];
			String value = tokens1[1];
			dict.insert(key, value); 
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("Millisekunden: "+time);

}
	}
