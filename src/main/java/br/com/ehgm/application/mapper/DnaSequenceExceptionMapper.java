package br.com.ehgm.application.mapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.ehgm.application.response.MessageResponse;
import br.com.ehgm.domain.exception.DnaSequenceException;

@Provider
public class DnaSequenceExceptionMapper implements ExceptionMapper<DnaSequenceException> {

	private static final Logger LOG = Logger.getLogger(DnaSequenceExceptionMapper.class.getName());

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse(DnaSequenceException ex) {
		LOG.info(ex.getMessage(), ex);
		
		return Response.status(Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new MessageResponse(ex))
				.build();
	}
}
