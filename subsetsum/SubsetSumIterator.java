package subsetsum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import condition.Condition;
import condition.FinalCondition;
import condition.NoCondition;
import progress.ProgressDataStructure;
import progress.ProgressStack;
import progress.SubsetSumProgress;
import toInt.ToInt;

public class SubsetSumIterator<T> implements Iterator<List<T>> {

	//Dynamic Programming table.
	private SubsetSumDPTable<T> dp;
	
	//Elements.
	private List<T> elements;
	//Element to Integer map.
	private List<? extends ToInt<T>> toInt;
	//Condition.
	private Condition<T> interCond;
	private FinalCondition<T> finalCond;
	
	//Next.
	private List<T> next;
	
	//Progress
	private ProgressDataStructure<T> pQ;
	

	
	/**
	 * A SubsetSumIterator that iterates over subsets of `elements` which elements sum to `sum`.
	 * It uses the ToInt in `toInt` at index i to translate the element int `elements` at index i into an integer.
	 * @param elements The elements.
	 * @param sum The sum to sum to.
	 * @param toInt The ToInt's to translate elements to integers.
	 */
	public SubsetSumIterator(List<T> elements, int sum, List<? extends ToInt<T>> toInt) {
		this(elements, sum, toInt, new ProgressStack<T>(), new NoCondition<T>(), new NoCondition<T>());
	}
	
	/**
	 * A SubsetSumIterator that iterates over subsets of `elements` which elements sum to `sum`.
	 * It uses the ToInt `toInt` to translate the elements to integers.
	 * @param elements The elements.
	 * @param sum The sum to sum to.
	 * @param toInt The ToInt to translate elements to integers.
	 */
	public SubsetSumIterator(List<T> elements, int sum, ToInt<T> toInt) {
		this(elements, sum, Collections.nCopies(elements.size(), toInt));
	}
	
	public SubsetSumIterator(List<T> elements, int sum, List<? extends ToInt<T>> toInt, //Obligatories
			ProgressDataStructure<T> progressDataStructure,	//Optionals
			Condition<T> progressCondition,
			FinalCondition<T> finalCondition) {
		this(elements, sum, toInt, progressDataStructure, progressCondition, finalCondition, new SubsetSumDPTable<T>(elements.size(), sum));
	}
	
	/**
	 * A SubsetSumIterator that iterates over subsets of `elements` which elements sum to `sum`.
	 * It uses the ToInt in `toInt` at index i to translate the element int `elements` at index i into an integer.
	 * Potential subsets are explored in order (if any order) defined in the `progressDatastructure`.
	 * When an element is being added to a subset because it would potentially lead to a valid subset sum,
	 * it is checked if adding the element would not violate the `progressCondition`. If it would, the element is not added not and not further explored.
	 * When a valid subset sum has been found, it is checked if the subset satisfies the `finalCondition`.
	 * @param elements The elements.
	 * @param sum The sum to sum to.
	 * @param toInt The ToInt's to translate elements to integers.
	 * @param progressDataStructure The progressDataStructure.
	 * @param progressCondition The progress condition.
	 * @param finalCondition The final condition.
	 */
	public SubsetSumIterator(List<T> elements, int sum, List<? extends ToInt<T>> toInt, //Obligatories
			ProgressDataStructure<T> progressDataStructure,	//Optionals
			Condition<T> progressCondition,
			FinalCondition<T> finalCondition,
			SubsetSumDPTable<T> dp) {
		
		//Check input.
		for (int i = 0; i < elements.size(); i++)
			if (toInt.get(i).toInt(elements.get(i)) < 0)
				throw new IllegalArgumentException("Must be all non-negative values.");
		
		if (sum < 0)
			throw new IllegalArgumentException("Wanted sum must be non-negative.");
		
		if (elements.size() != toInt.size())
			throw new IllegalArgumentException("Size of ToInteger List must be equal to size of element List.");
		
		//Set input.
		this.elements	= elements;
		this.toInt		= toInt;
		this.interCond	= progressCondition;
		this.finalCond	= finalCondition;
        this.dp 		= dp;
        
		dp.populate(elements, sum, toInt);
		
        this.pQ = progressDataStructure;
        pQ.add(new SubsetSumProgress<T>(elements.size() - 1, sum, new ArrayList<T>()));
        
        this.next = findNext();
	}
	
	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public List<T> next() {
		List<T> toReturn = next;
		
		this.next = findNext();
		
		return toReturn;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private List<T> findNext() {
		while (!pQ.isEmpty())
		{
			SubsetSumProgress<T> pop = pQ.next();
			
			if (pop.i == 0 && pop.remainingSum == 0 && finalCond.sat(pop.partialSubset))
				return new ArrayList<>(pop.partialSubset);
			
			if (pop.i == 0 && dp.get(0, pop.remainingSum)
					&& interCond.sat(pop.partialSubset, elements.get(0))
					&& finalCond.sat(pop.partialSubset, elements.get(0)))
			{
				pop.partialSubset.add(elements.get(0));
				return new ArrayList<>(pop.partialSubset);
			}
			
			if (pop.i == 0)
				continue;
			
			if (dp.get(pop.i-1, pop.remainingSum))
				pQ.add(new SubsetSumProgress<T>(pop.i-1, pop.remainingSum, new ArrayList<>(pop.partialSubset)));
			
			if (pop.remainingSum >= toInt.get(pop.i).toInt(elements.get(pop.i))
				&& dp.get(pop.i-1, pop.remainingSum - toInt.get(pop.i).toInt(elements.get(pop.i)) )
				&& interCond.sat(pop.partialSubset, elements.get(pop.i)))
			{
				List<T> c = new ArrayList<>(pop.partialSubset); c.add(elements.get(pop.i));
				pQ.add(new SubsetSumProgress<T>(pop.i-1, pop.remainingSum-toInt.get(pop.i).toInt(elements.get(pop.i)), c));
			}
		}
		
		return null;
	}

}