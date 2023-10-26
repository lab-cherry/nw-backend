package lab.cherry.nw.util;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HttpUtils {

	public static byte[] httpRequest(URI uri, String Method, HttpHeaders headers){

		HttpMethod _method;

		switch (Method) {
			case "GET":
				_method = HttpMethod.GET;
			case "POST":
				_method = HttpMethod.POST;
			case "DELETE":
				_method = HttpMethod.DELETE;
			default:
				_method = HttpMethod.GET;
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> result = restTemplate.exchange(uri, _method, new HttpEntity<>(headers), byte[].class);

		return result.getBody();

		// log.error("result {}", result);

		// return result.getBody();

	}


	public static byte[] getForObject(URI uri){

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(uri, byte[].class);

	}	
}