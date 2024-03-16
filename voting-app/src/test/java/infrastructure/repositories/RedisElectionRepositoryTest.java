package infrastructure.repositories;

import domain.*;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

@QuarkusTest
public class RedisElectionRepositoryTest extends ElectionRepositoryTest {

    public static final int CANDIDATES_SIZE = 10;
    public static final String KEY = "election:";
    @Inject
    RedisElectionRepository repository;
    @Inject
    RedisDataSource dataSource;

    @Override
    public ElectionRepository repository() {
        return repository;
    }

    @Override
    public int numberVotesForCandidateInAnElection(Election election, Candidate candidate) {

        SortedSetCommands<String, String> commands = dataSource.sortedSet(String.class, String.class);

        return (int) commands.zscore(KEY + election.id(), candidate.id()).orElse(0);
    }

    @BeforeEach
    @TestTransaction
    void setup() {

        ElectionCacheTest election = ElectionCacheTest.create(Instancio.stream(Candidate.class).limit(CANDIDATES_SIZE).toList());

        SortedSetCommands<String, String> commands = dataSource.sortedSet(String.class, String.class);

        Map<String, Double> rank = election.votes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().id(),
                        entry -> entry.getValue().doubleValue()));

        commands.zadd(KEY + election.id(), rank);
    }

    @AfterEach
    @TestTransaction
    void tearDown() {
        dataSource.flushall();
    }
}