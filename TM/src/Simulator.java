
import java.util.LinkedHashMap;
import java.util.Map;

import org.javatuples.Triplet;

public class Simulator {
	
	private Map<String,State> states = new LinkedHashMap<>();
	private char blankSymbol = ' ';
	private String startState;
	
	private StringBuilder currTape;
	private String currState;
	private int currHeadIndex;
	private int currStep;
	private Solver.Direction currDirection;
	
	public String result;
	
	public Simulator(Solver solver, String input){
		states = solver.getStates();
		blankSymbol = solver.getBlankSymbol();
		for(State state: states.values())
			if(state.isStart == true) {
				startState = state.name;
				break;
			}
		
		String blankString = "x";
		blankString = blankString.replace('x', blankSymbol);
		currTape = new StringBuilder(blankString.concat(input).concat(blankString));
		
		currState = startState;
		currHeadIndex = 1;
		currStep = 0;
		currDirection = Solver.Direction.star;
		
		result = run();
	}
	
	private String run() {
		while(states.get(currState).isFinal == false){
			print();
			step();
			currStep++;
		}
		print();
		
		int beginIndex=0 ,endIndex = 0;
		for(int i = 0; i<currTape.length();i++)
			if(currTape.charAt(i) != blankSymbol) {
				beginIndex = i;
				break;
			}
		for(int i = currTape.length()-1; i>=0; i--)
			if(currTape.charAt(i) != blankSymbol) {
				endIndex = i+1;
				break;
			}
		return currTape.toString().substring(beginIndex, endIndex);
	}

	private void step() {
		//make sure curr head index is pointed to the next one
		//because we have added a blank to the left of the tape
		int tempHeadIndex = 0;
		if(currDirection == Solver.Direction.l)
			tempHeadIndex = currHeadIndex-1;
		else if(currDirection == Solver.Direction.r)
			tempHeadIndex = currHeadIndex+1;
		else tempHeadIndex = currHeadIndex;
		
		State s = states.get(currState);
		char headChar = currTape.charAt(tempHeadIndex);
		if(s.transFuncs.containsKey(headChar)) {
			Triplet<Character,Solver.Direction,String> tri = s.transFuncs.get(headChar);
			currTape.setCharAt(tempHeadIndex,tri.getValue0());
			if(currDirection == Solver.Direction.l)
				currHeadIndex--;
			else if(currDirection == Solver.Direction.r)
				currHeadIndex++;

			currDirection = tri.getValue1();
			currState = tri.getValue2();
		}
		else if(s.transFuncsStar.containsKey('*')) {
			Triplet<Character,Solver.Direction,String> tri = s.transFuncsStar.get('*');
			if(tri.getValue0() != '*')
				currTape.setCharAt(tempHeadIndex,tri.getValue0());
			if(currDirection == Solver.Direction.l)
				currHeadIndex--;
			else if(currDirection == Solver.Direction.r)
				currHeadIndex++;

			currDirection = tri.getValue1();
			currState = tri.getValue2();
		}
	}
	
	private void print() {
		int beginIndex = 0;
		int endIndex = 0;
		for(int i = 0; i<currTape.length();i++)
			if(currTape.charAt(i) != blankSymbol) {
				beginIndex = i;
				break;
			}
		for(int i = currTape.length()-1; i>=0; i--)
			if(currTape.charAt(i) != blankSymbol) {
				endIndex = i+1;
				break;
			}
		beginIndex = Math.min(beginIndex,currHeadIndex);
		endIndex = Math.max(endIndex, currHeadIndex);
		
		System.out.println("Step  : " + currStep);
		System.out.print("Index :");
		for(int i=beginIndex; i<endIndex; i++) {
			int temp = i-1;
			if(temp<0)
				temp = -temp;
			System.out.print(" " + temp);
		}	
		System.out.println();
		
		System.out.print("Tape  :");
		for(int i=beginIndex; i<endIndex; i++)
			System.out.print(" " + currTape.charAt(i));
		System.out.println();
		
		System.out.print("Head  : ");
		for(int i = beginIndex; i< currHeadIndex; i++)
			System.out.print("  ");
		System.out.println("^");
		
		System.out.println("State : " + currState);
		System.out.println("---------------------------------------------");
	}
}
