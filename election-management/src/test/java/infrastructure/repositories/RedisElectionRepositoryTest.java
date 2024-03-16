package infrastructure.repositories;

import domain.ElectionRepository;
import domain.ElectionRepositoryTest;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;

@QuarkusTest
class RedisElectionRepositoryTest extends ElectionRepositoryTest {

    @InjectSpy
    RedisElectionRepository repository;
    @Inject
    RedisDataSource dataSource;

    @Override
    public ElectionRepository repository() {
        return repository;
    }

    @AfterEach
    @TestTransaction
    void tearDown() {
        dataSource.flushall();
    }
}