package eriks.csa.api;

import eriks.csa.api.dto.AngelDtoIn;
import eriks.csa.domain.CSAService;
import eriks.csa.domain.OPAService;
import eriks.csa.domain.obj.Match;
import eriks.csa.domain.obj.Member;
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

@Path("/opa/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OPAAdminResource {

    @Inject
    OPAService opaService;
    @Inject
    CSAService csaService;

    @Path("/member/save-angel")
    @PUT
    public Response saveAngel(AngelDtoIn angelDtoIn) {
        opaService.saveAngels(angelDtoIn.newAngels, angelDtoIn.oldAngels);
        return Response.accepted().build();
    }

    @Path("/member")
    @POST
    public Response saveMember(Member member, @Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        opaService.saveMember(member);
        return Response.accepted().build();
    }

    @Path("/member")
    @GET
    public List<Member> getMembers(@Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        return opaService.getAllMembers();
    }

    @Path("/match")
    @POST
    public Response saveMatch(List<Match> match, @Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        match.forEach(m -> opaService.saveOrUpdateMatch(m));
        return Response.accepted().build();
    }

    @Path("/match")
    @GET
    public List<Match> getAllMatches(@Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        return opaService.getAllMatches();
    }

    @Path("/fetch-packages")
    @GET
    public List<OPAPackage> fetchPackage(@Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        return csaService.getAllPackages();
    }

    @Path("/user-cleanup/{userId}")
    @PUT
    public Response userCleanUp(@RestPath String userId, @Context HttpHeaders headers) {
        validateAdminKey(headers.getHeaderString("adminKey"));
        csaService.userCleanUp(userId);
        return Response.accepted().build();
    }

    private void validateAdminKey(String key) {
        if (!key.equals(System.getenv("ADMIN_KEY"))) {
            throw new UnauthorizedException("Invalid admin key");
        }
    }
}
