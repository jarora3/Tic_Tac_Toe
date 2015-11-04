/* Name - Jyoti Arora
 * Game class has the main function that takes initial state of the game as an input from the user and then use
 * AlphaBeta Min Max pruning to find the next optimal move. Implementation for ALphaBetaPruning algorithm is present in OptimalMove.java
 * file
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Game {
	private static String create_string_input(String str, String Delim) {
		 StringTokenizer st = new StringTokenizer(str, Delim);
	     String new_str = "";
	     while(st.hasMoreTokens()) {
	    	 String ch = st.nextToken(Delim);
	    	 ch = ch.trim();
	    	 new_str += ch;
	     }	
	     return new_str;
	}
	
	//Main Funtion
	
	public static void main(String args[]) throws IOException {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     System.out.print("Enter Initial Board Configuration  :  ");
	     String initial_state = br.readLine();
	     //Min_Max minMax = new Min_Max(create_string_input(initial_state, ","));
	     //minMax.find_optimal_move();
	     // Call to AlphaBetaPruning
	     AlphaBetaPruning instance = new AlphaBetaPruning(create_string_input(initial_state, ","));
	     instance.find_optimal_move();
	}
}
