import java.io.File;
import java.io.FileWriter;
import java.util.regex.*;
import java.util.Scanner;

public class Finder{

	public static void main(String[] args){
		findWord(args[0], new File("port/service-names-port-numbers.csv"));
	}

	public static void findWord(String word, File file) {
		// int lineNo = 0;		
		try{
			Scanner scanner = new Scanner(file);
			FileWriter fw = new FileWriter(word+".txt", true);
			while (scanner.hasNextLine()) {
				// lineNo++;
			    String nextToken = scanner.nextLine();
			    Pattern p = Pattern.compile(".*"+word+".*", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(nextToken);
			    if (m.matches())
			    	fw.write(nextToken+"\n\n");
			    	// System.out.println("line "+ lineNo + ": " + nextToken);
			}
			fw.close();
		}catch(Exception e){}		
	}

}