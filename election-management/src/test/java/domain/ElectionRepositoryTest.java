package domain;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.mockito.Mockito.verify;

public abstract class ElectionRepositoryTest {
    public abstract ElectionRepository repository();

    @Test
    void submit() {
        Election election = Instancio.create(Election.class);

        repository().submit(election);

        verify(repository()).submit(election);
    }
}