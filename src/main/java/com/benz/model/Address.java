package com.benz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Address {
	public String text;
	public String street;
	public String postalCode;
	public String district;
	public String city;
	public String county;
	public String stateCode;
	public String country;
	public String countryCode;

}
