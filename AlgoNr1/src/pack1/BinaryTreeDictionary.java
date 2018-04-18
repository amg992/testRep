package pack1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import pack1.Dictionary.Entry;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
	static private class Node<K, V> {
		int height;
		K key;
		V value;
		Node<K, V> left;
		Node<K, V> right;
		Node<K, V> parent;

		private Node(K k, V v) {
			parent = null;
			height = 0;
			key = k;
			value = v;
			left = null;
			right = null;
		}
	}

	private int getHeight(Node<K, V> p) {
		if (p == null)
			return -1;
		else
			return p.height;
	}

	private int getBalance(Node<K, V> p) {
		if (p == null)
			return 0;
		else
			return getHeight(p.right) - getHeight(p.left);
	}

	Node<K, V> root = null;
	int elements = 0;
	V oldValue;
	int höh = 0;

	@Override
	public V insert(K key, V value) {
		root = insertR(key, value, root);
		if(root != null) {
			root.parent=null;
		}
		return oldValue;
	}

	private Node<K, V> insertR(K key, V value, Node<K, V> p) {
		if (p == null) {
			p = new Node<K, V>(key, value);
			oldValue = null;
		} else if (key.compareTo(p.key) < 0) {
			p.left = insertR(key, value, p.left);
			if (p.left != null)
				p.left.parent = p;
		} else if (key.compareTo(p.key) > 0) {
			p.right = insertR(key, value, p.right);
			if (p.right != null)
				p.right.parent = p;
		} else {
			oldValue = p.value;
			p.value = value;
		}
		p = balance(p);
		return p;
	}

	private Node<K, V> rotateRight(Node<K, V> p) {
		assert p.left != null;
		Node<K, V> q = p.left;
		p.left = q.right;
		if (p.left != null) {
			p.left.parent = p;
		}
		q.right = p;
		if (q.right != null) {
			q.right.parent = q;
		}
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
		return q;
	}

	private Node<K, V> rotateLeft(Node<K, V> p) {
		assert p.left != null;
		Node<K, V> q = p.right;
		p.right = q.left;
		if (p.right != null) {
			p.right.parent = p;
		}
		q.left = p;
		if (q.left != null) {
			q.left.parent = q;
		}
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
		return q;
	}

	private Node<K, V> rotateLeftRight(Node<K, V> p) {
		assert p.left != null;
		p.left = rotateLeft(p.left);
		return rotateRight(p);
	}

	private Node<K, V> rotateRightLeft(Node<K, V> p) {
		assert p.right != null;
		p.right = rotateRight(p.right);
		return rotateLeft(p);
	}

	private Node<K, V> balance(Node<K, V> p) {
		if (p == null)
			return null;
		p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
		if (getBalance(p) == -2) {
			if (getBalance(p.left) <= 0)
				p = rotateRight(p);
			else
				p = rotateLeftRight(p);
		} else if (getBalance(p) == +2) {
			if (getBalance(p.right) >= 0)
				p = rotateLeft(p);
			else
				p = rotateRightLeft(p);
		}
		return p;
	}

	@Override
	public V search(K key) {
		return searchR(key, root);
	}

	private V searchR(K key, Node<K, V> p) {
		if (p == null) {
			return null;
		} else if (key.compareTo(p.key) < 0)
			return searchR(key, p.left);
		else if (key.compareTo(p.key) > 0)
			return searchR(key, p.right);
		else
			return p.value;
	}

	@Override
	public V remove(K key) {
		root = removeR(key, root);
		return oldValue;
	}

	private Node<K, V> removeR(K key, Node<K, V> p) {
		if (p == null) {
			oldValue = null;
		} else if (key.compareTo(p.key) < 0) {
			p.left = removeR(key, p.left);
		} else if (key.compareTo(p.key) > 0) {
			p.right = removeR(key, p.right);
		} else if (p.left == null || p.right == null) {
			oldValue = p.value;
			p = (p.left != null) ? p.left : p.right;
		} else {
			MinEntry<K, V> min = new MinEntry<K, V>();
			p.right = getRemMinR(p.right, min);
			oldValue = p.value;
			p.key = min.key;
			p.value = min.value;
		}
		p = balance(p);
		return p;
	}

	public void prettyPrint() {
		prettyPrintB(root);
		System.out.println("");
		prettyPrintR(root, 0);
	}

	private void prettyPrintR(Node<K, V> p, int x) {
		if (p != null) {
			System.out.println(p.key);
			if (p.left != null || p.right != null) {
				for (int i = 0; i < x; i++) {
					System.out.print("   ");
				}
				System.out.print("|__");
				if (p.left != null) {
					prettyPrintR(p.left, ++x);
					--x;
				} else {
					System.out.println("#");
				}
				for (int i = 0; i < x; i++) {
					System.out.print("   ");
				}
				System.out.print("|__");
				if (p.right != null) {
					prettyPrintR(p.right, ++x);
					--x;
				} else {
					System.out.println("#");
				}
			}
		}
	}

	private void prettyPrintB(Node<K, V> p) {
		if (p != null) {
			System.out.println(p.key);
			höh = 0;
			prettyorder(p);
		}
	}

	private static class MinEntry<K, V> {
		private K key;
		private V value;
	}

	private Node<K, V> getRemMinR(Node<K, V> p, MinEntry<K, V> min) {
		assert p != null;
		if (p.left == null) {
			min.key = p.key;
			min.value = p.value;
			p = p.right;
		} else
			p.left = getRemMinR(p.left, min);
		return p;
	}

	@Override
	public int size() {
		return elements;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new BinarySearchTreeIterator();
	}

	public Node<K, V> parentOfLeftMostAncestor(Node<K, V> p) {
		assert p != null;
		while (p.parent != null && p.parent.right == p) {
			p = p.parent;
		}
		return p.parent;
	}

	public Node<K, V> leftMostDescendant(Node<K, V> p) {
		assert p != null;
		while (p.left != null) {
			p = p.left;
		}
		return p;
	}

	private void prettyorder(Node<K, V> p) {
		if (p.left != null) {
			p = p.left;
			for(int i = 0; i < höh; i++) {
				System.out.print("   ");
			}
			System.out.print("|__");
			System.out.println(p.key+": "+p.parent.key);
			höh++;
			prettyorder(p);
			p = p.parent;
			höh--;
		} else if (p.right != null) {
			for(int i = 0; i < höh; i++) {
				System.out.print("   ");
			}
			System.out.print("|__");
			System.out.println("#: "+p.key);
		}
		if (p.right != null) {
			p = p.right;
			for(int i = 0; i < höh; i++) {
				System.out.print("   ");
			}
			System.out.print("|__");
			System.out.println(p.key+": "+p.parent.key);
			höh++;
			prettyorder(p);
			p = p.parent;
			höh--;
		} else if (p.left != null) {
			for(int i = 0; i < höh; i++) {
				System.out.print("   ");
			}
			System.out.print("|__");
			System.out.println("#: "+p.key);
		}
	}

	private class BinarySearchTreeIterator implements Iterator<Entry<K, V>> {
		private Node<K, V> current = null;;
		private List<K> list = new LinkedList<>();
		private List<V> list2 = new LinkedList<>();
		private int i = -1;

		public BinarySearchTreeIterator() {
			inOrder(current);
		}

		private void inOrder(Node<K, V> p) {
			if (root != null) {
				p = leftMostDescendant(root);
			}
			while (p != null && !list.contains(p.key)) {
				list.add(p.key);
				list2.add(p.value);
				if (p.right != null) {
					p = leftMostDescendant(p.right);
				} else {
					p = parentOfLeftMostAncestor(p);
				}
			}
		}

		@Override
		public boolean hasNext() {
			return (i < list.size() - 1);
		}

		@Override
		public Entry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			++i;
			return new Entry<K, V>(list.get(i), list2.get(i));
		}

	}
}
