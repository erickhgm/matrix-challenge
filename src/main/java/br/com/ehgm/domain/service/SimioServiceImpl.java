package br.com.ehgm.domain.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.com.ehgm.domain.DnaSequence;
import br.com.ehgm.domain.exception.DnaSequenceException;
import br.com.ehgm.domain.repository.DnaSequenceRepository;

@ApplicationScoped
public class SimioServiceImpl implements SimioService {

	private static final Logger LOG = Logger.getLogger(SimioServiceImpl.class);
	private static final int NUMBER_OF_SEQUENCES = 2;
	
	private final DnaSequenceRepository dnaSequenceRepository;

	public SimioServiceImpl(DnaSequenceRepository dnaSequenceRepository) {
		this.dnaSequenceRepository = dnaSequenceRepository;
	}

	@Override
	@Transactional
	public boolean isSimian(List<String> sequences) throws DnaSequenceException {
		LOG.infov("DnaSequence {0} received", sequences);
		
		DnaSequence dnaSequence = new DnaSequence(sequences);
		dnaSequence.validateLetters();
		dnaSequence.validateMatrixSize();
		
		boolean isSimio = false;
		String sequenceHash = dnaSequence.toString();
		
		try {
			dnaSequence = dnaSequenceRepository.findBySequence(sequenceHash);
			LOG.info("DNA sequence already exist in database");
			isSimio = dnaSequence.isSimio();

		} catch (NoResultException e) {
			LOG.info("DNA sequence not found in database");
			
			int patternCount = 0;
			patternCount += dnaSequence.countHorizontalPattern();
			patternCount += dnaSequence.countVerticalPattern();
			patternCount += dnaSequence.countMainDiagonalPattern();
			patternCount += dnaSequence.countAntiDiagonalPattern();
			LOG.infov("Total of DNA sequences found: {0}", patternCount);

			if (patternCount >= NUMBER_OF_SEQUENCES) {
				isSimio = true;
				dnaSequence.setSimio(isSimio);
				LOG.infov("DNA sequences is simio");
			}
			
			dnaSequenceRepository.save(sequenceHash, isSimio);
			LOG.info("DNA sequences saved");
		}
		return isSimio;
	}
}
