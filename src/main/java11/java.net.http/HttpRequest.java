package java.net.http;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.Duration;

/**
 * For the Java 8 compatibility when compiled with JDK 11+.
 *
 * @author L.cm
 */
public class HttpRequest {

	public static HttpRequest.Builder newBuilder() {
		return null;
	}

	public interface BodyPublisher {
	}

	public static class BodyPublishers {
		public static BodyPublisher ofString(String body) {
			return null;
		}

		public static BodyPublisher ofString(String s, Charset charset) {
			return null;
		}

		public static BodyPublisher noBody() {
			return null;
		}
	}

	public interface Builder {
		Builder uri(URI uri);

		Builder header(String name, String value);

		Builder timeout(Duration duration);

		Builder GET();

		Builder POST(HttpRequest.BodyPublisher bodyPublisher);

		HttpRequest build();
	}
}
