package com.benz.utils.geo;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.benz.model.Position;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LatAndLongCalculation {
	
	@Value("${GEOCODING_RESOURCE}")
	private String GEOCODING_RESOURCE;
	
	@Value("${API_KEY}")
	private String API_KEY;
	
	static String lat;
	static String lng;

	@Autowired
	RestTemplate restTemplate;

	public String getdirection(String city) throws IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		Position pos = new Position();
		String encodedQuery = URLEncoder.encode(city, "UTF-8");
		String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery;
		JsonNode responseJsonNode = mapper.readTree(restTemplate.getForObject(requestUri, String.class));
		JsonNode items = responseJsonNode.get("items");
		for (JsonNode item : items) {
			JsonNode position = item.get("position");
			lat = position.get("lat").asText();
			lng = position.get("lng").asText();
			String positionValue[] = { lat, lng };
			pos.setPosition(positionValue);
		}
		return lat + "," + lng;

	}
}
