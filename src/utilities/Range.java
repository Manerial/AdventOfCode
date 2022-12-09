package utilities;

import java.util.ArrayList;
import java.util.List;

public class Range {
	private final Integer borneMin;
	private final Integer borneMax;

	public Range(Integer a, Integer b) {
		borneMin = (a > b) ? b : a;
		borneMax = (a > b) ? a : b;
	}

	public boolean isOverlappedBy(Range range2) {
		return (this.borneMin >= range2.borneMin && this.borneMin <= range2.borneMax) ||
				(this.borneMax >= range2.borneMin && this.borneMax <= range2.borneMax);
	}

	public boolean contains(Range range2) {
		return this.borneMin <= range2.borneMin && this.borneMax >= range2.borneMax;
	}
	
	@Override
	public String toString() {
		return "[" + borneMin + ".." + borneMax + "]";
	}

	public static List<Integer> toList(int a, int b) {
		List<Integer> list = new ArrayList<>();
		for(int i = a; i <= b; i++) {
			list.add(i);
		}
		return list;
	}
}
