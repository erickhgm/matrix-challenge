package br.com.ehgm.infrastracture.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.ehgm.domain.DnaSequence;
import br.com.ehgm.domain.repository.DnaSequenceRepository;

@ApplicationScoped
public class DnaSequencePostgresRepository implements DnaSequenceRepository {

	private final EntityManager entityManager;
	
	public DnaSequencePostgresRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public DnaSequence findBySequence(String sequence) {
		String sql = "select s from DnaSequenceEntity s where s.sequence = : sequence";
		TypedQuery<DnaSequenceEntity> query = entityManager.createQuery(sql, DnaSequenceEntity.class);
		query.setParameter("sequence", sequence);
		DnaSequenceEntity seq = query.getSingleResult();
		return new DnaSequence(seq.getSequence(), seq.isSimian());
	}
	
	public void save(String sequence, boolean isSimio) {
		DnaSequenceEntity seq = new DnaSequenceEntity(sequence, isSimio);
		entityManager.persist(seq);
	}
	
	public List<Object[]> countSimianAndHuman() {
		String sql = "select s.simian, count(s) from DnaSequenceEntity s group by s.simian order by s.simian";
		TypedQuery<Object[]> query = entityManager.createQuery(sql, Object[].class);
		return query.getResultList();
	}
}
