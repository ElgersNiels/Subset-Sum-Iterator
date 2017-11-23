package progress;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 
 * Takes a Comparator of SubsetSumProgresses to define which SubsetSumProgress is returned by next().
 * Makes use of a PriorityQueue for this purpose.
 * next() returns the smallest SubsetSumProgress (as defined by the comparator).
 * 
 * @author Niels Elgers
 *
 * @param <T> The type of the objects in the set to find subset sums for. 
 */
public class ProgressPriorityQueue<T> implements ProgressDataStructure<T> {

	private PriorityQueue<SubsetSumProgress<T>> set;
	
	public ProgressPriorityQueue(Comparator<SubsetSumProgress<T>> comparator) {
		this.set = new PriorityQueue<>(comparator);
	}
	
	@Override
	public void add(SubsetSumProgress<T> progress) {
		set.add(progress);
	}

	@Override
	public SubsetSumProgress<T> next() {
		return set.poll();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

}
