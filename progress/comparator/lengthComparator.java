package progress.comparator;

import java.util.Comparator;

import progress.SubsetSumProgress;

public class lengthComparator<T> implements Comparator<SubsetSumProgress<T>> {

	@Override
	public int compare(SubsetSumProgress<T> o1, SubsetSumProgress<T> o2) {
		int s = o1.partialSubset.size() - o2.partialSubset.size();
		
		if (s != 0) return s;
		
		return o1.remainingSum - o2.remainingSum;
	}

}
