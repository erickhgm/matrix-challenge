package br.com.ehgm.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import br.com.ehgm.domain.exception.DnaSequenceException;

public class DnaSequence {

	private static final Logger LOG = Logger.getLogger(DnaSequence.class);
	
	private static final List<String> DNA_VALID_LETTERS = new ArrayList<>(List.of("A", "T", "C", "G"));
	private static final int NUMBER_OF_LETTERS = 4;
	
	private final List<String> sequences;
	private String[][] matrix;
	
	private boolean isSimio = false;
	private boolean isLettersValidated = false;
	private boolean isMatrixSizeValidated = false;
	
	public DnaSequence(List<String> sequence) {
		this.sequences = sequence;
	}
	
	public DnaSequence(String sequenceStr, boolean isSimian) {
		this.sequences = Arrays.asList(sequenceStr.split("-"));
		this.isSimio = isSimian;
	}
	
	public int countHorizontalPattern() throws DnaSequenceException {
		checkValidations();
		
		int patternCount = 0;
		
		// Loop in each row of the matrix
		for (int r = 0; r < this.matrix.length; r++) {
			String[] row = this.matrix[r];
			LOG.infov("Analyzing horizontal sequence: {0}", Arrays.deepToString(row));

			String previousLetter = "";
			Map<String, Integer> counters = buildCounters(DNA_VALID_LETTERS);

			// Loop in each letter of the row 'r'
			for (int c = 0; c < row.length; c++) {
				String currentLetter = row[c];
				LOG.debugv("Row letter: {0}", row[c]);

				setCounters(counters, previousLetter, currentLetter);
				patternCount += checkPatternNumber(counters);
				previousLetter = currentLetter;
			}
		}

		LOG.infov("{0} horizontal sequences found", patternCount);
		return patternCount;
	}

	public int countVerticalPattern() throws DnaSequenceException {
		checkValidations();
		
		int patternCount = 0;
		int width = this.matrix[0].length;

		// Loop in each column of the matrix
		for (int c = 0; c < width; c++) {

			// Loop in each row of the column 'c'
			List<String> columnLetters = new ArrayList<>();
			for (int r = 0; r < this.matrix.length; r++) {
				columnLetters.add(this.matrix[r][c]);
			}
			LOG.infov("Analyzing vertical sequence: {0}", columnLetters);

			String previousLetter = "";
			Map<String, Integer> counters = buildCounters(DNA_VALID_LETTERS);

			// Loop in each letter of the column 'c'
			for (String currentLetter : columnLetters) {
				LOG.debugv("Column letter: {0}", currentLetter);

				setCounters(counters, previousLetter, currentLetter);
				patternCount += checkPatternNumber(counters);
				previousLetter = currentLetter;
			}
		}

		LOG.infov("{0} vertical sequences found", patternCount);
		return patternCount;
	}

	public int countMainDiagonalPattern() throws DnaSequenceException {
		checkValidations();
		
		int patternCount = 0;
		int height = this.matrix.length;
		int width = this.matrix[0].length;
		int diagonals = (height + width) - 2;
		
		// Limiting access to the diagonals that don't have the number of letters
		int diagonalToStart = NUMBER_OF_LETTERS - 1;
		int diagonalToFinish = diagonals - (NUMBER_OF_LETTERS - 1);

		// Loop in each diagonal calculated by the dimensions of the array
		for (int d = diagonalToStart; d <= diagonalToFinish; d++) {

			// Collect letters of the current diagonal
			List<String> diagonalLetters = new ArrayList<>();
			for (int c = 0; c <= d; c++) {
				int r = d - c;
				if (r < height && c < width) {
					diagonalLetters.add(this.matrix[r][c]);
				}
			}
			LOG.infov("Analyzing main diagonal sequence: {0}", diagonalLetters);

			String previousLetter = "";
			Map<String, Integer> counters = buildCounters(DNA_VALID_LETTERS);

			// Loop in each letter of the diagonal
			for (String currentLetter : diagonalLetters) {
				LOG.debugv("Main diagonal letter: {0}", currentLetter);

				setCounters(counters, previousLetter, currentLetter);
				previousLetter = currentLetter;

				patternCount += checkPatternNumber(counters);
			}
		}

		LOG.infov("{0} main diagonal sequences found", patternCount);
		return patternCount;
	}

	public int countAntiDiagonalPattern() throws DnaSequenceException {
		checkValidations();
		
		int patternCount = 0;
		int height = this.matrix.length;
		int width = this.matrix[0].length;
		int diagonals = (height + width) - 2;
		
		// Limiting access to the diagonals that don't have the number of letters
		int diagonalToStart = NUMBER_OF_LETTERS - 1;
		int diagonalToFinish = diagonals - (NUMBER_OF_LETTERS - 1);

		// Loop in each diagonal calculated by the dimensions of the array
		for (int d = diagonalToStart; d <= diagonalToFinish; d++) {
			
			// Collect letters of the current diagonal
			List<String> diagonalLetters = new ArrayList<>();
			for (int c = 0, k = 0; c <= d; c++, k++) {
				int r = (width - 1) - d + k;
				if (r >= 0 && r < height && c < width) {
					diagonalLetters.add(this.matrix[r][c]);
				}
			}
			LOG.infov("Analyzing anti diagonal sequence: {0}", diagonalLetters);

			String previousLetter = "";
			Map<String, Integer> counters = buildCounters(DNA_VALID_LETTERS);

			// Loop in each letter of the diagonal
			for (String currentLetter : diagonalLetters) {
				LOG.debugv("Anti diagonal letter: {0}", currentLetter);

				setCounters(counters, previousLetter, currentLetter);
				previousLetter = currentLetter;

				patternCount += checkPatternNumber(counters);
			}
		}

		LOG.infov("{0} anti diagonal sequences found", patternCount);
		return patternCount;
	}

