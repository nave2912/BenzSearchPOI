package com.benz.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class OpeningHours {
	public String text;
    public String label;
    public boolean isOpen;
    public List<Structured> structured;
}
