package br.com.ehgm.application.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ehgm.domain.Stats;

public class StatsResponse {

	private Long countSimianDna;
	private Long countHumanDna;
	private BigDecimal ratio;
	
	public StatsResponse(Stats stats) {
		this.countSimianDna = stats.getSimianCount();
		this.countHumanDna = stats.getHumanCount();
		this.ratio = stats.getRatio();
	}
	
	@JsonProperty("count_simian_dna")
	public Long getCountSimianDna() {
		return countSimianDna;
	}

	@JsonProperty("count_human_dna")
	public Long getCountHumanDna() {
		return countHumanDna;
	}

	@JsonProperty("ratio")
	public BigDecimal getRatio() {
		return ratio;
	}
}