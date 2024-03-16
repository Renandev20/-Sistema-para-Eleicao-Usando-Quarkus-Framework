package infrastructure.resources;

import api.ElectionApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;

import static domain.ElectionCacheTest.ELECTION_ID;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
@TestHTTPEndpoint(VotingResource.class)
class VotingResourceTest {

    public static final String CANDIDATE_ID = "candidateId";
    @InjectMock
    ElectionApi api;

    @Test
    void candidates() {
        var out = Instancio.stream(api.dto.out.Election.class).limit(4).toList();

        when(api.findAll()).thenReturn(out);

        var response = given()
                .when().get()
                .then().statusCode(RestResponse.StatusCode.OK)
                .extract().as(api.dto.out.Election[].class);

        verify(api).findAll();
        verifyNoMoreInteractions(api);

        assertEquals(out, Arrays.stream(response).toList());
    }

    @Test
    void vote() {
        given().contentType(MediaType.APPLICATION_JSON)
                .when().post("/elections/" + ELECTION_ID + "/candidates/" + CANDIDATE_ID)
                .then().statusCode(RestResponse.StatusCode.ACCEPTED);

        verify(api).vote(ELECTION_ID, CANDIDATE_ID);
        verifyNoMoreInteractions(api);
    }
}