package infrastructure.resources;

import api.ElectionApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@QuarkusIntegrationTest
@TestHTTPEndpoint(ElectionResource.class)
class ElectionResourceIT {
    @Test
    void submit() {

        given().contentType(MediaType.APPLICATION_JSON)
                .when().post()
                .then().statusCode(RestResponse.StatusCode.CREATED);
    }

}