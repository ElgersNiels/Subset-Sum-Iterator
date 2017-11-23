package subsetsum;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import condition.Condition;
import condition.FinalCondition;
import condition.NoCondition;
import progress.ProgressDataStructure;
import progress.ProgressPriorityQueue;
import progress.ProgressStack;
import progress.SubsetSumProgress;
import progress.comparator.lengthComparator;
import toInt.ToInt;

public class SubsetSumIteratorBuilder<T> {

	private List<T> elements;
	private int sum;
	private List<ToInt<T>> subsetConditions;
	
	private ProgressDataStructure<T> progressDataStructure;
	private Condition<T> progressCondition;
	private FinalCondition<T> finalCondition;
	private SubsetSumDPTable<T> table;
	
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
	
	public SubsetSumIteratorBuilder<T> priorityQueue() {
		return priorityQueue(new lengthComparator<T>());
	}
	
	public SubsetSumIteratorBuilder<T> stack() {
		this.progressDataStructure = new ProgressStack<T>();
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> progressCondition(Condition<T> condition) {
		this.progressCondition = condition;
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> finalCondition(FinalCondition<T> condition) {
		this.finalCondition = condition;
		return this;
	}
	
	public SubsetSumIteratorBuilder<T> dpTable(SubsetSumDPTable<T> table) {
		this.table = table;
		return this;
	}
	
	public SubsetSumIterator<T> build() {
		if (this.progressDataStructure == null)
			stack();
		if (this.progressCondition == null)
			progressCondition(new NoCondition<T>());
		if (this.finalCondition == null)
			finalCondition(new NoCondition<T>());
		if (this.table == null)
			dpTable(new SubsetSumDPTable<T>(elements.size(), sum));
		
		return new SubsetSumIterator<T>(elements, sum, subsetConditions, progressDataStructure, progressCondition, finalCondition, table);
	}
}
