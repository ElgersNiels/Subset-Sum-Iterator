package driver;

import java.util.Arrays;
import java.util.Comparator;

import progress.SubsetSumProgress;
import subsetsum.SubsetSumIterator;
import subsetsum.SubsetSumIteratorBuilder;
import toInt.IntToInt;

public class Driver {

	public static void main(String[] args) {
		SubsetSumIterator<Integer> iter = new SubsetSumIteratorBuilder<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10), 20, new IntToInt())
				.priorityQueue(new Comparator<SubsetSumProgress<Integer>>() {

					@Override
					public int compare(SubsetSumProgress<Integer> o1, SubsetSumProgress<Integer> o2) {
						int o = o1.partialSubset.size() - o2.partialSubset.size();
						
						if (o != 0) return o;
						
						return o1.remainingSum - o2.remainingSum;
					}
					
				})
				.build();
		
		while (iter.hasNext()) System.out.println(iter.next());
	}

}
