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
				System.out.println("Kein Wort oder nicht vorhanden");
			} else {
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
			case "help":
			System.out.println("create <Implementation> | Legt ein Dictionary an.");
			System.out.println("read [n] Dateiname>     | Liest die ersten n Einträg der Datei in das Dictionary ein.");
			System.out.println("p                       | Gibt alle Einträge des Dictionary in der Konsole aus.");
			System.out.println("s <deutsch>             | Gibt das entsprechende englische Wort aus.");
			System.out.println("i <deutsch> <englisch>  | Fügt ein neues Wortpaar in das Dictionary ein.");
			System.out.println("r <deutsch>             | Löscht den Eintrag.");
			System.out.println("exit                    | Beendet das Programm.");
			System.out.println("help                    | Text wird erneut angezeigt.");
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
}
