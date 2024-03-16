package api;

import domain.ElectionService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.*;

@QuarkusTest
class ElectionApiTest {
    @Inject
    ElectionApi electionApi;

    @InjectMock
    ElectionService electionService;

    @Test
    void create() {
        electionApi.submit();

        verify(electionService).submit();
        verifyNoMoreInteractions(electionService);
    }
}