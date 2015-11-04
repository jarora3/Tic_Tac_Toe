import java.util.*;


/*Node is a data structure from which the Min Max tree is constructed*/
class Node {
	String state;      // state of the board configuration
	public void set_state(String current_st) {
		state = current_st;
	}
	String get_state() { return state; }
}


/*OptimalMove is the base class from which Min_Max and AlphaBetaPruning classes are derived.
 * It contains the common functions which are required by both Min_Max and alphaBetaPruning to 
 * find the next optimal move 
 * 
 */
 
abstract public class OptimalMove {
	String initial_state;
	char next_move; /* char whose next optimal move is calculated*/
	int num_of_nodes = 0;
	
	OptimalMove(String state) {
		initial_state = state;
		int num_of_O = 0;
		int num_of_X = 0;
		for(int i = 0; i < initial_state.length(); i++) {
			if(initial_state.charAt(i) == 'O') {
				num_of_O++;
			} else if(initial_state.charAt(i) == 'X') {
				num_of_X++;
			}
		}
		if((num_of_O == num_of_X) ||(num_of_O > num_of_X)) {
			next_move = 'X';
		} else {
			next_move = 'O';
		}
	}
	int best_move;
	
	
	/*Function that determines if a given state configuration is a terminal state*/
	
	Boolean isTerminal(Node node) {
        String state = node.get_state();
        char opponent = next_move == 'X' ?'O': 'X';
        Boolean is_blank = false;
		for (int i = 0; i < state.length(); i++) {
			if(state.charAt(i) == 'b') {
				is_blank = true;
				break;
			}
		}
		if(!is_blank)
			return true;
		if((state.charAt(0) == state.charAt(1) && state.charAt(1) == state.charAt(2) && (state.charAt(0) == next_move || state.charAt(0) == opponent)) ||
		   (state.charAt(3) == state.charAt(4) && state.charAt(4) == state.charAt(5) &&  (state.charAt(3) == next_move || state.charAt(3) == opponent)) ||
		   (state.charAt(6) == state.charAt(7) && state.charAt(7) == state.charAt(8) && (state.charAt(6) == next_move || state.charAt(6) == opponent ))) {
			return true;
	    }
		
		if((state.charAt(0) == state.charAt(3) && state.charAt(3) == state.charAt(6) && (state.charAt(0) == next_move || state.charAt(0) == opponent)) ||
		   (state.charAt(1) == state.charAt(4) && state.charAt(4) == state.charAt(7) && (state.charAt(1) == next_move || state.charAt(1) == opponent)) ||
		   (state.charAt(2) == state.charAt(5) && state.charAt(5) == state.charAt(8) && (state.charAt(2) == next_move || state.charAt(2) == opponent))) {
			return true;
	    }
		if((state.charAt(0) == state.charAt(4) && state.charAt(4) == state.charAt(8) && (state.charAt(0) == next_move || state.charAt(0) == opponent)) ||
			(state.charAt(2) == state.charAt(4) && state.charAt(4) == state.charAt(6)&& (state.charAt(2) == next_move || state.charAt(2) == opponent))) {
			return true;
		 }
		 return false;
    }
	
	/*Function that calculates the utility value of a state configuration*/
	
	int utilityValue(Node node) {
		 String state = node.get_state();
	        Boolean is_blank = false;
			for (int i = 0; i < state.length(); i++) {
				if(state.charAt(i) == 'b') {
					is_blank = true;
					break;
				}
			}
			
			if((state.charAt(0) == state.charAt(1) && state.charAt(1) == state.charAt(2) && state.charAt(0) == next_move) ||
					   (state.charAt(3) == state.charAt(4) && state.charAt(4) == state.charAt(5) &&  state.charAt(3) == next_move) ||
					   (state.charAt(6) == state.charAt(7) && state.charAt(7) == state.charAt(8) && state.charAt(6) == next_move)) {
					return 1;
		    }
			if((state.charAt(0) == state.charAt(3) && state.charAt(3) == state.charAt(6) && state.charAt(0) == next_move) ||
					   (state.charAt(1) == state.charAt(4) && state.charAt(4) == state.charAt(7) && state.charAt(1) == next_move) ||
					   (state.charAt(2) == state.charAt(5) && state.charAt(5) == state.charAt(8) && state.charAt(2) == next_move)) {
						return 1;
		    }
			if((state.charAt(0) == state.charAt(4) && state.charAt(4) == state.charAt(8) && state.charAt(0) == next_move) ||
						(state.charAt(2) == state.charAt(4) && state.charAt(4) == state.charAt(6) && state.charAt(2) == next_move)) {
						return 1;
			}
			if(!is_blank)
				return 0;
			return -1;
	    }
	
	
	LinkedList<String> get_moves(Node node, char val) {
		String current_state = node.get_state();
		LinkedList<String> new_moves = new LinkedList<String>();
		int size = current_state.length();
		for (int i = 0; i < size; i++) {
			if(current_state.charAt(i) == 'b') {
				String new_state = "";
			    new_state = current_state.substring(0, i);
				new_state += val;
				if( i != size -1 )
					new_state += current_state.substring(i+1);
				new_moves.add(new_state);
				//System.out.println("Move generated : " + new_state);
				
			}
		}
		return new_moves;
	}
	abstract void find_optimal_move();
	
}

