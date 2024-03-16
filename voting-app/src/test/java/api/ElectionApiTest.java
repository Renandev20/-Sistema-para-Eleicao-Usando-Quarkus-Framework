package api;

import domain.Candidate;
import domain.Election;
import domain.ElectionService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static domain.ElectionCacheTest.ELECTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ElectionApiTest {
    @Inject
    ElectionApi electionApi;

    @InjectMock
    ElectionService electionService;

    @Test
    void findAll() {
        var elections = Instancio.stream(Election.class).limit(10).toList();

        when(electionService.findAll()).thenReturn(elections);

        var response = electionApi.findAll();

        verify(electionService).findAll();
        verifyNoMoreInteractions(electionService);

        assertEquals(elections.stream().map(api.dto.out.Election::fromDomain).toList(), response);
    }

    @Test
    void vote() {
        Election election = new Election(ELECTION_ID, Instancio.stream(Candidate.class).limit(10).toList());
        Candidate candidate = election.candidates().get(0);

        electionApi.vote(election.id(), candidate.id());

        verify(electionService).vote(election.id(), candidate.id());
        verifyNoMoreInteractions(electionService);
    }
}