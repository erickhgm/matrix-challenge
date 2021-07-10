package br.com.ehgm.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.ehgm.domain.DnaSequence;
import br.com.ehgm.domain.exception.DnaSequenceException;
import br.com.ehgm.domain.repository.DnaSequenceRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
class SimioServiceTest {

	@Inject
	SimioService simioService;
	
	@InjectMock
	DnaSequenceRepository dnaSequenceRepository;
	
	@Test
	void testIsSimianTrueNotInDataBase() {
		List<String> sequence = List.of("AAAA", "CCCC", "GGGG", "TTTT");
		String sequenceHash = sequence.stream().collect(Collectors.joining("-"));
		Mockito.when(dnaSequenceRepository.findBySequence(sequenceHash)).thenThrow(NoResultException.class);
		
		Assertions.assertDoesNotThrow(() ->{
			boolean simian = simioService.isSimian(sequence);
			Assertions.assertTrue(simian);
		});
	}
	
	@Test
	void testIsSimianTrueInDataBase() {
		List<String> sequence = List.of("AAAA", "CCCC", "GGGG", "TTTT");
		String sequenceHash = sequence.stream().collect(Collectors.joining("-"));
		DnaSequence dnaSequence = new DnaSequence(sequenceHash, true);
		Mockito.when(dnaSequenceRepository.findBySequence(sequenceHash)).thenReturn(dnaSequence);
		
		Assertions.assertDoesNotThrow(() ->{
			boolean simian = simioService.isSimian(sequence);
			Assertions.assertTrue(simian);
		});
	}
	
	@Test
	void testIsSimianFalseNotInDataBase() {
		List<String> sequence = List.of("ATAA", "CGCC", "GCGG", "TATT");
		String sequenceHash = sequence.stream().collect(Collectors.joining("-"));
		Mockito.when(dnaSequenceRepository.findBySequence(sequenceHash)).thenThrow(NoResultException.class);
		
		Assertions.assertDoesNotThrow(() ->{
			boolean simian = simioService.isSimian(sequence);
			Assertions.assertFalse(simian);
		});
	}
	
	@Test
	void testIsSimianFalseInDataBase() {
		List<String> sequence = List.of("ATAA", "CGCC", "GCGG", "TATT");
		String sequenceHash = sequence.stream().collect(Collectors.joining("-"));
		DnaSequence dnaSequence = new DnaSequence(sequenceHash, false);
		Mockito.when(dnaSequenceRepository.findBySequence(sequenceHash)).thenReturn(dnaSequence);
		
		Assertions.assertDoesNotThrow(() ->{
			boolean simian = simioService.isSimian(sequence);
			Assertions.assertFalse(simian);
		});
	}
	
	@Test
	void testIsSimianInvalidLetter() {
		List<String> sequence = List.of("ATAA", "CGCC", "GFGG", "TATT");
		
		Assertions.assertThrows(DnaSequenceException.class, () ->{
			simioService.isSimian(sequence);
		});
	}
	
	@Test
	void testIsSimianInvalidMatrixSize() {
		List<String> sequence = List.of("ATAA", "CGCC", "GCGG", "TAT");
		
		Assertions.assertThrows(DnaSequenceException.class, () ->{
			simioService.isSimian(sequence);
		});
	}
	
}
