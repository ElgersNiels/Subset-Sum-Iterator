package condition;

import java.util.List;

public interface ListCondition<T> {
	boolean sat(List<T> ts, T t);
	boolean sat(List<T> ts);
}
