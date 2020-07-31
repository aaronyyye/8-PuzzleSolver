import java.util.ArrayList;

public class Board {
	private int N;
	private int[][] board;
	private int[][] goal;
	
	public Board(int[][] tiles) {
		board = new int[tiles[0].length][tiles.length];
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				board[i][j] = tiles[i][j];
			}
		}
		N = tiles.length;
//		System.out.println("N = " + N);
		goal = new int[N][N];
		int counter = 1;
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles.length; j++) {
				goal[i][j] = counter;
				counter ++;
			}
		}
		goal[N-1][N-1] = 0;
	}
	
	public String toString() {
		String s = N + "\n";
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				s = s + board[i][j] + " ";
			}
			s = s + "\n";
		}
		return s;
	}
	
	public int dimension() {
		return N;
	}
	
	public int hamming() {
		int total = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(board[i][j] != goal[i][j] && board[i][j] != 0) {
					total++;
				}
			}
		}
		return total;
	}
	
	public int manhattan() {
		int dx,dy;
		int total = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(board[i][j] != 0) {
//					dx = N-1-j;
//					dy = N-1-i;
//					total = total + dx + dy;
					dx = Math.abs(j-(board[i][j]-1)%N);
					dy = Math.abs(i-(board[i][j]-1)/N);
					total = total + dx + dy;	
				}
			}
		}
		return total;
		
	}
	
	
	public boolean isGoal() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(board[i][j] != goal[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean equals(Object y) {
		if(y == this) {
			return true;
		}
		if(!(y instanceof Board) || y == null) {
			return false;
		}
		if(((Board)y).board.length != N || ((Board)y).board[0].length != N) {
			return false;
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(((Board)y).board[i][j] != board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Iterable<Board> neighbors(){
		ArrayList<Board> list = new ArrayList<Board>();
		int x,y;
		x = blank()[1];
		y = blank()[0];
		
		if(x-1 >= 0) {
			int[][] b = copy();
			swap(b,y,x,y,x-1);
			list.add(new Board(b));
		}
		if(x+1 < N) {
			int[][] b = copy();
			swap(b,y,x,y,x+1);
			list.add(new Board(b));
		}
		if(y-1 >= 0) {
			int[][] b = copy();
			swap(b,y,x,y-1,x);
			list.add(new Board(b));
		}
		if(y+1 < N) {
			int[][] b = copy();
			swap(b,y,x,y+1,x);
			list.add(new Board(b));
		}
		
		return list;
	}
	
	private void swap(int[][] a, int y, int x, int i, int j) {
		int val = a[y][x];
		a[y][x] = a[i][j];
		a[i][j] = val;
	}
	
	private int[][] copy() {
		int[][] b = new int[N][N];
		for(int i = 0 ; i < N; i++) {
			for(int j = 0; j < N; j++) {
				b[i][j] = board[i][j];
			}
		}
		return b;
	}
	
	private int[] blank() {
		int[] blank = new int[2];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(board[i][j] == 0) {
					blank[0] = i;
					blank[1] = j;
					return blank;
				}
			}
		}
		return null;
	}
			
		
	public Board twin() {
		int[][] b = copy();
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N-1; j++) {
				if(b[i][j] != 0 && b[i][j+1] != 0) {
					swap(b,i,j,i,j+1);
					return new Board(b);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}
	
}
