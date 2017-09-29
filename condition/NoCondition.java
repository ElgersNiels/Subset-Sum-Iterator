package condition;

import java.util.List;

public class NoCondition<T> implements FinalCondition<T> {

	@Override
	public boolean sat(List<T> ts, T t) {
		return true;
	}

	@Override
	public boolean sat(List<T> ts) {
		return true;
	}

}
