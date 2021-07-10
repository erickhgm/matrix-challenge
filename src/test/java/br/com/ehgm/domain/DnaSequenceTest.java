package br.com.ehgm.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.ehgm.domain.exception.DnaSequenceException;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class DnaSequenceTest {

	@Test
	void testValidateLettersOk() {
		List<String> validLetters = new ArrayList<>(Arrays.asList(
				"CTGAGA", 
				"CTATGC", 
				"TATTGT", 
				"AGAGGG", 
				"CCCCTA", 
				"TCACTG"));
		
		DnaSequence dnaSequence = new DnaSequence(validLetters);
		Assertions.assertDoesNotThrow(() -> {
			boolean isValid = dnaSequence.validateLetters();
			Assertions.assertTrue(isValid);
		});
	}

	@Test
	void testValidateLettersNotOk() {
		List<String> invalidLetters = new ArrayList<>(Arrays.asList(
				"CTGAGA", 
				"CTATGC", 
				"TATZGT", 
				"AGAGGG", 
				"CCCCTA", 
				"TCACTG"));
		
		DnaSequence dnaSequence = new DnaSequence(invalidLetters);
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			dnaSequence.validateLetters();
		});
	}

	@Test
	void testValidateMatrixSize4x4() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGA", 
				"CTAT", 
				"TATT", 
				"AGAG"));
		
		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			boolean isValid = dnaSequence.validateMatrixSize();
			Assertions.assertTrue(isValid);
		});
	}

	@Test
	void testValidateMatrixSize6x6() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGAGA", 
				"CTATGC",
				"TATTGT", 
				"AGAGGG", 
				"CCCCTA", 
				"TCACTG"));
		
		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			dnaSequence.validateMatrixSize();
		});
	}

	@Test
	void testValidateMatrixSize3x3() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTG", 
				"CTA", 
				"TAT"));
		
		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			dnaSequence.validateMatrixSize();
		});
	}

	@Test
	void testValidateMatrixDifferentSizeNxN() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGA", 
				"CTAT", 
				"TATTGT", 
				"AGA"));
		
		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			dnaSequence.validateMatrixSize();
		});
	}
	
	@Test
	void testValidateMatrixDifferentRowSize() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGA", 
				"CTAT", 
				"TATTGT", 
				"AGA",
				"AGA"));
		
		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			dnaSequence.validateMatrixSize();
		});
	}

	@Test
	void testValidateMatrixEmpty() {
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			DnaSequence dnaSequence = new DnaSequence(new ArrayList<>());
			dnaSequence.validateMatrixSize();
		});
	}

	@Test
	void testValidateMatrixNull() {
		Assertions.assertThrows(DnaSequenceException.class, () -> {
			DnaSequence dnaSequence = new DnaSequence(null);
			dnaSequence.validateMatrixSize();
		});
	}

	@Test
	void testCountHorizontalZeroPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGA", 
				"CTAT", 
				"TATT", 
				"AGAG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(0, dnaSequence.countHorizontalPattern());
		});
	}

	@Test
	void testCountHorizontalOnePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGAC", 
				"CTATC", 
				"TATTC", 
				"AGAGA", 
				"CCCCT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(1, dnaSequence.countHorizontalPattern());
		});
	}

	@Test
	void testCountHorizontalTwoPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTTTTA", 
				"CTATGC", 
				"TATATT", 
				"AGAGGG", 
				"CCCCTA",
				"CTGTTA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(2, dnaSequence.countHorizontalPattern());
		});
	}

	@Test
	void testCountHorizontalThreePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CGGGGAAA", 
				"CTATGCAA", 
				"TATGTTAA", 
				"AGAGGGAA", 
				"CCCCTTTT",
				"AGAGGGAA",
				"CGTGGAAA", 
				"CTATGCAA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(3, dnaSequence.countHorizontalPattern());
		});
	}

	@Test
	void testCountVerticalZeroPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGA", 
				"CTAT", 
				"TATT", 
				"AAGT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(0, dnaSequence.countVerticalPattern());
		});
	}

	@Test
	void testCountVerticalOnePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGATA", 
				"ATATGC", 
				"AATTGT", 
				"AGAGGG", 
				"CCCCTA",
				"AGAGGG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(1, dnaSequence.countVerticalPattern());
		});
	}

	@Test
	void testCountVerticalTwoPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGATA", 
				"ATGTGC", 
				"AAGTGT", 
				"AGGGGG", 
				"CCCCTA",
				"ATGATA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(2, dnaSequence.countVerticalPattern());
		});
	}

	@Test
	void testCountVerticalThreePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGATATT", 
				"CTGTGCGG", 
				"AAGTTTCC", 
				"CGGGGGAA",
				"AGTGTAGG",
				"CGTTGGCC",
				"AGTGGGAA",
				"CCTCTATT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(3, dnaSequence.countVerticalPattern());
		});
	}

	@Test
	void testCountMainDiagonalZeroPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"AAAA", 
				"CCCC", 
				"GGGG", 
				"TTTT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(0, dnaSequence.countMainDiagonalPattern());
		});
	}

	@Test
	void testCountMainDiagonaOnelPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGT", 
				"CGTA", 
				"ATGC", 
				"TGTA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(1, dnaSequence.countMainDiagonalPattern());
		});
	}

	@Test
	void testCountMainDiagonalTwoPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGTA", 
				"CGTGA", 
				"ATGCA", 
				"TGTAA", 
				"GGTAA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(2, dnaSequence.countMainDiagonalPattern());
		});
	}

	@Test
	void testCountMainDiagonalThreePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGTGA", 
				"CGTGAG", 
				"ATGAAT", 
				"TGAATG", 
				"TGAATG", 
				"ATGTGA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(3, dnaSequence.countMainDiagonalPattern());
		});
	}

	@Test
	void testCountMainDiagonalSevenPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGTGAAT", 
				"CGTGAGTG", 
				"ATGAATAT", 
				"TGAATGTG", 
				"TGAATGTG", 
				"ATATGAAT", 
				"CATGAGCG", 
				"ATGAATAT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(7, dnaSequence.countMainDiagonalPattern());
		});
	}

	@Test
	void testCountAntiDiagonalZeroPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"AAAA", 
				"CCCC", 
				"GGGG", 
				"TTTT"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(0, dnaSequence.countAntiDiagonalPattern());
		});
	}

	@Test
	void testCountAntiDiagonalOnePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGAC", 
				"AAATG", 
				"AAATA", 
				"AGAAT", 
				"CTCCG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(1, dnaSequence.countAntiDiagonalPattern());
		});
	}

	@Test
	void testCountAntiDiagonalTwoPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATGATA", 
				"CAATGC", 
				"ACATGT", 
				"AGCAGG", 
				"CTCCTA",
				"CTCCTA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(2, dnaSequence.countAntiDiagonalPattern());
		});
	}

	@Test
	void testCountAntiDiagonalThreePattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"ATTATA", 
				"CAATGC", 
				"ACATTT", 
				"AGCAGT", 
				"CTCCTA",
				"CTCCTA"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(3, dnaSequence.countAntiDiagonalPattern());
		});

	}

	@Test
	void testCountAntiDiagonalSevenPattern() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"TTTATAAG", 
				"ATATGTAG", 
				"AATTTTTG", 
				"CGATCTAT", 
				"GCCAGCAG",
				"TGACTGCG",
				"CTGCTAGC",
				"CTCGTAAG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			Assertions.assertEquals(7, dnaSequence.countAntiDiagonalPattern());
		});
	}

	@Test
	void testCountAllSequenceDirections() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"TTTTTAAG", 
				"ATACGTAT", 
				"AATCCCCG", 
				"CGGGCTAG", 
				"GCCAGCAG",
				"TGAATGCG",
				"CAGATAGC",
				"ATCATAAG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);

		Assertions.assertDoesNotThrow(() -> {
			int count = dnaSequence.countHorizontalPattern();
			Assertions.assertEquals(2, count);

			count = dnaSequence.countVerticalPattern();
			Assertions.assertEquals(2, count);

			count = dnaSequence.countMainDiagonalPattern();
			Assertions.assertEquals(1, count);

			count = dnaSequence.countAntiDiagonalPattern();
			Assertions.assertEquals(2, count);
		});
	}

	@Test
	void testIsNotSimiam() {
		List<String> sequence = new ArrayList<>(Arrays.asList(
				"CTGAGA", 
				"GTATAC", 
				"CATTGT", 
				"CGAGGG", 
				"GCCCTA", 
				"TCACTG"));

		DnaSequence dnaSequence = new DnaSequence(sequence);
		Assertions.assertDoesNotThrow(() -> {
			boolean isSimian = dnaSequence.isSimio();
			Assertions.assertFalse(isSimian);
		});
	}

	@Test
	void testToString() {
		DnaSequence dnaSequence = new DnaSequence(Arrays.asList("CTGATA", "CTATGC"));
		Assertions.assertEquals("CTGATA-CTATGC", dnaSequence.toString());
	}
}
