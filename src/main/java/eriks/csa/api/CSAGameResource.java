package eriks.csa.api;

import eriks.csa.api.dto.LoginDtoIn;
import eriks.csa.api.dto.LoginDtoOut;
import eriks.csa.api.dto.SignUpDtoIn;
import eriks.csa.domain.CSAService;
import eriks.csa.domain.obj.Login;
import eriks.csa.domain.obj.OPAPackage;
import io.quarkus.security.UnauthorizedException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/csa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CSAGameResource extends AuthenticatedResource {

    @Inject
    CSAService service;

    @Path("/ping")
    @GET
    public Response ping() {
        return Response.ok().build();
    }

    @Path("/sign-up")
    @POST
    public Response signUp(String payload) {
        try {
            SignUpDtoIn signUpDtoIn = deserialize(payload, SignUpDtoIn.class);
            Login login = service.signUp(signUpDtoIn.userName, signUpDtoIn.password);
            return Response.accepted().entity(LoginDtoOut.fromDomain(login)).build();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/login")
    @POST
    public Response login(String payload) {
        try {
            LoginDtoIn loginDtoIn = deserialize(payload, LoginDtoIn.class);
            Login login = service.login(loginDtoIn.userId, loginDtoIn.password);
            return Response.accepted().entity(LoginDtoOut.fromDomain(login)).build();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/fetch-packages/{userName}")
    @GET
    public List<OPAPackage> fetchPackage(@RestPath String userName, @Context HttpHeaders headers) {
        service.validateToken(headers.getHeaderString("token"));
        return service.getPackagesByUser(userName);
    }

    @Path("/version")
    @GET
    public List<String> getAvailableVersions() {
        return List.of("1.7.1");
    }
}
