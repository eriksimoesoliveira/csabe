package eriks.csa.api;

import eriks.csa.api.dto.LoginDtoIn;
import eriks.csa.api.dto.LoginDtoOut;
import eriks.csa.api.dto.SignUpDtoIn;
import eriks.csa.domain.obj.Login;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/csa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CSAGameResource extends AuthenticatedResource {

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

}
