package domain;

import domain.annotations.SQL;
import infrastructure.repositories.RedisElectionRepository;
import infrastructure.repositories.SQLElectionRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import static org.mockito.Mockito.*;

@QuarkusTest
class ElectionServiceTest {
    @Inject
    ElectionService service;

    @InjectMock
    CandidateService candidateService;

    @InjectMock
    RedisElectionRepository redisElectionRepository;

    @InjectMock
    @SQL
    SQLElectionRepository sqlElectionRepository;

    @Test
    void submit(){
        when(candidateService.findAll()).thenReturn(Instancio.stream(Candidate.class).limit(10).toList());

        service.submit();

        verify(redisElectionRepository).submit(any(Election.class));
        verify(sqlElectionRepository).submit(any(Election.class));
        verifyNoMoreInteractions(redisElectionRepository);
        verifyNoMoreInteractions(sqlElectionRepository);
    }
}