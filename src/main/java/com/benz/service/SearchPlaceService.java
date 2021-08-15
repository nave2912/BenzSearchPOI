package com.benz.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.benz.model.Item;
import com.benz.model.Root;
import com.benz.searchinterface.SearchPOI;
import com.benz.utils.geo.LatAndLongCalculation;

@Service
public class SearchPlaceService {

	Logger logger = LoggerFactory.getLogger(SearchPlaceService.class);

	@Autowired(required = true)
	SearchPOI spoi;

	@Autowired(required = true)
	LatAndLongCalculation getLatAndLong;

	@Value("${API_KEY}")
	private String API_KEY;

	@Value("${SEARCH_API}")
	private String SEARCH_API_URL;

	@Value("${SEARCH_COUNT}")
	private int SEARCH_COUNT;

	@Async
	@CacheEvict(value = "ten-minute-cache", key = "'poicache'+#city+#searchtype", condition = "#isCacheable == null || !#isCacheable", beforeInvocation = true)
	@Cacheable(value = "ten-minute-cache", key = "'poicache'+#city+#searchtype", condition = "#isCacheable != null && #isCacheable")
	public CompletableFuture<HashMap<String, ArrayList<List<Item>>>> searchPlace(String city, String searchtype,
			boolean isCacheable) throws JSONException, MalformedURLException, IOException, InterruptedException {
		Thread.sleep(4000);
		logger.info("get list of user by " + Thread.currentThread().getName());
		logger.info("searchtype started time " + searchtype + System.currentTimeMillis());
		HashMap<String, ArrayList<List<Item>>> hash_mavalue = new HashMap<String, ArrayList<List<Item>>>();
		String cityPosition = getLatAndLong.getdirection(city);
		String searchAPI = SEARCH_API_URL + "?at=" + cityPosition + "&q=" + searchtype + "&apiKey=" + API_KEY;
		Root roothistory = spoi.searchpoi(searchAPI);
		logger.info("searchtype end time " + searchtype + System.currentTimeMillis());
		hash_mavalue.put(searchtype, SearchPlaceService.findBySearchCount(roothistory,SEARCH_COUNT));
		return CompletableFuture.completedFuture(hash_mavalue);
	}

	public static ArrayList<List<Item>> findBySearchCount(Root rootvalue,int SEARCH_COUNT) {
		ArrayList<List<Item>> result = new ArrayList<List<Item>>();
		List<Item> itemList = new ArrayList<Item>();

		List<Item> allItemInList = rootvalue.getResults().getItems();
		Collections.sort(allItemInList, new Comparator<Item>() {
			public int compare(Item item1, Item item2) {
				return ((Integer) item1.getDistance()).compareTo(item2.getDistance());
			}
		});

		for (int i = 0; i < SEARCH_COUNT; i++) {
			Item makingItemPojo = new Item();
			makingItemPojo.setPosition(allItemInList.get(i).getPosition());
			makingItemPojo.setDistance(allItemInList.get(i).getDistance());
			makingItemPojo.setTitle(allItemInList.get(i).getTitle());
			makingItemPojo.setAverageRating(allItemInList.get(i).getAverageRating());
			makingItemPojo.setHref(allItemInList.get(i).getHref());
			makingItemPojo.setOpeningHours(allItemInList.get(i).getOpeningHours());
			itemList.add(makingItemPojo);

		}
		result.add(itemList);

		return result;

	}

}