package adventofcode.aoc_2022_04;

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
}
