/**
 * Created by MyMac on 26.10.16.
 */
public interface TestMap<V> {

    V put(long key, V value);
    V get(long key);
    V remove(long key);

    boolean isEmpty();
    boolean containsKey(long key);
    boolean containsValue(V value);

    long[] keys();
    V[] values();

    long size();
    void clear();
}

