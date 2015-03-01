package com.epam.mymap;

import java.util.Comparator;
import java.util.Iterator;

import com.epam.mylist.MyList;
import com.epam.mylist.mylinkedlist.MyLinkedList;

public class MyTreeMap<K, V> implements MyMap<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private SimpleEntry<K, V> root;
    private Comparator<K> comparator = null;

    public MyTreeMap() {
        this(null);
    }

    public MyTreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<SimpleEntry<K, V>> it = entryIterator();
        while (it.hasNext()) {
            if (it.next().getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        return get(root, (K) key);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public V put(K key, V value) {
        V prev = get(key);
        root = put(root, key, value);
        root.color = BLACK;
        return prev;
    }

    @Override
    public V remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        V prev = get(key);
        root = delete(root, (K) key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
        return prev;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Iterator<SimpleEntry<K, V>> entryIterator() {
        return entries().iterator();
    }

    private static class SimpleEntry<K, V> implements MyMap.Entry<K, V> {

        private K key; // key
        private V value; // associated data
        private SimpleEntry<K, V> left, right; // links to left and right
                                               // subtrees
        private int subtreeCount; // subtree count
        private boolean color; // color of parent link

        public SimpleEntry(K key, V value, int count, boolean color) {
            super();
            this.key = key;
            this.value = value;
            this.subtreeCount = count;
            this.color = color;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public Object setValue(Object newValue) {
            V temp = this.value;
            this.value = (V) newValue;
            return temp;
        }

    }

    private boolean isRed(SimpleEntry<K, V> x) {
        if (x == null) {
            return false;
        }
        return (x.color == RED);
    }

    private int size(SimpleEntry<K, V> x) {
        if (x == null) {
            return 0;
        }
        return x.subtreeCount;
    }

    private V get(SimpleEntry<K, V> entry, K key) {
        while (entry != null) {
            int cmp = compareKeys(key, entry.getKey());
            if (cmp < 0) {
                entry = entry.left;
            } else if (cmp > 0) {
                entry = entry.right;
            } else {
                return entry.getValue();
            }
        }
        return null;
    }

    private SimpleEntry<K, V> put(SimpleEntry<K, V> h, K key, V val) {
        if (h == null) {
            return new SimpleEntry<K, V>(key, val, 1, RED);
        }

        int cmp = compareKeys(key, h.getKey());
        if (cmp < 0) {
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.setValue(val);
        }

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.subtreeCount = size(h.left) + size(h.right) + 1;

        return h;
    }

    private SimpleEntry<K, V> delete(SimpleEntry<K, V> h, K key) {
        if (compareKeys(key, h.getKey()) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (compareKeys(key, h.getKey()) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (compareKeys(key, h.key) == 0) {
                SimpleEntry<K, V> x = min(h.right);
                h.key = x.key;
                h.setValue(x.getValue());
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    private SimpleEntry<K, V> deleteMin(SimpleEntry<K, V> h) {
        if (h.left == null) {
            return null;
        }

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        return balance(h);
    }

    private SimpleEntry<K, V> rotateRight(SimpleEntry<K, V> h) {
        SimpleEntry<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.subtreeCount = h.subtreeCount;
        h.subtreeCount = size(h.left) + size(h.right) + 1;
        return x;
    }

    private SimpleEntry<K, V> rotateLeft(SimpleEntry<K, V> h) {
        SimpleEntry<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.subtreeCount = h.subtreeCount;
        h.subtreeCount = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(SimpleEntry<K, V> h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private SimpleEntry<K, V> moveRedLeft(SimpleEntry<K, V> h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private SimpleEntry<K, V> moveRedRight(SimpleEntry<K, V> h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private SimpleEntry<K, V> balance(SimpleEntry<K, V> h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.subtreeCount = size(h.left) + size(h.right) + 1;
        return h;
    }

    private int compareKeys(K first, K second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        } else {
            return ((Comparable<K>) first).compareTo(second);
        }

    }

    private K min() {
        if (isEmpty()) {
            return null;
        }
        return min(root).getKey();
    }

    private SimpleEntry<K, V> min(SimpleEntry<K, V> x) {
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    private K max() {
        if (isEmpty()) {
            return null;
        }
        return max(root).getKey();
    }

    private SimpleEntry<K, V> max(SimpleEntry<K, V> x) {
        if (x.right == null) {
            return x;
        } else {
            return max(x.right);
        }
    }

    private Iterable<SimpleEntry<K, V>> entries() {
        return entries(min(), max());
    }

    private Iterable<SimpleEntry<K, V>> entries(K lo, K hi) {
        MyList<SimpleEntry<K, V>> queue = new MyLinkedList<SimpleEntry<K, V>>();
        entries(root, queue, lo, hi);
        return queue;
    }

    private void entries(SimpleEntry<K, V> x, MyList<SimpleEntry<K, V>> queue,
            K lo, K hi) {
        if (x == null) {
            return;
        }
        int cmplo = compareKeys(lo, x.key);
        int cmphi = compareKeys(hi, x.key);
        if (cmplo < 0) {
            entries(x.left, queue, lo, hi);
        }
        queue.add(x);
        if (cmphi > 0) {
            entries(x.right, queue, lo, hi);
        }
    }
}
