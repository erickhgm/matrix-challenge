package br.com.ehgm.application;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ehgm.application.request.SimioRequest;
import br.com.ehgm.application.response.SimioResponse;
import br.com.ehgm.domain.exception.DnaSequenceException;
import br.com.ehgm.domain.service.SimioService;

@Path("/simian")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimioController {

	private final SimioService dnaSequenceService;

	public SimioController(SimioService dnaSequenceService) {
		this.dnaSequenceService = dnaSequenceService;
	}

	@POST
	public Response postSimian(SimioRequest simian) throws DnaSequenceException {
		boolean isSimian = dnaSequenceService.isSimian(simian.getDna());
		return Response.ok(new SimioResponse(isSimian)).build();
	}
}