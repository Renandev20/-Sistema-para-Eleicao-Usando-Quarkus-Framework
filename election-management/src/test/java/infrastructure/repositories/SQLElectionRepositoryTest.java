package infrastructure.repositories;

import domain.ElectionRepository;
import domain.ElectionRepositoryTest;
import domain.annotations.SQL;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.AfterEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@QuarkusTest
class SQLElectionRepositoryTest extends ElectionRepositoryTest {

    @InjectSpy
    @SQL
    SQLElectionRepository repository;
    @Inject
    EntityManager entityManager;

    @Override
    public ElectionRepository repository() {
        return repository;
    }

    @AfterEach
    @TestTransaction
    void tearDown() {
        entityManager.createNativeQuery("TRUNCATE TABLE election_candidate").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE elections").executeUpdate();
    }
}