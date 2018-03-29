package pack1;

import java.util.Iterator;

import pack1.Dictionary.Entry;

public class SortedArrayDictionary<K extends Comparable<?super K>,V> implements Dictionary<K,V> {
	int size;
	Entry<K,V>[] data;
	V ret = null;
	int ac=16;
	public SortedArrayDictionary() {
		size = 0;
		data = new Entry[ac];
	}


	@Override
	public V insert(K key, V value) {
		int  h  = searchKey(key);
		if(h > -1) {
			V r = data[h].getValue();
			data[h].setValue(value);
			return r;
		}
		if(size()+1 == ac) {
			ensureCapacity(2*size);
		}
		int j = size() -1;
		while(j >= 0 && key.compareTo(data[j].getKey())< 0) {
			data[j+1] = data[j];
			j--;
		}
		data[j+1]= new Entry<K,V>(key,value);
		size++;
		return null;
	}

	@Override
	public V search(K key) {
		int h = searchKey(key);
		if(h > -1) {
			return data[h].getValue();
		} else {
			return null;
		}
	}
	
	public int searchKey(K key) {
		int li = 0;
		int re = size-1;
		while (re>=li) {
			int m = (li +re)/2;
			if(key.compareTo(data[m].getKey())< 0) {
				re = m -1;
			} else if (key.compareTo(data[m].getKey()) > 0) {
				li = m + 1;
			} else {
				return m;
			}
		}
		return -1;
	}

	@Override
	public V remove(K key) {
		int h = searchKey(key);
		if(h > -1) {
			V removedE = data[h].getValue();
			for(int i = h; i < size()-1; i++) {
				Entry<K,V> old = data[i+1];
				data[i] = data[i+1]; 
			}
			data[--size] = null;
			return removedE;
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator iterator() {
		return new Iterator<Entry<K,V>>() {
			int current = 0;
			@Override
			public boolean hasNext() {
				return data[current] != null;
			}
			@Override
			public Entry<K,V> next() {
				return data[current++];
			}
		};
	}
	
	private void ensureCapacity(int nc) {
		if(nc < size()) {
			return;
		}
			ac=nc;
			Entry[] old = data;
			data = new Entry[nc];
			System.arraycopy(old, 0, data, 0, size());
		}
	}
	
