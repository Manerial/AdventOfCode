package aoc.common_objects;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@EqualsAndHashCode(callSuper = false)
public class SortedLimitedList<E> extends ArrayList<E> {
    private final transient Comparator<? super E> comparator;
    private final int limit;

    public SortedLimitedList(Comparator<? super E> comparator, int limit) {
        this.comparator = comparator;
        this.limit = limit;
    }

    @Override
    public boolean add(E e) {
        int index = Collections.binarySearch(this, e, comparator);
        if (index < 0) {
            index = -(index + 1);
        }
        super.add(index, e);
        if (size() > limit) {
            super.removeLast();
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> e) {
        for (E element : e) {
            add(element);
        }
        return true;
    }
}