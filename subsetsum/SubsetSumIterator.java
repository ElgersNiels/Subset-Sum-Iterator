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
	private boolean[][] dp;
	
	//Elements.
	private List<T> elements;
	//Element to Integer map.
	private List<ToInt<T>> toInt;
	//Condition.
	private Condition<T> interCond;
	private FinalCondition<T> finalCond;
	//Desired sum.
	private int sum;
	
	//Next.
	private List<T> next;
	
	private ProgressDataStructure<T> pQ;
	
	public SubsetSumIterator(List<T> elements, int sum, List<ToInt<T>> toInt) {
		this(elements, sum, toInt, new ProgressStack<T>(), new NoCondition<T>(), new NoCondition<T>());
	}
	
	public SubsetSumIterator(List<T> elements, int sum, ToInt<T> toInt) {
		this(elements, sum, Collections.nCopies(elements.size(), toInt));
	}
	
	public SubsetSumIterator(List<T> elements, int sum, List<ToInt<T>> toInt, //Obligatories
			ProgressDataStructure<T> progressDataStructure,	//Optionals
			Condition<T> progressCondition,
			FinalCondition<T> finalCondition) {
		
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
		this.sum = sum;
        
		populateDPTable();
		
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

	private void populateDPTable() {
		this.dp = new boolean[elements.size()][sum + 1];
		
		for (int i = 0; i < elements.size(); i++)
			dp[i][0] = true;
		if (toInt.get(0).toInt(elements.get(0)) <= sum)
			dp[0][toInt.get(0).toInt(elements.get(0))] = true;
		for (int i = 1; i < elements.size(); i++)
	        for (int j = 0; j < sum + 1; j++)
	            dp[i][j] = toInt.get(i).toInt(elements.get(i)) <= j 
	            ? dp[i - 1][j] || dp[i - 1][j - toInt.get(i).toInt(elements.get(i))] : dp[i - 1][j];
	}

	private List<T> findNext() {
		while (!pQ.isEmpty()) {
			SubsetSumProgress<T> pop = pQ.next();
			
			if (pop.i == 0 && pop.remainingSum == 0 && finalCond.sat(pop.partialSubset))
				return new ArrayList<>(pop.partialSubset);
			
			if (pop.i == 0 && dp[0][pop.remainingSum]
					&& interCond.sat(pop.partialSubset, elements.get(0))
					&& finalCond.sat(pop.partialSubset, elements.get(0)))
			{
				pop.partialSubset.add(elements.get(0));
				return new ArrayList<>(pop.partialSubset);
			}
			
			if (pop.i == 0)
				continue;
			
			if (dp[pop.i-1][pop.remainingSum])
				pQ.add(new SubsetSumProgress<T>(pop.i-1, pop.remainingSum, new ArrayList<>(pop.partialSubset)));
			
			if (pop.remainingSum >= toInt.get(pop.i).toInt(elements.get(pop.i))
				&& dp[pop.i-1][pop.remainingSum-toInt.get(pop.i).toInt(elements.get(pop.i))]
				&& interCond.sat(pop.partialSubset, elements.get(pop.i)))
			{
				List<T> c = new ArrayList<>(pop.partialSubset); c.add(elements.get(pop.i));
				pQ.add(new SubsetSumProgress<T>(pop.i-1, pop.remainingSum-toInt.get(pop.i).toInt(elements.get(pop.i)), c));
			}
		}
		
		return null;
	}

}