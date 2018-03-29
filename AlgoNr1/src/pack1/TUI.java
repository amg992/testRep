package pack1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class TUI {	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		Dictionary<String, String> data = null;
		String[] command = {};
		System.out.println("create <Implementation> | Legt ein Dictionary an.");
		System.out.println("read [n] Dateiname>     | Liest die ersten n Einträg der Datei in das Dictionary ein.");
		System.out.println("p                       | Gibt alle Einträge des Dictionary in der Konsole aus.");
		System.out.println("s <deutsch>             | Gibt das entsprechende englische Wort aus.");
		System.out.println("i <deutsch> <englisch>  | Fügt ein neues Wortpaar in das Dictionary ein.");
		System.out.println("r <deutsch>             | Löscht den Eintrag.");
		System.out.println("exit                    | Beendet das Programm.");
		System.out.println("help                    | Text wird erneut angezeigt.");
		System.out.println("b <8> oder b <16>       | Benchmarking mit 8k oder 16k Befehlen.");
		
		
		while(scan.hasNext()) {
			command = scan.nextLine().split(" ");
			switch (command[0]) {
			case "create":
			if (command.length == 1 || command[1].equals("SortedArrayDictionary")) {
				data = new SortedArrayDictionary<>();
				System.out.println("SortedArrayDictionary angelegt.");
			} else if (command[1].equals("HashDictionary")) {
				data = new HashDictionary<>(3);
				System.out.println("HashDictionary angelegt.");
			} else {
				System.out.println("Keine gültige Eingabe!");
			}
			break;
			
			case "read":
			JFileChooser dat = new JFileChooser();
			int rg = dat.showOpenDialog(null);
			if (rg == JFileChooser.APPROVE_OPTION) {
				String s = dat.getSelectedFile().getAbsolutePath();
				if (command.length == 1) {
				insert(data, s);
				} else {
					int anzahl = Integer.parseInt(command[1]);
					insert(data, s, anzahl);
				}
			}
			break;
			
			case "p":
				for (Dictionary.Entry<String, String> e : data) {
					System.out.println(e.getKey() + ": " + e.getValue());
				}
			break;
			
			case "s":
			if(command.length == 1) {
				System.out.println("Wort vergessen!");
				break;
			} else {
				if(data.search(command[1]) == null) {
					System.out.println("Wort nicht vorhanden!");
					break;
				}
				String h = command[1];
				System.out.println(data.search(h));
			}
			break;
			
			case "i":
				if (command.length == 3) {
				data.insert(command[1], command[2]);
				}
			break;
			
			case "r":
				if (command.length == 2) {
				data.remove(command[1]);
				}
			break;
			
			case "exit":
			System.exit(0);
			break;
			case "b":
				JFileChooser dat2 = new JFileChooser();
				int op = dat2.showOpenDialog(null);
				if (op == JFileChooser.APPROVE_OPTION) {
					String s = dat2.getSelectedFile().getAbsolutePath();
				if (command.length == 1) {
					benchMark(data,s,8000);
				} else {
					if(command[1].equals("8k")) {
						benchMark(data,s,8000);
					} else {
						benchMark(data,s,16000);
					}
				}
				}
				break;
			case "help":
			System.out.println("create <Implementation> | Legt ein Dictionary an.");
			System.out.println("read [n] Dateiname>     | Liest die ersten n Einträg der Datei in das Dictionary ein.");
			System.out.println("p                       | Gibt alle Einträge des Dictionary in der Konsole aus.");
			System.out.println("s <deutsch>             | Gibt das entsprechende englische Wort aus.");
			System.out.println("i <deutsch> <englisch>  | Fügt ein neues Wortpaar in das Dictionary ein.");
			System.out.println("r <deutsch>             | Löscht den Eintrag.");
			System.out.println("exit                    | Beendet das Programm.");
			System.out.println("help                    | Text wird erneut angezeigt.");
			System.out.println("b <8> oder b <16>       | Benchmarking mit 8k oder 16k Befehlen.");
			}
		}
	}
	private static void insert(Dictionary d, String path) throws FileNotFoundException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		Scanner scanner = new Scanner(fr);
		while (scanner.hasNext()) {
			String[] tokens1 = scanner.nextLine().split(" ");
			String key = tokens1[0];
			String value = tokens1[1];
			d.insert(key, value);
		}
	}
	private static void insert(Dictionary d, String path, int n) throws FileNotFoundException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		Scanner scanner = new Scanner(fr);
		int o = 0;
		while (scanner.hasNext() && o <= n) {
			String[] tokens1 = scanner.nextLine().split(" ");
			String key = tokens1[0];
			String value = tokens1[1];
			d.insert(key, value);
			o++;
		}
	}
	
	private static void benchMark(Dictionary d, String path, int n) throws FileNotFoundException {
		File fe = new File(path);
		FileReader fre = new FileReader(fe);
		Scanner scannere = new Scanner(fre);
		int o = 0;
		long startTime = System.currentTimeMillis();
		while (scannere.hasNext() && o <= n) {
			String[] tokens1 = scannere.nextLine().split(" ");
			String key = tokens1[0];
			String value = tokens1[1];
			d.insert(key, value);
			o++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Millisekunden: " + time);
		
	}
}
