package toInt;

public interface ToInt<T> {
	
	/**
	 * Translate type T into an integer.
	 * For example, if T is a Rectangle, toInt could return its area.
	 * @param t The object to translate.
	 * @return An integer respresenting the given object.
	 */
	int toInt(T t);

}