package com.benz.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL) 
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Item {
	public List<Double> position;

	public int distance;

	public String title;
	public int averageRating;
	public Category category;
	public String icon;
	public String vicinity;
	public List<Object> having;
	public String type;
	public String href;
	public String id;
	public List<String> chainIds;
	public OpeningHours openingHours;
	public List<Tag> tags;
	public List<AlternativeName> alternativeNames;

	@Override
	public String toString() {
		return "Item [position=" + position + ", distance=" + distance + ", title=" + title + ", averageRating="
				+ averageRating + ", category=" + category + ", icon=" + icon + ", vicinity=" + vicinity + ", having="
				+ having + ", type=" + type + ", href=" + href + ", id=" + id + ", chainIds=" + chainIds
				+ ", openingHours=" + openingHours + ", tags=" + tags + ", alternativeNames=" + alternativeNames + "]";
	}

}
