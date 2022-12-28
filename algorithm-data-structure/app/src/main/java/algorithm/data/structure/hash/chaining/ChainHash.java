package algorithm.data.structure.hash.chaining;

import java.util.ArrayList;
import java.util.List;

public class ChainHash<K, V> {
    private List<Node<K, V>> nodes;

    public ChainHash(int capacity){
        try{
            this.nodes = new ArrayList<>();
        }catch(OutOfMemoryError e){
            System.err.println(e.getMessage());
        }
    }

    public int hashValue(Object key){
        return key.hashCode() % nodes.size();
    }
}
