package progress;

/**
 * 
 * Datastructure interface to store/get SubsetSumProgress.
 * 
 * @author Niels Elgers
 *
 * @param <T> The type of the objects in the set to find subset sums for. 
 */
public interface ProgressDataStructure<T> {
	void add(SubsetSumProgress<T> progress);
	SubsetSumProgress<T> next();
	boolean isEmpty();
}
