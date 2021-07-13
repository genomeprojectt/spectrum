package com.spectrum.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Details {

	@JsonProperty("organization")
	private String organization;
	
	@JsonProperty("release_count")
	private int release_count = 20;
	
	@JsonProperty("total_labor_hours")
	private Long total_labor_hours;
	
	@JsonProperty("all_in_production")
	private Boolean all_in_production = Boolean.FALSE;
	
	@JsonProperty("licenses")
	private String licenses = "LGPL-2.1";
	
	@JsonProperty("most_active_months")
	private String most_active_months = "5";

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public int getRelease_count() {
		return release_count;
	}

	public void setRelease_count(int release_count) {
		this.release_count = release_count;
	}

	public Long getTotal_labor_hours() {
		return total_labor_hours;
	}

	public void setTotal_labor_hours(Long total_labor_hours) {
		this.total_labor_hours = total_labor_hours;
	}

	public Boolean getAll_in_production() {
		return all_in_production;
	}

	public void setAll_in_production(Boolean all_in_production) {
		this.all_in_production = all_in_production;
	}

	public String getLicenses() {
		return licenses;
	}

	public void setLicenses(String licenses) {
		this.licenses = licenses;
	}

	public String getMost_active_months() {
		return most_active_months;
	}

	public void setMost_active_months(String most_active_months) {
		this.most_active_months = most_active_months;
	}
	
	
	
}
