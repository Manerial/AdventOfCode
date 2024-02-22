package aoc.common_objects;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class RoundDeque<T> extends ArrayDeque<T> {
    public void rotate(long rotation) {
        rotation = rotation % this.size();
        if (rotation > 0) {
            for (int i = 0; i < rotation; i++) {
                this.addLast(this.removeFirst());
            }
        } else {
            for (int i = 0; i > rotation; i--) {
                this.addFirst(this.removeLast());
            }
        }
    }

    public void goTo(T item) {
        List<T> list = new ArrayList<>(this);
        if (!list.contains(item)) {
            throw new IllegalArgumentException("Item not found");
        }
        int index = list.indexOf(item);
        if (index > list.size() / 2) {
            index = -(list.size() - index);
        }
        this.rotate(index);
    }
}
