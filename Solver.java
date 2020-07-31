import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Solver {
	private MinPQ<SearchNode>pq;
	private boolean solve;
	
	public Solver(Board initial) {
		if(initial == null) {
			throw new IllegalArgumentException();
		}
		pq = new MinPQ<SearchNode>(100);
		SearchNode start = new SearchNode(initial,null);
		start.moves = 0;
		pq.insert(start);
		int added;
		solve = true;
		SearchNode node;
		while(!solved(pq.min())) {
			added = 0;
			SearchNode previous = (SearchNode) pq.min();
			ArrayList<Board> next = (ArrayList<Board>) previous.board.neighbors();
			pq.delMin();
			for(int i = 0; i < next.size(); i++) {
				node = new SearchNode(next.get(i),previous);
				if(!contains(node) && node!= previous.prev) {
					pq.insert(node);
					node.prev = previous;
					added++;
				}
			}	
			if(added == 0) {
				solve = false;
				break;
			}
		}
	}
	
	private class SearchNode implements Comparable<SearchNode>{
		private int moves;
		private Board board;
		private SearchNode prev;
		
		public SearchNode(Board b, SearchNode prev) {
			board = b;
			this.prev = prev;
			if(this.prev == null) {
				this.moves = 1;
			}
			else {
				this.moves = prev.moves + 1;
			}
		}
		
		@Override
		public int compareTo(SearchNode o) {
//			int d1 = board.hamming() + moves;
//			int d2 = o.board.hamming() + o.moves;
			int d1 = board.manhattan() + moves;
			int d2 = o.board.manhattan() + o.moves;
			return d1-d2;
		}
		
	}
	
	private boolean contains(SearchNode node) {
		if(pq.isEmpty()) {
			return false;
		}
		for(SearchNode s : pq) {
			if(s!= null && s.board.equals(node.board)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean solved(Comparable c) {
		return ((SearchNode)c).board.isGoal();
	}
	
	public boolean isSolvable() {
		return solve;
	}
	
	public int moves() {
		if(isSolvable()) {
			return pq.min().moves;
		}
		return -1;
	}
	
	public Iterable<Board> solution(){
		ArrayList<Board> list = new ArrayList<Board>();
		SearchNode node = pq.min();
		for(int i = 0; i < moves(); i++) {
			list.add(node.board);
			node = node.prev;
		}
		reverse(list);
		return list;
	}
	
	private void reverse(ArrayList<Board> list) {
		Board[] copy = new Board[list.size()];
		for(int i = 0; i < list.size(); i++) {
			copy[i] = list.get(list.size()-i-1);
		}
		for(int i = 0; i < list.size(); i++) {
			list.set(i,copy[i]);
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("Enter input");
		BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
	    StringTokenizer st = new StringTokenizer(f.readLine());
	    int n = Integer.parseInt(st.nextToken());
	    int[][] tiles = new int[n][n];
	    for(int i = 0; i < n; i++) {
	    	st = new StringTokenizer(f.readLine());
	    	for(int j = 0; j < n; j++) {
	    		tiles[i][j] = Integer.parseInt(st.nextToken());
	    	}
	    }
	    Board initial = new Board(tiles);
	    Solver solver = new Solver(initial);
	    if(!solver.isSolvable()) {
	    	System.out.println("No solution possible");
	    }
	    else {
	    	System.out.println("Minimum number of moves = " + solver.moves());
	    	for(Board b : solver.solution()) {
	    		System.out.println(b);
	    	}
	    }
		
	}


}
