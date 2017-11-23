package subsetsum;

import java.util.List;

import toInt.IntToInt;

public class IntSubsetSumIterator extends SubsetSumIterator<Integer> {

	/**
	 * An Iterator that iterates over all sublists of integers that sum to the given sum.
	 * @param elements The integers.
	 * @param sum The sum.
	 */
	public IntSubsetSumIterator(List<Integer> elements, int sum) {
		super(elements, sum, new IntToInt());
	}

}
