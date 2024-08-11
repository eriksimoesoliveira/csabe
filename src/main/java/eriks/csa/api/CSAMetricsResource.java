package eriks.csa.api;

import eriks.csa.api.dto.AlbumValueUpdateIn;
import eriks.csa.api.dto.PackOpenDtoIn;
import eriks.csa.domain.CSAService;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/csa/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CSAMetricsResource extends AuthenticatedResource {

    @Inject
    CSAService service;

    @Path("/pack-open")
    @POST
    public Response openPack(String payload, @Context HttpHeaders headers) {
        try {
            service.validateToken(headers.getHeaderString("token"));
            PackOpenDtoIn packOpenDtoIn = deserialize(payload, PackOpenDtoIn.class);
            service.savePackOpen(packOpenDtoIn.toDomain());
            return Response.accepted().build();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/album-value")
    @PUT
    public Response albumValueUpdate(String payload, @Context HttpHeaders headers) {
        try {
            service.validateToken(headers.getHeaderString("token"));
            AlbumValueUpdateIn albumValueUpdateIn = deserialize(payload, AlbumValueUpdateIn.class);
            service.updateAlbumValue(albumValueUpdateIn.toDomain());
            return Response.accepted().build();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/all")
    @GET
    public String all() {
        return service.buildFriendlyMetrics();
    }
}
