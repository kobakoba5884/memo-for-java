package algorithm.data.structure.hash.chaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> nextNode;

    public int hashCode(){
        return key.hashCode();
    }
}
