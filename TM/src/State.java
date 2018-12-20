
import java.util.LinkedHashMap;
import java.util.Map;

import org.javatuples.Triplet;


public class State {
	public String name;
	public boolean isStart;
	public boolean isFinal;
	public Map<Character,Triplet<Character,Solver.Direction,String>> transFuncs = new LinkedHashMap<Character,Triplet<Character,Solver.Direction,String>>();
	public Map<Character,Triplet<Character,Solver.Direction,String>> transFuncsStar = new LinkedHashMap<Character,Triplet<Character,Solver.Direction,String>>();
	
	public State(String stateName) {
		name = stateName;
		isStart = false;
		isFinal = false;
	}
	
	public boolean addTransFunc(Solver solver, String[] strs) {
		char startSymbol = strs[1].charAt(0);
		if(!solver.tapeSymbolSatisfied(startSymbol))
			return false;
		
		char endSymbol = strs[2].charAt(0);
		if(!solver.tapeSymbolSatisfied(endSymbol))
			return false;
		
		char dirChar = strs[3].charAt(0);
		Solver.Direction dir;
		if(dirChar == 'l')
			dir = Solver.Direction.l;
		else if(dirChar == 'r')
			dir = Solver.Direction.r;
		else if(dirChar == '*')
			dir = Solver.Direction.star;
		else return false;
		
		Triplet<Character,Solver.Direction,String> transFunc = new Triplet<Character,Solver.Direction,String>(endSymbol,dir,strs[4]);
		if(startSymbol == '*' || endSymbol == '*')
			transFuncsStar.put(startSymbol, transFunc);
		else transFuncs.put(startSymbol, transFunc);
		
		return true;
	}
}
