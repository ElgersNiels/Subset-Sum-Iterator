package condition;

import java.util.List;

public interface FinalCondition<T> extends Condition<T> {
	boolean sat(List<T> ts);
}
