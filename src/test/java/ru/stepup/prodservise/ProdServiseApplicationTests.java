package ru.stepup.prodservise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProdServiseApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void sendPost_Account() throws URISyntaxException {
		final String baseUrl = "http://localhost:8080/api/dopay";
		URI uri = new URI(baseUrl);

		String message = "{\n" +
				"    \"prodId\": 82,\n" +
				"    \"saldo\": 500\n" +
				"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(message, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		System.out.println("########## result.getStatusCode() = " + result.getStatusCode());
		System.out.println(result.getBody().toString());

	}
}