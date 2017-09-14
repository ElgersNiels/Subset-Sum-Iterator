package subsetsum;

import java.util.List;

import toInt.IntToInt;

public class IntSubsetSumIterator extends SubsetSumIterator<Integer> {

	public IntSubsetSumIterator(List<Integer> elements, int sum) {
		super(elements, sum, new IntToInt());
	}

}
