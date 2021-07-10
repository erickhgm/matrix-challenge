package br.com.ehgm.application;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ehgm.application.response.StatsResponse;
import br.com.ehgm.domain.Stats;
import br.com.ehgm.domain.service.StatsService;

@Path("/stats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatsController {

	private final StatsService statsService;
	
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}
	
    @GET
    public Response getStats() {
    	Stats stats = statsService.getStats();
        return Response.ok(new StatsResponse(stats)).build();
    }
}