package java.net.http;

/**
 * For the Java 8 compatibility when compiled with JDK 11+.
 *
 * @author L.cm
 */
public interface HttpResponse<T> {

	T body();

	interface BodyHandler<T> {

	}

	class BodyHandlers {
		public static BodyHandler<String> ofString() {
			return null;
		}
	}
}
