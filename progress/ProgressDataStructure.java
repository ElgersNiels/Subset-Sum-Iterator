package progress;

//An Adapter kinda.
public interface ProgressDataStructure<T> {
	void add(SubsetSumProgress<T> progress);
	SubsetSumProgress<T> next();
	boolean isEmpty();
}
