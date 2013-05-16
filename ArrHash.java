import java.util.*;
/**
 * This ArrHash object . . .
 * 
 * @author  
 * @version 
 */
public class ArrHash
{
    ArrayList<Integer> a;
    HashTable hash;
    public ArrHash(ArrayList<Integer> arr, HashTable h){
        a = arr;
        hash = h;
    }
    
    public ArrayList<Integer> getArrayList(){
        return a;
    }
    
    public HashTable getHashTable(){
        return hash;
    }
}
