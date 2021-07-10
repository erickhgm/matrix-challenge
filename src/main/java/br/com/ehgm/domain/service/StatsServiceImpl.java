package br.com.ehgm.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import br.com.ehgm.domain.Stats;
import br.com.ehgm.domain.repository.DnaSequenceRepository;

@ApplicationScoped
public class StatsServiceImpl implements StatsService {

	private static final Logger LOG = Logger.getLogger(StatsServiceImpl.class);
	
	private final DnaSequenceRepository dnaSequenceRepository;

	public StatsServiceImpl(DnaSequenceRepository dnaSequenceRepository) {
		this.dnaSequenceRepository = dnaSequenceRepository;
	}
	
	@Override
	public Stats getStats() {
		LOG.info("Getting stats");
		
		Long humanCount = 0L;
		Long simianCount = 0L;
		BigDecimal ratio = BigDecimal.ZERO;
		
		List<Object[]> counters = dnaSequenceRepository.countSimianAndHuman();
		
		if (!counters.isEmpty())
			humanCount = Long.parseLong(counters.get(0)[1].toString());
		
		if (!counters.isEmpty() && counters.size() > 1)
			simianCount = Long.parseLong(counters.get(1)[1].toString());
		
		if (humanCount > 0L)
			ratio = BigDecimal.valueOf(simianCount).divide(BigDecimal.valueOf(humanCount), 1, RoundingMode.HALF_UP);
		
		Stats stats = new Stats();
		stats.setHumanCount(humanCount);
		stats.setSimianCount(simianCount);
		stats.setRatio(ratio);
		
		LOG.infov("Stats found: human {0}, simian {1}, ratio {2}", humanCount, simianCount, ratio);
		return stats;
	}
}