/*Min_Max class uses the min max algorithm to find the next optimal move*/
class Min_Max extends OptimalMove {
	Min_Max(String state) {
		super(state);
	}
	int minimax(Node node, Boolean maximizing_player) {
		if(isTerminal(node)) {
			//System.out.println("trminal " + node.get_state());
			return utilityValue(node);
		}
		if(maximizing_player){
			int best_value = Integer.MIN_VALUE;
			LinkedList<String> lMoves = get_moves(node, next_move);
			Iterator<String> lMovesIterator = lMoves.iterator();
			while(lMovesIterator.hasNext()) {
				Node new_state = new Node();
				num_of_nodes++;
				new_state.set_state(lMovesIterator.next());
				//System.out.println("maximizing " + new_state.get_state());
				best_value = Math.max(best_value, minimax(new_state, false));
			}
			return best_value;
		}else{
			int best_value = Integer.MAX_VALUE;
			char opponent = next_move == 'X' ?'O': 'X';
			LinkedList<String> lMoves = get_moves(node, opponent);
			Iterator<String> lMovesIterator = lMoves.iterator();
			while(lMovesIterator.hasNext()) {
				Node new_state = new Node();
				num_of_nodes++;
				new_state.set_state(lMovesIterator.next());
				//System.out.println("minimizing " + new_state.get_state());
				best_value = Math.min(best_value, minimax(new_state, true));
			}
			return best_value;
		}
	}
	void find_optimal_move () {
		Node initial = new Node();
		initial.set_state(initial_state);
		LinkedList<String> lMoves = get_moves(initial, next_move);
		Iterator<String> lMovesIterator = lMoves.iterator();
		int best_value = Integer.MIN_VALUE;
		String best_move = "";
		while(lMovesIterator.hasNext()) {
			Node new_state = new Node();
			num_of_nodes++;
			new_state.set_state(lMovesIterator.next());
			int val = minimax(new_state, false);
			if(val>best_value){
				best_move = new_state.get_state();
				best_value = val;
			}
		}
		for(int i =1; i <= initial_state.length(); i++) {
			if(best_move.charAt(i-1) != initial_state.charAt(i-1)) {
				System.out.println("The Next Optimal Move for " + next_move + " is : " + i);
				System.out.println("Number of generated nodes : " + num_of_nodes);
				break;
			}
			
		}
		
    }
	
}

/*AlphaBetaPruning class uses the AlphaBeta Min Max Pruning algorithm to find the next optimal move*/

class AlphaBetaPruning extends OptimalMove {
	AlphaBetaPruning(String state) { super(state); }
	int alphaBetaPruning(Node node, int alpha, int beta, Boolean maximizing_player) {
		if(isTerminal(node)) {
			//System.out.println("trminal " + node.get_state());
			return utilityValue(node);
		}
		if(maximizing_player){
			int best_value = Integer.MIN_VALUE;
			LinkedList<String> lMoves = get_moves(node, next_move);
			Iterator<String> lMovesIterator = lMoves.iterator();
			while(lMovesIterator.hasNext()) {
				Node new_state = new Node();
				num_of_nodes++;
				new_state.set_state(lMovesIterator.next());
				//System.out.println("maximizing " + new_state.get_state());
				best_value = Math.max(best_value, alphaBetaPruning(new_state, alpha, beta, false));
				//This step of the algorithm is changed as per the requirements pdf
				if(best_value >= beta) return Integer.MAX_VALUE;
				alpha = Math.max(alpha, best_value);
			}
			return best_value;
		}else{
			int best_value = Integer.MAX_VALUE;
			char opponent = next_move == 'X' ?'O': 'X';
			LinkedList<String> lMoves = get_moves(node, opponent);
			Iterator<String> lMovesIterator = lMoves.iterator();
			while(lMovesIterator.hasNext()) {
				Node new_state = new Node();
				num_of_nodes++;
				new_state.set_state(lMovesIterator.next());
				//System.out.println("minimizing " + new_state.get_state());
				best_value = Math.min(best_value, alphaBetaPruning(new_state, alpha, beta, true));
				//This step of the algorithm is changed as per the requirements pdf
				if(best_value <= alpha) return Integer.MIN_VALUE;
				beta = Math.min(beta, best_value);
			}
			return best_value;
		}
	}
	void find_optimal_move () {
		Node initial = new Node();
		initial.set_state(initial_state);
		LinkedList<String> lMoves = get_moves(initial, next_move);
		Iterator<String> lMovesIterator = lMoves.iterator();
		int best_value = Integer.MIN_VALUE;
		String best_move = "";
		while(lMovesIterator.hasNext()) {
			Node new_state = new Node();
			num_of_nodes++;
			new_state.set_state(lMovesIterator.next());
			int val = alphaBetaPruning(new_state, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			if(val>best_value){
				best_move = new_state.get_state();
				best_value = val;
			}
		}
		for(int i =1; i <= initial_state.length(); i++) {
			if(best_move.charAt(i-1) != initial_state.charAt(i-1)) {
				System.out.println("The Next Optimal Move for " + next_move + " is : " + i);
				System.out.println("Number of generated nodes : " + num_of_nodes);
				break;
			}
	    }
		
    }
	
}
