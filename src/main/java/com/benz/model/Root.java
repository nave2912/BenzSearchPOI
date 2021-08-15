package com.benz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter

public class Root {
	public Results results;
	public Search search;
}
