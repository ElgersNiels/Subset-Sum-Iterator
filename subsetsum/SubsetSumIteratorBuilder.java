package subsetsum;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import condition.ListCondition;
import condition.NoCondition;
import progress.ProgressDataStructure;
import progress.ProgressPriorityQueue;
import progress.ProgressStack;
import progress.SubsetSumProgress;
import toInt.ToInt;

public class SubsetSumIteratorBuilder<T> {

	private List<T> elements;
	private int sum;
	private List<ToInt<T>> subsetConditions;
	
	private ProgressDataStructure<T> progressDataStructure;
	private ListCondition<T> progressCondition;
	private ListCondition<T> finalCondition;
	
	public SubsetSumIteratorBuilder(List<T> elements, int sum, List<ToInt<T>> subsetConditions) {
		this.elements = elements;
		this.sum = sum;
		this.subsetConditions = subsetConditions;
	}
	
	public SubsetSumIteratorBuilder(List<T> elements, int sum, ToInt<T> subsetCondition) {
		this(elements, sum, Collections.nCopies(elements.size(), subsetCondition));
	}
	
	public SubsetSumIteratorBuilder<T> priorityQueue(Comparator<SubsetSumProgress<T>> comparator) {
		this.progressDataStructure = new ProgressPriorityQueue<T>(comparator);
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> stack() {
		this.progressDataStructure = new ProgressStack<T>();
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> progressCondition(ListCondition<T> condition) {
		this.progressCondition = condition;
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> finalCondition(ListCondition<T> condition) {
		this.finalCondition = condition;
		return this;
	}
	
	public SubsetSumIterator<T> build() {
		if (this.progressDataStructure == null)
			stack();
		if (this.progressCondition == null)
			progressCondition(new NoCondition<T>());
		if (this.finalCondition == null)
			finalCondition(new NoCondition<T>());
		
		return new SubsetSumIterator<T>(elements, sum, subsetConditions, progressDataStructure, progressCondition, finalCondition);
	}
}
