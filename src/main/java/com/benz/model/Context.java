package com.benz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Context {
	public Location location;
	public String type;
	public String href;
}
