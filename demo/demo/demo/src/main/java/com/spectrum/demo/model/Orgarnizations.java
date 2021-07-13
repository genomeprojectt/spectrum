package com.spectrum.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Orgarnizations {

	@JsonProperty("organizations")
	private List<Details> details;

	public List<Details> getDetails() {
		return details;
	}

	public void setDetails(List<Details> details) {
		this.details = details;
	}
	
	
}
