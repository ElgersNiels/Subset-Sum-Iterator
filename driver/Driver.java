package driver;

import java.util.Arrays;

import subsetsum.IntSubsetSumIterator;
import subsetsum.SubsetSumIterator;

public class Driver {

	public static void main(String[] args) {
		SubsetSumIterator<Integer> iter = new IntSubsetSumIterator(Arrays.asList(1,2,3,4,5), 10);
		
		while (iter.hasNext())
			System.out.println(iter.next());
	}

}
