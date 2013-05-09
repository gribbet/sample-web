package server.util;

import com.google.common.base.Objects;

public class Range implements Comparable<Range> {
	private int high;
	private int low;

	public Range() {
	}

	public Range(Integer high, Integer low) {
		if (high == null)
			high = Integer.MAX_VALUE;
		if (low == null)
			low = Integer.MIN_VALUE;
		this.high = high;
		this.low = low;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("high", high).add("low", low).toString();
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	@Override
	public int compareTo(Range other) {
		if (other.high < low)
			return -1;
		if (other.low > high)
			return 1;
		return 0;
	}
}
