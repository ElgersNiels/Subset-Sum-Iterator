package progress;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ProgressPriorityQueue<T> implements ProgressDataStructure<T> {

	private PriorityQueue<SubsetSumProgress<T>> queue;
	
	public ProgressPriorityQueue(Comparator<SubsetSumProgress<T>> comparator) {
		this.queue = new PriorityQueue<>(0, comparator);
	}
	
	@Override
	public void add(SubsetSumProgress<T> progress) {
		queue.add(progress);
	}

	@Override
	public SubsetSumProgress<T> next() {
		return queue.poll();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
