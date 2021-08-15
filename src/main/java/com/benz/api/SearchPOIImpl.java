package com.benz.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.benz.model.Root;
import com.benz.searchinterface.SearchPOI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SearchPOIImpl implements SearchPOI {

	@Autowired
	ObjectMapper om;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Root searchpoi(String url) throws JsonMappingException, JsonProcessingException {

		Root rotoresult = null;
		String requestUri = url;
		try {
			rotoresult = restTemplate.getForObject(requestUri, Root.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotoresult;

	}
}
