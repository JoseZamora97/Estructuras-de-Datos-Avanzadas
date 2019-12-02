package practicas.practica5.dictionary;

import java.util.Comparator;

import practicas.practica5.Entry;
import practicas.practica5.binarysearchtree.AVLTree;
import practicas.practica5.binarysearchtree.BinarySearchTree;

public class AVLOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {

    public AVLOrderedDict() {
        super();
    }

    public AVLOrderedDict(Comparator<K> keyComparator) {
        super(keyComparator);
    }


    @Override
    protected BinarySearchTree<Entry<K,V>> createTree() {
        return new AVLTree<>();
    }

}
