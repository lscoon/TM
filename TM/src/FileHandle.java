
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandle {
	
	public static final String inputFilePath = "inputs" + File.separator;
	public static final String outputFilePath = "outputs" + File.separator;
	public static String caseDirectory = "";
	
	public static void main(String[] args) {
		if(args.length > 1)
			System.out.println("more than one args, error return");
		
		caseDirectory = caseDirectory.concat(args[0]).concat(File.separator);
		//caseDirectory = "case-directory/case2/";
		String tmFileName = caseDirectory.concat("test.tm");
		List<String> lines = readTMFile(tmFileName);
		Solver solver = new Solver(lines);
		
		//consoleInputTest(solver);
		setConsoleFile(caseDirectory.concat("console.txt"));
		
		String inputFileName = caseDirectory.concat("input.txt");
		List<String> inputs = readInputFile(inputFileName);
		
		List<String> outputs = new ArrayList<String>();
		for(String input: inputs)
			outputs.add(run(solver,input));
		String result = "";
		for(String output: outputs)
			result = result.concat(output).concat("\n");
		result = result.substring(0,result.length()-1);
		
		try {
			FileOutputStream outputStream = new FileOutputStream(caseDirectory.concat("result.txt"));
			outputStream.write(result.getBytes(), 0, result.length());;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void consoleInputTest(Solver solver) {
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("Input: ");
			String line = scan.nextLine();
			run(solver,line);
		}
	}
	
	private static void setConsoleFile(String filename) {
		try {
			System.setOut(new PrintStream(new FileOutputStream(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String run(Solver solver, String input) {
		System.out.println("Input: " + input);
		boolean breakFlag = false;
		for(int i = 0; i<input.length(); i++)
			if(!solver.inputSymbolSatisfied(input.charAt(i))) {
				breakFlag = true;
				break;
			}
		if(breakFlag) {
			System.out.println("==================== ERR ====================");
			System.out.println("The input "+input+" is illegal");
			System.out.println("==================== END ====================");
			System.out.println();
			return "Error";
		}
		else {
			System.out.println("==================== RUN ====================");
			Simulator simu = new Simulator(solver,input);
			System.out.println("Result: "+simu.result);
			System.out.println("==================== END ====================");
			System.out.println();
			if(simu.isAccept == true)
				return "True";
			else return "False";
		}
		//return "False";
	}
	
	private static List<String> readTMFile(String fileName) {
		List<String> lines = new ArrayList<>();
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			
			String line;
			while((line = br.readLine())!= null) {
				if(line.contains(";")) {
					int commentIndex = line.indexOf(";");
					line = line.substring(0, commentIndex);
					int spaceNum = 0;
					for(int i= line.length()-1; i>=0; i--) {
						if(line.charAt(i) == ' ')
							spaceNum++;
						else break;
					}
					line = line.substring(0, commentIndex-spaceNum);
				}
				if(line.length() == 0)
					continue;
				lines.add(line);
			}
			reader.close();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			return lines;
		}
		return lines;
	}
	
	private static List<String> readInputFile(String fileName) {
		List<String> lines = new ArrayList<>();
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			
			String line;
			while((line = br.readLine())!= null) {
				lines.add(line);
			}
			reader.close();
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			return lines;
		}
		return lines;
	}
}
