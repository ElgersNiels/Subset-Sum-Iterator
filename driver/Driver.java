package driver;

import java.util.Arrays;

import subsetsum.IntSubsetSumIterator;
import subsetsum.SubsetSumIterator;

public class Driver {

	public static void main(String[] args) {
		SubsetSumIterator<Integer> iter = new IntSubsetSumIterator(Arrays.asList(1,2,3,4,5,6,7,8,9,10), 13);
		
		while (iter.hasNext()) System.out.println(iter.next());
	}

}
