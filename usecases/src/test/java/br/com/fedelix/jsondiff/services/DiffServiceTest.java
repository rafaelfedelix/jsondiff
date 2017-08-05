package br.com.fedelix.jsondiff.services;

import br.com.fedelix.jsondiff.exception.NoDataException;
import br.com.fedelix.jsondiff.model.DiffData;
import br.com.fedelix.jsondiff.model.DiffResponse;
import br.com.fedelix.jsondiff.repository.JsonRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiffServiceTest {

    private DiffService diffService;
    private JsonRepository jsonRepository = mock(JsonRepository.class);

    @Before
    public void setUp() {
        diffService = new DiffService(jsonRepository);
    }

    @Test
    public void shouldGetTwoDifferences() {
        // given
        DiffData leftData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        DiffData rightData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzMWUyIjogNDU2Cn0=");
        when(jsonRepository.getLeftById(any())).thenReturn(leftData);
        when(jsonRepository.getRightById(any())).thenReturn(rightData);
        // when
        DiffResponse response = diffService.getDiff(1L);
        // then
        assertEquals(response.getMessage(), "Differences found");
        assertEquals(response.getDifferences().size(), 2);
    }

    @Test
    public void shouldGetNoDifferences() {
        // given
        DiffData data = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        when(jsonRepository.getLeftById(any())).thenReturn(data);
        when(jsonRepository.getRightById(any())).thenReturn(data);
        // when
        DiffResponse response = diffService.getDiff(1L);
        // then
        assertEquals(response.getMessage(), "Left and right are equal");
        assertEquals(response.getDifferences().size(), 0);
    }

    @Test
    public void shouldGetDifferentLength() {
        // given
        DiffData leftData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        DiffData rightData = new DiffData("");
        when(jsonRepository.getLeftById(any())).thenReturn(leftData);
        when(jsonRepository.getRightById(any())).thenReturn(rightData);
        // when
        DiffResponse response = diffService.getDiff(1L);
        // then
        assertEquals(response.getMessage(), "Left and right have different length");
        assertEquals(response.getDifferences().size(), 0);
    }

    @Test(expected = NoDataException.class)
    public void shouldThrowNoDataException() throws Exception {
        // given
        when(jsonRepository.getLeftById(any())).thenThrow(NoDataException.class);
        // when
        diffService.getDiff(1L);
    }
}