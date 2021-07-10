package br.com.ehgm.domain.service;

import java.util.List;

import br.com.ehgm.domain.exception.DnaSequenceException;

public interface SimioService {

	boolean isSimian(List<String> sequence) throws DnaSequenceException;
}
