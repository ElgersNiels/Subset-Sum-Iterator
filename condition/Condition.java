package condition;

import java.util.List;

public interface Condition<T> {
	boolean sat(List<T> ts, T t);
}
