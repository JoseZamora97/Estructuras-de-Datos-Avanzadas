package practicas.practica5.dictionary;

import java.util.Comparator;

import practicas.practica5.Entry;
import practicas.practica5.binarysearchtree.LinkedBinarySearchTree;

public class BSTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {
    public BSTOrderedDict() {
        super();
    }

    public BSTOrderedDict(Comparator<K> keyComparator) {
        super(keyComparator);
    }

    protected LinkedBinarySearchTree<Entry<K,V>> createTree (){
        return new LinkedBinarySearchTree<Entry<K,V>>();
    }
}
