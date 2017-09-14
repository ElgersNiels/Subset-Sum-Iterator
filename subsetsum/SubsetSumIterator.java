package subsetsum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import toInt.ToInt;

//Memory: O(elements.size()*sum)
//Time: O(elements.size()*sum)
public class SubsetSumIterator<T> implements Iterator<List<T>> {

	//Dynamic Programming table.
	private boolean[][] dp;
	
	//Elements.
	private List<T> elements;
	//Element to Integer map.
	private List<ToInt<T>> toInt;
	
	/**
	 * Constructor.
	 * @param elements The elements.
	 * @param sum The desired sum.
	 * @param toInt A List containing ToInt for each of the element in respective order.  
	 */
	public SubsetSumIterator(List<T> elements, int sum, List<ToInt<T>> toInt) {
		//Check input.
		for (int i = 0; i < elements.size(); i++)
			if (toInt.get(i).toInt(elements.get(i)) < 0)
				throw new IllegalArgumentException("Must be all non-negative values.");
		
		if (sum < 0)
			throw new IllegalArgumentException("Wanted sum must be non-negative.");
		
		if (elements.size() != toInt.size())
			throw new IllegalArgumentException("Size of elements List must be equal to size of ToInteger List.");
		
		//Set input.
		this.elements = elements;
		this.toInt = toInt;
		this.dp = new boolean[elements.size()][sum + 1];
		
		////Populate DP table.
		//Any set has a subset that sums to 0, viz. the empty subset.
		for (int i = 0; i < elements.size(); i++)
			dp[i][0] = true;
		//The singleton sums to its value. Only set it is smaller or equal to the desired sum.
		if (toInt.get(0).toInt(elements.get(0)) <= sum)
			dp[0][toInt.get(0).toInt(elements.get(0))] = true;
		//Using base cases above, fill the rest of the table.
		for (int i = 1; i < elements.size(); i++)
            for (int j = 0; j < sum + 1; j++)
                dp[i][j] = (toInt.get(i).toInt(elements.get(i)) <= j) ? (dp[i - 1][j] || dp[i - 1][j - toInt.get(i).toInt(elements.get(i))]) : dp[i - 1][j];
        //Initialize the Stack and populate it with the first subset to check.
        this.stack = new Stack<>();
        stack.push(new Info<T>(elements.size() - 1, sum, new ArrayList<T>()));
	}
	
	public SubsetSumIterator(List<T> elements, int sum, ToInt<T> tI) {
		this(elements, sum, new ArrayList<ToInt<T>>(Collections.nCopies(elements.size(), tI)));
	}
	
	private Stack<Info<T>> stack;
	private static class Info<T> {
		public Info(int i, int sum, List<T> p) {
			this.i = i;
			this.sum = sum;
			this.p = p;
		}
		private int i;
		private int sum;
		private List<T> p;
	}
	
	@Override
	public boolean hasNext() {
		//If the DP table proofs that a subset sum exists and the Stack is not empty,
		//there still exists a subset sum that has not yet been found.
		return dp[elements.size() - 1][dp[0].length - 1] && !stack.empty();
	}

	@Override
	public List<T> next() {
		//while a subset sum has not been found and the Stack is not empty.
		while (!stack.isEmpty()) {
			Info<T> pop = stack.pop();
			
			////Base cases
			//If all elements have been iterated but the first one and the sum has been found, return the result.
			if (pop.i == 0 && pop.sum == 0)
				return new ArrayList<>(pop.p);
			//If all elements have been iterated but the first one and the first element's integer value equals the remaining desired sum,
			//add the first element to the result and return result.
			if (pop.i == 0 && dp[0][pop.sum]) {
				pop.p.add(elements.get(pop.i));
				return new ArrayList<>(pop.p);
			}
			
			////Add to Stack.
			//If a subset sum exists when skipping current element, add as such to Stack.
			if (dp[pop.i-1][pop.sum])
	            stack.push(new Info<T>(pop.i-1, pop.sum, new ArrayList<>(pop.p)));
			//If a subset sum exists when adding current element to result, add as such to Stack.
			if (pop.sum >= toInt.get(pop.i).toInt(elements.get(pop.i)) && dp[pop.i-1][pop.sum-toInt.get(pop.i).toInt(elements.get(pop.i))]){
				List<T> c = new ArrayList<>(pop.p); c.add(elements.get(pop.i));
				stack.push(new Info<T>(pop.i-1, pop.sum-toInt.get(pop.i).toInt(elements.get(pop.i)), c));
			}
		}
		//This will only be reached when the desired sum is 0. The empty set sums to 0.
		return new ArrayList<>();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
