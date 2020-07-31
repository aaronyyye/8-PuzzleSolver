import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key>{

	private Key[] pq;
	private int N;
	
	public MinPQ(int capacity) {
		pq = (Key[]) new Comparable[capacity+1];
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public void insert(Key key) {
		pq[++N] = key;
		swim(N);
	}
	
	public Key delMin() {
		Key val = pq[1];
		swap(1,N--);
		sink(1);
		pq[N+1] = null;
		return val;
	}
	
	public int size() {
		return N;
	}
	
	public Key min() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return pq[1];
	}
	
	private void swim(int k) {
		while(k > 1 && !less(k/2,k)) {
			swap(k,k/2);
			k = k/2;
		}
	}
	
	private void sink(int k) {
		while(2*k <= N) {
			int j = 2*k;
			if(j < N && !less(j, j+1)) {
				j++;
			}
			if(less(k,j)) {
				break;
			}
			swap(k,j);
			k = j;
		}
	}
	
	private boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}
	
	private void swap(int i, int j) {
		Key temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
	}

	@Override
	public Iterator<Key> iterator() {
		// TODO Auto-generated method stub
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator{
		private int n;
		
		private ArrayIterator() {
			n = 0;
		}
		
		public boolean hasNext() {
			return n < N;
		}

		@Override
		public Object next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			else {
				return pq[n++];
			}
		}
	}
}
