package progress;

import java.util.Stack;

public class ProgressStack<T> implements ProgressDataStructure<T> {

	private Stack<SubsetSumProgress<T>> stack;
	
	public ProgressStack() {
		this.stack = new Stack<SubsetSumProgress<T>>();
	}
	
	@Override
	public void add(SubsetSumProgress<T> progress) {
		stack.push(progress);
	}

	@Override
	public SubsetSumProgress<T> next() {
		return stack.pop();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

}
