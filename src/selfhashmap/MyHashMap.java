package selfhashmap;

import com.sun.scenario.effect.impl.prism.PrImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Entry<K, V> implements MyMap.Entry<K, V> {
    K k;
    V v;
    Entry<K, V> next;
    public Entry(K k, V v, Entry next) {
        this.k = k;
        this.v = v;
        this.next = next;
    }

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }
}

public class MyHashMap<K, V> {

    private static int defaultLength = 16;
    private static double defaultloader = 0.75;
    private Entry<K, V>[] table = null;
    private int size = 0;

    public MyHashMap(int length, double loader) {
        defaultLength = length; loader = defaultloader;
        table = new Entry[defaultLength];
    }

    public MyHashMap() {
        this(defaultLength, defaultloader);
    }

    private int getKey(K k) {
        int m = defaultLength;
        int index = k.hashCode() % m;
        return index >= 0 ? index : -index;
    }

    public void put(K k, V v) {
        if (size >= defaultLength * defaultloader) expand();
        int index = getKey(k);
        Entry<K, V> entry = table[index];

        if (entry == null) {
            table[index] = new Entry(k, v, null);
            size++;
        }
        else {
            table[index] = new Entry(k, v, entry);
            size++;
        }
    }

    private void rehash(Entry<K, V>[] newTable) {
        List<Entry<K, V>> list = new ArrayList<Entry<K, V>>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Entry<K, V> z = table[i];
            while (z != null) {
                list.add(z); z = z.next;
            }
        }
        table = newTable;
        defaultLength = 2 * defaultLength;
        size = 0;
        for (Entry<K, V> entry : list) {
            put(entry.getKey(), entry.getValue());
        }
        list.clear();
    }

    private void expand() {
        Entry<K, V>[] newTable = new Entry[2 * defaultLength];
        rehash(newTable);
    }

    public V findEntryByNext(K k, Entry<K, V> entry) {
        if (k == entry.getKey() || k.equals(entry.getKey())) return entry.getValue();
        else {
            if (entry.next != null) return findEntryByNext(k, entry.next);
        }
        return entry.getValue();
    }

    public V get(K k) {
        int index = getKey(k);
        if (table[index] == null) return null;
        return findEntryByNext(k, table[index]);
    }

    public int size() { return size; }
}