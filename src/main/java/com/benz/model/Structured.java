package com.benz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Structured {
	public String start;
	public String duration;
	public String recurrence;
}
