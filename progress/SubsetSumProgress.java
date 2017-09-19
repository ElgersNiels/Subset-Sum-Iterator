package progress;

import java.util.List;

public final class SubsetSumProgress<T> {
	
	public final int i;
	public final int remainingSum;
	public final List<T> partialSubset;
	
	public SubsetSumProgress(final int i, final int remainingSum, final List<T> partialSubset) {
		this.i = i;
		this.remainingSum = remainingSum;
		this.partialSubset = partialSubset;
	}
	
}
