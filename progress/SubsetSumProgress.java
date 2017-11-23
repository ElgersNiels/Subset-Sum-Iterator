package progress;

import java.util.List;

public final class SubsetSumProgress<T> {
	
	//The elements currently in the subset.
	public final List<T> partialSubset;
	
	//The progress. Also the index of the next element to consider.
	public final int i;
	
	//The amount that is left to find.
	//If 0, partialSubset is a subset sum.
	public final int remainingSum;
	
	public SubsetSumProgress(final int i, final int remainingSum, final List<T> partialSubset) {
		this.i = i;
		this.remainingSum = remainingSum;
		this.partialSubset = partialSubset;
	}
	
	public String toString() {
		return i + " " + remainingSum + " " + partialSubset;
	}
	
}
