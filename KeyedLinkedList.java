
/**
 * This KeyedLinkedList object represents a Keyed List ADT implemented as
 * a LinkedList.
 * 
 * @Wyatt Dahlenburg 
 * @5/29/13
 */
public class KeyedLinkedList<K,V> {
    private KeyNode first;
    private int size;

    // a private inner class that represents a doubly 
    // linked-list node that contains both a key and a value
    private class KeyNode
    {
        K key;
        V value;
        KeyNode previous;
        KeyNode next;

        public KeyNode(K key, V value, KeyNode previous, KeyNode next)
        {
            this.key = key;
            this.value = value;
            this.previous = previous;
            this.next = next;
            if(this.previous != null)
                this.previous.next = this;
            if(this.next != null)
                this.next.previous = this;
        }
    }

    // returns the logical size of this list
    public int size()
    {
        return size;
    }

    // if key found, update value and return false (nothing added), 
    // otherwise grow list and return true
    public boolean put(K key, V value)
    {
        KeyNode p = first;
        while(p != null){
            if(p.key.equals(key)){
                p.value = value;
                return false;
            }
            p = p.next;
        }
        KeyNode newKey = new KeyNode(key,value,null,first);
        first = newKey;
        size++;
        return true;
    }

    // search for key, return associated value
    // if key not found, return null
    public V get(K key)
    {
        KeyNode p = first;
        while(p != null){
            if(p.key.equals(key)){
                return p.value;
            }
            p = p.next;
        }

        return null; 
    }

    // removes the element at the specified key location in this list
    // return true if remove was successful, false if key not found
    public boolean remove(K key)
    {
        KeyNode p = first;
        while(p != null){
            if(p.key.equals(key)){
                if(p == first)
                    first = first.next;
                else{
                    if(p.previous != null)
                        p.previous.next = p.next;
                    if(p.next != null)
                        p.next.previous = p.previous;
                }
                size--;
                return true;
            }
            p = p.next;
        }
        return false; 
    }
}
