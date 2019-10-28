package practica2.iterators;

import java.util.Iterator;
import java.util.function.Predicate;

import practica2.Position;
import practica2.tree.Tree;


//TODO: Practica 2 Ejercicio 3
public class PreorderIterator<E> implements Iterator<Position<E>> {

    public PreorderIterator(Tree<E> tree) {
        throw new RuntimeException("Not yet implemented");
    }

    public PreorderIterator(Tree<E> tree, Position<E> start) {
        throw new RuntimeException("Not yet implemented");
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean hasNext() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> next() {
        throw new RuntimeException("Not yet implemented");
    }

}