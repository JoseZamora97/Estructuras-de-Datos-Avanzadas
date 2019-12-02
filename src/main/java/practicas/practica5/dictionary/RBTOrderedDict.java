package practicas.practica5.dictionary;

import java.util.Comparator;

import practicas.practica5.Entry;
import practicas.practica5.binarysearchtree.BinarySearchTree;
import practicas.practica5.binarysearchtree.RBTree;

public class RBTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {
    public RBTOrderedDict() {
        super();
    }

    public RBTOrderedDict(Comparator<K> keyComparator) {
        super(keyComparator);
    }

    @Override
    protected BinarySearchTree<Entry<K,V>> createTree() {
        return new RBTree<>();
    }
}
