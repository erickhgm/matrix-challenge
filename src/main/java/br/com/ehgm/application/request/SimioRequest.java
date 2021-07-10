package br.com.ehgm.application.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimioRequest {
	
	private List<String> dna;
	
	@JsonProperty("dna")
	public List<String> getDna() {
		return dna;
	}

	public void setDna(List<String> dna) {
		this.dna = dna;
	}

}