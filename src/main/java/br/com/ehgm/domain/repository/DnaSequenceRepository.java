package br.com.ehgm.domain.repository;

import java.util.List;

import br.com.ehgm.domain.DnaSequence;

public interface DnaSequenceRepository {

	public DnaSequence findBySequence(String sequence);
	
	public void save(String sequence, boolean isSimio);

	public List<Object[]> countSimianAndHuman();
}
