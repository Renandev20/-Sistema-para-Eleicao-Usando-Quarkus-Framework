package domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static domain.ElectionCacheTest.ELECTION_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ElectionServiceTest {
    @Inject
    ElectionService service;

    @InjectMock
    ElectionRepository repository;

    @Test
    void findAll() {
        List<Election> elections = Instancio.stream(Election.class).limit(10).toList();

        when(repository.findAll()).thenReturn(elections);

        List<Election> result = service.findAll();

        verify(repository).findAll();
        verifyNoMoreInteractions(repository);

        assertEquals(elections, result);
    }

    @Test
    void vote() {
        Election election = new Election(ELECTION_ID, Instancio.stream(Candidate.class).limit(10).toList());
        Candidate candidate = election.candidates().get(0);

        when(repository.findById(ELECTION_ID)).thenReturn(election);

        service.vote(ELECTION_ID, candidate.id());

        verify(repository).findById(ELECTION_ID);
        verify(repository).vote(ELECTION_ID, candidate);
        verifyNoMoreInteractions(repository);
    }
}