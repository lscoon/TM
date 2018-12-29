
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solver {

	private List<String> lines = new ArrayList<>();
	private List<String> transFuncStrs = new ArrayList<>();
	private Map<String,State> states = new LinkedHashMap<>();
	private Set<Character> inputSymbols = new HashSet<>();
	private Set<Character> tapeSymbols = new HashSet<>();
	
	public static enum Direction{l,r,star};
	private char blankSymbol = ' ';
	
	public Solver(List<String> strings) {
		lines = strings;
		try {
			readDefinition();
			readTransFunc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//testPrint();
	}
	
	public Map<String,State> getStates(){
		return states;
	}
	
	public char getBlankSymbol() {
		return blankSymbol;
	}
	
	private void readDefinition() throws Exception{
		int defCount = 0;
		
		for(String line : lines) {
			if(line.length()>=2 && line.startsWith("#")) {
				char mark = line.charAt(1);
				switch(mark) {
					case 'Q':readQ(line);break;
					case 'S':readS(line);break;
					case 'T':readT(line);break;
					case 'q':readq0(line);break;
					case 'B':readB(line);break;
					case 'F':readF(line);break;
					default:
						throw new Exception("undefined #?");
				}
				defCount++;
			}
			else transFuncStrs.add(line);
		}
		
		if(defCount != 6)
			throw new Exception("definition count line do not equal six");
		
		if(!checkInputAndTapeSymbols())
			throw new Exception("input and tape symbols set not satisfied");
	}
	
	private void readQ(String line) throws Exception {
		String statesStr[] = splitString(line,'{','}',",");
		if(statesStr != null) {
			for(int i = 0; i<statesStr.length;i++) {
				String tempStr = statesStr[i];
				if(states.put(tempStr, new State(tempStr)) != null)
					throw new Exception("states put error");
			}
		}
		else throw new Exception("statesStr analysis error");
	}
	
	private void readS(String line) throws Exception {
		String inputSymbolsStr[] = splitString(line,'{','}',",");
		if(inputSymbolsStr != null) {
			for(int i = 0; i<inputSymbolsStr.length; i++) {
				String tempStr = inputSymbolsStr[i];
				if(tempStr.length()!=1)
					throw new Exception("inputSymbol not a char");
				char tempChar = tempStr.charAt(0);
				inputSymbols.add(tempChar);
			}
		}
		else throw new Exception("inputSymbolsStr analysis error");
	}

	private void readT(String line) throws Exception {
		String tapeSymbolsStr[] = splitString(line,'{','}',",");
		if(tapeSymbolsStr != null) {
			for(int i = 0; i<tapeSymbolsStr.length; i++) {
				String tempStr = tapeSymbolsStr[i];
				if(tempStr.length()!=1)
					throw new Exception("tapeSymbol not a char");
				char tempChar = tempStr.charAt(0);
				tapeSymbols.add(tempChar);
			}
		}
		else throw new Exception("tapeSymbolsStr analysis error");
	}
	
	private void readq0(String line) throws Exception {
		String startStateStr = line;
		if(startStateStr.startsWith("#q0")) {
			startStateStr = startStateStr.substring(startStateStr.indexOf("=")+2,startStateStr.length());
			states.get(startStateStr).isStart = true;
		}
		else throw new Exception("startStateStr analysis error");
	}

	private void readB(String line) throws Exception {
		String blankSymbolStr = line;
		if(blankSymbolStr.startsWith("#B")) {
			char tempBlankSymbol = blankSymbolStr.charAt(blankSymbolStr.indexOf("=")+2);
			blankSymbol = tempBlankSymbol;
		}
		else throw new Exception("blankSymbolStr analysis error");
	}
	
	private void readF(String line) throws Exception {
		String finalStatesStr[] = splitString(line,'{','}',",");
		if(finalStatesStr != null) {
			for(int i = 0; i<finalStatesStr.length; i++) {
				String tempStr = finalStatesStr[i];
				if(!states.containsKey(tempStr))
					throw new Exception("final state not in states");
				else states.get(tempStr).isFinal = true;
			}
		}
		else throw new Exception("finalStatesStr analysis error");
	}
	
	private String[] splitString(String str, char leftSymbol, char RightSymbol, String splitSymbol) {
		int leftIndex = str.indexOf(leftSymbol);
		int rightIndex = str.indexOf(RightSymbol);
		str = str.substring(leftIndex+1, rightIndex);
		String strs[] = str.split(splitSymbol);
		return strs;
	}
	
	private boolean checkInputAndTapeSymbols() {
		if(tapeSymbols.containsAll(inputSymbols))
			return true;
		else return false;
	}
	
	private void readTransFunc() throws Exception{
		for(String line : transFuncStrs) {
			String strs[] = line.split(" ");
			if(strs.length != 5)
				throw new Exception("transFunc format error");
			else if(!states.containsKey(strs[0])) 
				throw new Exception("transFunc start state error");
			else if(!states.containsKey(strs[4]))
				throw new Exception("transFunc final state error");
			else if (!states.get(strs[0]).addTransFunc(this,strs))
				throw new Exception("transFunc("+line+") add error, maybe symbol problem");
		}
	}
	
	public boolean tapeSymbolSatisfied(char c) {
		if(tapeSymbols.contains(c) || c == '*')
			return true;
		else return false;
	}
	
	public boolean inputSymbolSatisfied(char c) {
		if(inputSymbols.contains(c) || c == '*')
			return true;
		else return false;
	}
	
	private void testPrint() {
		for(State s: states.values()) {
			System.out.println("State: "+ s.name);
			System.out.println("isStart: " + s.isStart);
			System.out.println("isFinal: " + s.isFinal);
			for(char c : s.transFuncs.keySet()) {
				System.out.println(c + "," + s.transFuncs.get(c).getValue0() 
						+ "," +s.transFuncs.get(c).getValue1() 
						+ "," + s.transFuncs.get(c).getValue2());
			}
			for(char c : s.transFuncsStar.keySet()) {
				System.out.println(c + "," + s.transFuncsStar.get(c).getValue0() 
						+ "," +s.transFuncsStar.get(c).getValue1() 
						+ "," + s.transFuncsStar.get(c).getValue2());
			}
			System.out.println();
		}
	}
}