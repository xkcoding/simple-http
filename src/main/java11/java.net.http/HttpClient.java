package java.net.http;

import java.io.IOException;
import java.time.Duration;

/**
 * For the Java 8 compatibility when compiled with JDK 11+.
 *
 * @author L.cm
 */
public abstract class HttpClient {

	public static HttpClient.Builder newBuilder() {
		return null;
	}

	public abstract <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException;

	public interface Builder {
		Builder connectTimeout(Duration duration);

		HttpClient build();
	}
}
