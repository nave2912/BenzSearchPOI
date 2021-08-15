package com.benz.searchinterface;

import java.io.IOException;

import com.benz.model.Root;

public interface SearchPOI {
	Root searchpoi(String url) throws IOException, InterruptedException;
}
