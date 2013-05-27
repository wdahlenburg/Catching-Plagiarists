/**
 * This HashTable object represents a hash table ADT implemented as an array of doubly linked lists
 * 
 * @author  
 * @version 
 */
public class HashTable<K,V>{
    public int size;
    private int capacity;
    private KeyedLinkedList<K,V> [] table;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity)
    {
        table = (KeyedLinkedList<K,V>[]) new KeyedLinkedList[capacity];
        size = 0;        // set the remaining instance variables
        // instantiate a new KeyedLinkedList<K,V> list in every element of table
        for(int i = 0; i < table.length; i++)
            table[i] = new KeyedLinkedList<K,V>();
        this.capacity = capacity;
    }

    // returns the number of keys in this hashtable
    public int size()
    {
        return size;  // complete this method
    }

    // clears this hashtable so that it contains no keys
    @SuppressWarnings("unchecked")
    public void clear()
    {
        table = (KeyedLinkedList<K,V>[]) new KeyedLinkedList[capacity];
        size = 0;
        for(int i = 0; i < table.length; i++)
            table[i] = new KeyedLinkedList<K,V>();
        this.capacity = capacity;
        // complete this method
        // it is essentially identical to the constructor
    }

    // returns the hashtable index for a given key
    public int hashIndex(K key)
    {
        // the line below insures a positive integer by eliminating the sign bit
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    // if key found, update value and return false (nothing added), 
    // otherwise grow table and return true
    public boolean put(K key, V value)
    {
        KeyedLinkedList<K,V> list = table[hashIndex(key)];
        for(int i = 0; i < table.length; i++){
            if(table[i].equals(list)){
                //int a = hashIndex(key);
                table[i].put(key,value);
                size++;
                return false;
            }
        }
        table[hashIndex(key)].put(key,value);
        //capacity++;
        return true;
    }

    // search for key, return associated value
    // if key not found, return null
    public V get(K key)
    {
        for(int i = 0; i <table.length; i++){
            if(i == (hashIndex(key))){
                return (V) table[i].get(key);
            }
        }
        return null;    // complete this method
    }

    // removes the element at the specified key location in this table
    // return true if remove was successful, false if key not found
    public boolean remove(K key)
    {
        for(int i = 0; i <table.length; i++){
            if(i == (hashIndex(key))){
                if(table[i].remove(key)){
                    size--;
                    return true;
                }
                else
                    return false;
            }
        }
        return false;   // complete this method
    }
}
