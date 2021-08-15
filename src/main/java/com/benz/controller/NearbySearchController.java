package com.benz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.benz.model.Item;
import com.benz.service.SearchPlaceService;

@RestController
@RequestMapping("/v1")
public class NearbySearchController {

	@Autowired
	private SearchPlaceService searchPlaceService;
	
	CompletableFuture<HashMap<String, ArrayList<List<Item>>>> searchrestaurant;
	CompletableFuture<HashMap<String, ArrayList<List<Item>>>> searchevchargingstation;
	CompletableFuture<HashMap<String, ArrayList<List<Item>>>> searchparkingfacility;

	Logger logger = LoggerFactory.getLogger(SearchPlaceService.class);
	
	@GetMapping(value = "/searchPOI", produces = "application/json")
	@ResponseBody
	public ResponseEntity<ArrayList<HashMap<String, ArrayList<List<Item>>>>> getDetails(
			@RequestParam(name = "city") String city, @RequestParam(name = "isCacheable") boolean isCacheable) {
		try {
			searchrestaurant = searchPlaceService.searchPlace(city, "restaurant", isCacheable);
			searchevchargingstation = searchPlaceService.searchPlace(city, "ev-charging-station", isCacheable);
			searchparkingfacility = searchPlaceService.searchPlace(city, "parking-facility", isCacheable);
			CompletableFuture.allOf(searchrestaurant, searchevchargingstation, searchparkingfacility).get();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Restaurant search API call is completed true/false : " + searchrestaurant.isDone());
		logger.info(
				"ChargingStation search API call is completed true/false : " + searchevchargingstation.isDone());
		logger.info("Parking Facility search API call is completed true/false : " + searchrestaurant.isDone());
		ArrayList<HashMap<String, ArrayList<List<Item>>>> ls = new ArrayList<>(
				Stream.of(searchrestaurant, searchevchargingstation, searchparkingfacility).map(CompletableFuture::join)
						.collect(Collectors.toList()));
		return new ResponseEntity<ArrayList<HashMap<String, ArrayList<List<Item>>>>>(ls, HttpStatus.OK);

	}

}
