package eriks.csa.api;

import eriks.csa.api.dto.AlbumValueUpdateIn;
import eriks.csa.api.dto.PackOpenDtoIn;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/csa/metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CSAMetricsResource extends AuthenticatedResource {

    @Path("/pack-open")
    @POST
    public Response openPack(String payload, @Context HttpHeaders headers) {
        service.validateToken(headers.getHeaderString("token"));
        PackOpenDtoIn packOpenDtoIn = validate(payload, headers, PackOpenDtoIn.class);
        service.savePackOpen(packOpenDtoIn.toDomain());
        return Response.accepted().build();
    }

    @Path("/album-value")
    @PUT
    public Response albumValueUpdate(String payload, @Context HttpHeaders headers) {
        service.validateToken(headers.getHeaderString("token"));
        AlbumValueUpdateIn albumValueUpdateIn = validate(payload, headers, AlbumValueUpdateIn.class);
        service.updateAlbumValue(albumValueUpdateIn.toDomain());
        return Response.accepted().build();
    }

    @Path("/all")
    @GET
    public String all() {
        return service.buildFriendlyMetrics();
    }
}
