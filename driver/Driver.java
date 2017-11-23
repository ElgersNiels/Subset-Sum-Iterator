package driver;

import java.util.Arrays;

import subsetsum.SubsetSumIterator;
import subsetsum.SubsetSumIteratorBuilder;
import toInt.IntToInt;

public class Driver {

	public static void main(String[] args) {
		SubsetSumIterator<Integer> iter = new SubsetSumIteratorBuilder<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10), 20, new IntToInt())
				.priorityQueue()
				.build();
		
		while (iter.hasNext()) System.out.println(iter.next());
	}

}
