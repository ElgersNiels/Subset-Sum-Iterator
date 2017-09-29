package progress;

import java.util.Comparator;
import java.util.PriorityQueue;

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
