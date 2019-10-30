package practica2.iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

import practica2.Position;
import practica2.Tree;

public class PreorderIterator<E> implements Iterator<Position<E>> {

    private final Deque<Position<E>> nodeStack;
    private final Tree<E> tree;
    private Predicate<Position<E>> predicate;

    public PreorderIterator(Tree<E> tree) {
        this.nodeStack = new LinkedList<>();
        this.tree = tree;
        predicate = a -> true;
        if(!tree.isEmpty())
            this.nodeStack.add(tree.root());
    }

    public PreorderIterator(Tree<E> tree, Position<E> start) {
        this.nodeStack = new  LinkedList<>();
        this.tree = tree;
        this.nodeStack.add(start);
        predicate = a -> true;
    }

    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        this.nodeStack = new  LinkedList<>();
        this.tree = tree;
        this.nodeStack.add(start);
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    @Override
    public Position<E> next() {

        Position<E> aux = nodeStack.pop();

        Deque<Position<E>> children = new LinkedList<>();

        for(Position<E> node : tree.children(aux))
            children.push(node);

        for(Position<E> node : children)
            nodeStack.push(node);

        if(!predicate.test(aux))
            if(hasNext())
                aux = next();
            else
                return null;

        return aux;
    }
}