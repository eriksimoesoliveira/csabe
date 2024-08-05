package eriks.csa.api;

import eriks.csa.api.dto.LoginDtoIn;
import eriks.csa.api.dto.LoginDtoOut;
import eriks.csa.domain.Login;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/csa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CSAGameResource extends AuthenticatedResource {

    private static final Logger LOG = Logger.getLogger(CSAGameResource.class);

    @Path("/ping")
    @GET
    public Response ping() {
        return Response.ok().build();
    }

    @Path("/login")
    @POST
    public Response login(String payload, @Context HttpHeaders headers) {
        try {
            LoginDtoIn loginDtoIn = validate(payload, headers, LoginDtoIn.class);
            Login login = service.login(loginDtoIn.userId, loginDtoIn.userName, loginDtoIn.password);
            return Response.accepted().entity(LoginDtoOut.fromDomain(login)).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
