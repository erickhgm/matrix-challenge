package br.com.ehgm.infrastracture.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "dna_sequence", indexes = @Index(name = "idx_dna_sequence", columnList = "sequence"))
public class DnaSequenceEntity {

	public DnaSequenceEntity() { }
	
	public DnaSequenceEntity(String sequence, boolean isSimian) {
		this.sequence = sequence;
		this.simian = isSimian;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sequence", nullable = false, unique = true, columnDefinition="VARCHAR")
	private String sequence;

	@Column(name = "is_simian", nullable = false)
	private boolean simian;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isSimian() {
		return simian;
	}

	public void setSimian(boolean simian) {
		this.simian = simian;
	}
}
