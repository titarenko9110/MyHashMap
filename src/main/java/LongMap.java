import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by MyMac on 26.10.16.
 */
public class LongMap implements TestMap {

    private LongMap.Entry[] table;
    private int capacity = 4;
    private int size = 0;

    static class Entry {
        Long key;
        Object value;
        LongMap.Entry next;

        public Entry(Long key, Object value, LongMap.Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public LongMap() {
        table = new LongMap.Entry[capacity];
    }

    @Override
    public Object put(long Key, Object data) {
        Long newKey = Key;
        int hash = hash(newKey);
        LongMap.Entry newEntry = new LongMap.Entry(newKey, data, null);
        if (table[hash] == null) {
            table[hash] = newEntry;
            ++size;
            return newEntry.value;
        } else {
            LongMap.Entry previous = null;
            LongMap.Entry current = table[hash];
            while (current != null) {
                if (current.key.equals(newKey)) {
                    if (previous == null) {
                        newEntry.next = current.next;
                        table[hash] = newEntry;
                        return newEntry.value;
                    } else {
                        newEntry.next = current.next;
                        previous.next = newEntry;
                        return newEntry.value;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
            ++size;
            return newEntry.value;
        }
    }

    @Override
    public Object get(long key) {
        Long newKey = key;
        int hash = hash(newKey);
        if (table[hash] == null) {
            return null;
        } else {
            LongMap.Entry temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(newKey))
                    return temp.value;
                temp = temp.next;
            }
            return null;
        }
    }

    @Override
    public Object remove(long key) {
        Long deleteKey = key;
        int hash = hash(deleteKey);

        if (table[hash] == null) {
            return false;
        } else {
            LongMap.Entry previous = null;
            LongMap.Entry current = table[hash];

            while (current != null) {
                if (current.key.equals(deleteKey)) {
                    if (previous == null) {
                        table[hash] = table[hash].next;
                        --size;
                        return current.value;
                    } else {
                        previous.next = current.next;
                        --size;
                        return current.value;
                    }
                }
                previous = current;
                current = current.next;
            }
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(long key) {
        for (int i = 0; i < table.length; i++) {
            LongMap.Entry current = table[i];
            while (current != null) {
                if (current.key.equals(key)) return true;
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < table.length; i++) {
            LongMap.Entry current = table[i];
            while (current != null) {
                if (current.value.equals(value)) return true;
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public long[] keys() {
        long[] a = new long[size];
        int b = 0;
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                LongMap.Entry current = table[i];
                while (current != null) {
                    a[b] = current.key;
                    b++;
                    current = current.next;
                }
            }
        }
        return a;
    }

    @Override
    public Object[] values() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            LongMap.Entry current = table[i];
            while (current != null) {
                objects.add(current.value);
                current = current.next;
            }
        }
        return objects.toArray();
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void clear() {
        table = new LongMap.Entry[capacity];
        size = 0;
    }

    private int hash(Long key) {
        return Math.abs(key.hashCode()) % capacity;
    }
}
