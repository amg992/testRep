package pack1;

import java.awt.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import pack1.Dictionary.Entry;

public class HashDictionary<K extends Comparable<? super K>,V> implements Dictionary<K,V>{
	int elements = 0;
	int m;
	LinkedList<Entry<K,V>>[] alist;
	
	public HashDictionary(int i) {
		alist = new LinkedList[i];
		for(int h = 0;h < alist.length; h++) {
			alist[h]=new LinkedList<Entry<K,V>>();
		}
		m = i;
	}
	
	@Override
	public V insert(K key, V value) {
		int adr = key.hashCode();
		if(adr < 0) {
			adr = -adr;
		}
		adr = adr % m;
		int h = searchKey(key);
		if (elements / alist.length > 1) {
			ensureCapacity(2*m);
		}
		if(h > -1) {
			for(Entry<K,V> entry : alist[adr]) {
				if (entry.getKey().equals(key)) {
					V old = entry.getValue();
					entry.setValue(value);
					return old;
				}
			}
		} else {
			alist[adr].addLast(new Entry<K,V>(key,value));
			elements++;
			return null;
		}
		return null;
	}
	
	public int searchKey(K key) {
		int adr = key.hashCode();
		if(adr < 0) {
			adr = -adr;
		}
		adr = adr % m;
		for (Entry<K,V> entry : alist[adr]) {
			if (entry.getKey().equals(key)) {
				return 2;
			}
		}
		return -1;
	}

	@Override
	public V remove(K key) {
		int adr = key.hashCode();
		if(adr < 0) {
			adr = -adr;
		}
		adr = adr % m;
		for (Entry<K,V> entry : alist[adr]) {
			if (entry.getKey().equals(key)) {
				V old = entry.getValue();
				alist[adr].remove(entry);
				elements--;
				return old;
			}
		}
		return null;
	}

	@Override
	public int size() {
		return elements;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K,V>>() {
			int current = 0;
			int all = alist.length;
			int i = 0;
			int elz = 0;
			int el = 0;

			@Override
			public boolean hasNext() {
				if (alist[current].get(el).getKey() != null) {
					el++;
					return true;
				}
				el = 0;
				current++;
				while(current < all) {
					if (alist[current].get(el).getKey() != null) {
						el++;
						return true;
					}
				}
				return false;
			}
			@Override
			public Entry<K,V> next() {
				elz = alist[current].size();
				if (el <= elz) {
					return alist[current].get(el++);
				}
				el = 0;
				return alist[++current].get(el);
			}
			
		};
	}
	private void ensureCapacity(int nc) {
		if(nc < size()) {
			return;
		}
		while(isPrime(nc) != true) {
			nc++;
			isPrime(nc);
		}
		LinkedList<Entry<K, V>>[] old = alist;
		m = nc;
		alist = new LinkedList[nc];
		for(int h = 0;h < alist.length; h++) {
			alist[h]=new LinkedList<Entry<K,V>>();
		}
		for (LinkedList<Entry<K, V>> entry : old) {
			for (Entry<K,V> entry2 : entry) {
			insert(entry2.getKey(), entry2.getValue());
			}
		}
	}
	
	public boolean isPrime(int nc) {
		if (nc <= 1) {
			return false;
		} else if (nc <= 3) {
			return true;
		} else if (nc % 2 == 0 || nc % 3 == 0) {
			return false;
		}
		int i = 5;
		while(i*i <= nc) {
			if (nc % i == 0 || nc % (i+2)==0){
				return false;
			}
			i = i + 6;
		}
		return true;
		
	}

	@Override
	public V search(K key) {
		int adr = key.hashCode();
		if(adr < 0) {
			adr = -adr;
		}
		adr = adr % m;
		for(Entry<K,V> entry : alist[adr]) {
			if(entry.getKey().equals(key)) {
				V word = entry.getValue();
				return word;
			}
		}
		return null;
	}
}
