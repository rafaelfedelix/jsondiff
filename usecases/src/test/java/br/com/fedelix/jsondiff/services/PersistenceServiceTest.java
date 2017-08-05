package br.com.fedelix.jsondiff.services;

import br.com.fedelix.jsondiff.repository.JsonRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PersistenceServiceTest {

    private PersistenceService persistenceService;
    private JsonRepository jsonRepository = mock(JsonRepository.class);

    @Before
    public void setUp() {
        persistenceService = new PersistenceService(jsonRepository);
    }

    @Test
    public void shouldSaveOnLeft() {
        // when
        persistenceService.saveOnLeft(1L,"ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        // then
        verify(jsonRepository).saveOnLeft(any(), any());
    }

    @Test
    public void shouldSaveOnRight() {
        // when
        persistenceService.saveOnRight(1L,"ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        // then
        verify(jsonRepository).saveOnRight(any(), any());
    }
}