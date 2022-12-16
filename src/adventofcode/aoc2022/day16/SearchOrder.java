package adventofcode.aoc2022.day16;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchOrder {
    private List<String> order;

    public SearchOrder() {
        this.order = new ArrayList<>();
    }

    public SearchOrder(SearchOrder s) {
        this.order = new ArrayList<>(s.order);
    }

    public SearchOrder(List<String> subList) {
        this.order = subList;
    }

    public void addOrder(String room) {
        order.add(room);
    }

    public boolean contains(String room) {
        return order.contains(room);
    }
}
