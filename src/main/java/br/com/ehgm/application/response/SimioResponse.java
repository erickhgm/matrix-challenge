package br.com.ehgm.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimioResponse {
	
	private Boolean isSimian;
	
	public SimioResponse(Boolean isSimian) {
		this.isSimian = isSimian;
	}

	@JsonProperty("is_simian")
	public Boolean getIsSimian() {
		return isSimian;
	}
}