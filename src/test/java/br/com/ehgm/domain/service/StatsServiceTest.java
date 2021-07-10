package br.com.ehgm.domain.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.ehgm.domain.Stats;
import br.com.ehgm.domain.repository.DnaSequenceRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
class StatsServiceTest {

	@Inject
	StatsService statsService;
	
	@InjectMock
	DnaSequenceRepository dnaSequenceRepository;
	
	@Test
	void testCountSimianAndHumanEmpty() {
		List<Object[]> elms = new ArrayList<>();
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(0L, stats.getHumanCount());
		Assertions.assertEquals(0L, stats.getSimianCount());
		Assertions.assertEquals(BigDecimal.ZERO, stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanRatio30() {
		List<Object[]> elms = List.of(new Object[] { false, 100 }, new Object[] { true, 30 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(100L, stats.getHumanCount());
		Assertions.assertEquals(30L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("0.3"), stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanRatio50() {
		List<Object[]> elms = List.of(new Object[] { false, 100 }, new Object[] { true, 50 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(100L, stats.getHumanCount());
		Assertions.assertEquals(50L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("0.5"), stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanRatio100() {
		List<Object[]> elms = List.of(new Object[] { false, 100 }, new Object[] { true, 100 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(100L, stats.getHumanCount());
		Assertions.assertEquals(100L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("1.0"), stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanRatio150() {
		List<Object[]> elms = List.of(new Object[] { false, 100 }, new Object[] { true, 150 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(100L, stats.getHumanCount());
		Assertions.assertEquals(150L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("1.5"), stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanZeroHuman() {
		List<Object[]> elms = List.of(new Object[] { false, 0 }, new Object[] { true, 50 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(0L, stats.getHumanCount());
		Assertions.assertEquals(50L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("0"), stats.getRatio());
	}
	
	@Test
	void testCountSimianAndHumanOnlyHuman() {
		List<Object[]> elms = new ArrayList<>();
		elms.add(new Object[] { false, 10 });
		Mockito.when(dnaSequenceRepository.countSimianAndHuman()).thenReturn(elms);
		
		Stats stats = statsService.getStats();
		
		Assertions.assertEquals(10L, stats.getHumanCount());
		Assertions.assertEquals(0L, stats.getSimianCount());
		Assertions.assertEquals(new BigDecimal("0.0"), stats.getRatio());
	}
}
