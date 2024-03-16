package domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.ElectionCacheTest.ELECTION_ID;
import static infrastructure.repositories.RedisElectionRepositoryTest.CANDIDATES_SIZE;
import static org.junit.jupiter.api.Assertions.*;

public abstract class ElectionRepositoryTest {

    public static final String NON_EXISTENT_ELECTION = "NonExistentElection";

    public abstract ElectionRepository repository();

    public abstract int numberVotesForCandidateInAnElection(Election election, Candidate candidate);

    @Test
    void findAll() {
        List<Election> result = repository().findAll();

        assertEquals(CANDIDATES_SIZE, result.get(0).candidates().size());
    }

    @Test
    void findByIdWithExistentId() {
        Election result = repository().findById(ELECTION_ID);

        assertAll("find the election",
                () -> assertEquals(ELECTION_ID, result.id()),
                () -> assertEquals(CANDIDATES_SIZE, result.candidates().size())
        );
    }

    @Test
    void findByIdWithNonExistentId() {
        Election result = repository().findById(NON_EXISTENT_ELECTION);

        assertAll("returned election that has no candidates",
                () -> assertEquals(NON_EXISTENT_ELECTION, result.id()),
                () -> assertTrue(result.candidates().isEmpty())
        );
    }

    @Test
    void vote() {
        Candidate candidate = repository().findById(ELECTION_ID).candidates().get(0);

        repository().vote(ELECTION_ID, candidate);

        int result = numberVotesForCandidateInAnElection(repository().findById(ELECTION_ID), candidate);

        assertEquals(1, result);
    }
}