	/**
	 * Looking for the fourth occurrence of some letter. If found, reset it's count and return 1. 
	 * If not found return 0.
	 * 
	 * @param counters
	 * @return
	 */
	private int checkPatternNumber(Map<String, Integer> counters) {
		int patternCount = 0;
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			if (entry.getValue().equals(NUMBER_OF_LETTERS)) {
				LOG.infov("Found {0} sequences of the letter: {1}", entry.getValue(), entry.getKey());
				counters.put(entry.getKey(), 0);
				patternCount++;
				break;
			}
		}
		return patternCount;
	}

	/**
	 * Checking if the previous letter is the same as the current one and increment it's counter.
	 * If not equal, reset the previous letter counter and increment the current one.
	 * 
	 * @param counters
	 * @param previousLetter
	 * @param currentLetter
	 */
	private void setCounters(Map<String, Integer> counters, final String previousLetter, final String currentLetter) {
		if (currentLetter.equals(previousLetter) || previousLetter.isEmpty()) {
			counters.merge(currentLetter, 1, Integer::sum);
		} else {
			counters.put(previousLetter, 0);
			counters.put(currentLetter, 1);
		}
	}

	/**
	 * Returns a map with zero for each letter (key) in the list
	 * 
	 * @param letters
	 * @return
	 */
	private Map<String, Integer> buildCounters(final List<String> letters) {
		Map<String, Integer> counters = new HashMap<>();
		for (String letter : letters) {
			counters.put(letter, 0);
		}
		return counters;
	}

	/**
	 * Check if DNA sequence is valid before do the sequence count analysis
	 * 
	 * @return
	 * @throws DnaSequenceException
	 */
	private void checkValidations() throws DnaSequenceException {
		if (!this.isLettersValidated)
			validateLetters();
		
		if (!this.isMatrixSizeValidated)
			validateMatrixSize();
		
		if (this.matrix == null) {
			this.matrix = new String[this.sequences.size()][this.sequences.get(0).length()];
			for (int i = 0; i < this.sequences.size(); i++) {
				this.matrix[i] = this.sequences.get(i).split("");
			}
		}
	}
	
	/**
	 * All rows must be the same size, a square NxN and at least 4 letters per row
	 * 
	 * @param sequences
	 * @return
	 * @throws DnaSequenceException 
	 */
	public boolean validateMatrixSize() throws DnaSequenceException {
		if (this.sequences == null || this.sequences.isEmpty())
			throw new DnaSequenceException("Invalid sequences: null or empty");

		int height = this.sequences.size();
		int width = this.sequences.get(0).length();
		
		if (height != width)
			throw new DnaSequenceException("Invalid sequences: needs to be a square NxN");
		
		if (width < 4)
			throw new DnaSequenceException("Invalid sequences: must be at least 4 letters in each row");

		boolean hasDifferentSize = this.sequences.stream().map(String::trim).anyMatch(l -> l.length() != width);
		if (hasDifferentSize)
			throw new DnaSequenceException("Invalid sequences: rows with different sizes");

		LOG.info("DNA sequence has valid size");
		this.isMatrixSizeValidated = true;
		return true;
	}

	/**
	 * All letters need be a valid letter
	 * 
	 * @return
	 * @throws DnaSequenceException
	 */
	public boolean validateLetters() throws DnaSequenceException {
		// Put all letters in one List 
		List<String> allLetters = this.sequences.stream()
				.map(l -> l.split(""))
				.flatMap(Arrays::stream)
				.collect(Collectors.toList());

		// UpperCase in all letters and check if all are valid letters
		boolean hasInvalidLetter = allLetters.stream()
				.map(String::toUpperCase)
				.anyMatch(Predicate.not(DNA_VALID_LETTERS::contains));

		if (hasInvalidLetter)
			throw new DnaSequenceException(String.format("DNA sequence %s has invalid letters", sequences));

		LOG.info("DNA sequence has valid letters");
		this.isLettersValidated = true;
		return true;
	}
	
	public boolean isSimio() {
		return this.isSimio;
	}
	
	public void setSimio(boolean isSimian) {
		this.isSimio = isSimian;
	}
	
	/**
	 * Add a '-' between each sequence and return all sequences as String
	 */
	public String toString() {
		return this.sequences.stream().map(String::toUpperCase).collect(Collectors.joining("-"));
	}
}
