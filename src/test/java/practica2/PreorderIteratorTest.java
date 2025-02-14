package practica2;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

import practicas.Position;
import practicas.practica2.iterators.PreorderIterator;
import practicas.practica2.narytree.LinkedTree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreorderIteratorTest {

    private LinkedTree<Integer> tree;
    private Position<Integer>[] pos;
    PreorderIterator<Integer> iterator;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new LinkedTree<>();
        pos = (Position<Integer>[]) new Position[12];
        pos[0] = tree.addRoot(0);
        pos[1] = tree.add(1, pos[0]);
        pos[2] = tree.add(2, pos[0]);
        pos[3] = tree.add(3, pos[0]);
        pos[4] = tree.add(4, pos[0]);
        pos[5] = tree.add(5, pos[1]);
        pos[6] = tree.add(6, pos[2]);
        pos[7] = tree.add(7, pos[2]);
        pos[8] = tree.add(8, pos[3]);
        pos[9] = tree.add(9, pos[7]);
        pos[10] = tree.add(10, pos[7]);
        pos[11] = tree.add(11, pos[7]);
    }


    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Not required

    }

    @org.junit.jupiter.api.Test
    void iteratorTest() {
        iterator = new PreorderIterator<Integer>(tree);
        int[] expected = {0,1,5,2,6,7,9,10,11,3,8,4};
        int element, i=0;
        while(iterator.hasNext()){
            element = iterator.next().getElement();
            assertEquals(expected[i], element);
            i++;
        }

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @org.junit.jupiter.api.Test
    void iteratorTest_startPosition() {
        iterator = new PreorderIterator<Integer>(tree, pos[2]);
        int[] expected = {2,6,7,9,10,11};
        int element, i=0;
        while(iterator.hasNext()){
            element = iterator.next().getElement();
            assertEquals(expected[i], element);
            i++;
        }

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }


    @org.junit.jupiter.api.Test
    void iteratorPredicateElement() {
        Predicate<Position<Integer>> isPair = p -> p.getElement() % 2 == 0;
        iterator = new PreorderIterator<>(tree, pos[0], isPair);
        int[] expected = {0,2,6,10,8,4};
        int element, i=0;
        while(iterator.hasNext()){
            element = iterator.next().getElement();
            assertEquals(expected[i], element);
            i++;
        }

        assertThrows(NoSuchElementException.class, () -> iterator.next());

    }

    @org.junit.jupiter.api.Test
    void iteratorPredicatePosition() {
        Predicate<Position<Integer>> isLeaf = p -> tree.isLeaf(p);
        iterator = new PreorderIterator<>(tree, pos[0], isLeaf);
        int[] expected = {5, 6, 9, 10, 11, 8, 4};
        int element, i=0;
        while(iterator.hasNext()){
            Position<Integer> next = iterator.next();
            element = next.getElement();
            assertEquals(expected[i], element);
            i++;
        }

        assertThrows(NoSuchElementException.class, () -> iterator.next());

    }

}