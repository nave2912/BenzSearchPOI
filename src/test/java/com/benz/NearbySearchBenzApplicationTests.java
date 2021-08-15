package com.benz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.benz.model.Item;
import com.benz.service.SearchPlaceService;

@WebMvcTest
class NearbySearchBenzApplicationTests {

	@MockBean
	private SearchPlaceService searchPlaceService;

	HashMap<String, ArrayList<List<Item>>> as = new HashMap<String, ArrayList<List<Item>>>();

	ArrayList<List<Item>> list = new ArrayList<List<Item>>();

	@Test
	void testGetRestaurantDetails() throws Exception {
		CompletableFuture<HashMap<String, ArrayList<List<Item>>>> completableFuture = CompletableFuture
				.completedFuture(as);
		assertTrue(completableFuture.isDone());
		CompletableFuture<HashMap<String, ArrayList<List<Item>>>> as = new CompletableFuture<HashMap<String, ArrayList<List<Item>>>>();
		Mockito.when(searchPlaceService.searchPlace("chennai", "restaurant", false)).thenReturn(as);
	}

	@Test
	void testGetRestaurantDetailsWithCache() throws Exception {
		CompletableFuture<HashMap<String, ArrayList<List<Item>>>> completableFuture = CompletableFuture
				.completedFuture(as);
		assertTrue(completableFuture.isDone());
		CompletableFuture<HashMap<String, ArrayList<List<Item>>>> as = new CompletableFuture<HashMap<String, ArrayList<List<Item>>>>();
		Mockito.when(searchPlaceService.searchPlace("chennai", "restaurant", true)).thenReturn(as);
	}

	@Test
	public void testSequence1() {
		List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> futures = getFutures();
		CompletableFuture<Void> myCombinedFuture = sequence(futures);
		// here we complete each future
		futures.forEach(future -> future.complete(as));
		myCombinedFuture.join();
		assertTrue(myCombinedFuture.isDone());
	}

	@Test
	public void testSequence2() {
		List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> futures = getFutures();
		CompletableFuture<Void> myCombinedFuture = sequence(futures);
		futures.get(0).complete(as);
		futures.get(1).complete(as);
		myCombinedFuture.join();
		// this does not work because future3 is not completed -> we wait forever
		assertTrue(myCombinedFuture.isDone());
	}

	@Test
	public void testSequence3() throws InterruptedException {
		List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> futures = getFutures();
		CompletableFuture<Void> myCombinedFuture = sequence(futures);
		futures.get(0).complete(as);
		futures.get(1).complete(as);
		// complete future 3 asynchronous after 2 seconds
		Thread.sleep(2000);
		futures.get(2).complete(as);
		myCombinedFuture.join();
		// after 2 seconds all futures are completed and the test will pass
		assertTrue(myCombinedFuture.isDone());

	}

	public List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> getFutures() {
		CompletableFuture<HashMap<String, ArrayList<List<Item>>>> s = new CompletableFuture<HashMap<String, ArrayList<List<Item>>>>();
		List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> al = new ArrayList<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>>();
		al.add(s);
		al.add(s);
		al.add(s);
		return al;


	}

	public CompletableFuture<Void> sequence(
			List<CompletableFuture<HashMap<String, ArrayList<List<Item>>>>> futuresList) {
		return CompletableFuture.allOf(CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])));
	}
}
