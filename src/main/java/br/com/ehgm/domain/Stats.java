package br.com.ehgm.domain;

import java.math.BigDecimal;

public class Stats {

	private Long simianCount;
	private Long humanCount;
	private BigDecimal ratio;

	public Long getSimianCount() {
		return simianCount;
	}

	public void setSimianCount(Long simianCount) {
		this.simianCount = simianCount;
	}

	public Long getHumanCount() {
		return humanCount;
	}

	public void setHumanCount(Long humanCount) {
		this.humanCount = humanCount;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
}
