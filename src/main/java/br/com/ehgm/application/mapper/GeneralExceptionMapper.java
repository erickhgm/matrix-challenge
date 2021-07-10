package br.com.ehgm.application.mapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import br.com.ehgm.application.response.MessageResponse;

@Provider
public class GeneralExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

	private static final Logger LOG = Logger.getLogger(GeneralExceptionMapper.class.getName());

	@Context
	UriInfo uriInfo;

	@Override
	public Response toResponse(Exception ex) {
		StatusType status = null;

		if (ex instanceof WebApplicationException) {
			status = ((WebApplicationException) ex).getResponse().getStatusInfo();
			LOG.warn(ex.getMessage(), ex);
		
		} else {
			status = Status.INTERNAL_SERVER_ERROR;
			LOG.error(ex.getMessage(), ex);
		}

		return Response.status(status)
				.type(MediaType.APPLICATION_JSON)
				.entity(new MessageResponse(ex))
				.build();
	}
}
