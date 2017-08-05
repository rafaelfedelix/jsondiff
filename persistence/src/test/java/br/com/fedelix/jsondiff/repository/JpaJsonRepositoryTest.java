package br.com.fedelix.jsondiff.repository;

import br.com.fedelix.jsondiff.dao.JsonLeftDAO;
import br.com.fedelix.jsondiff.dao.JsonRightDAO;
import br.com.fedelix.jsondiff.domain.JsonLeftDomain;
import br.com.fedelix.jsondiff.domain.JsonRightDomain;
import br.com.fedelix.jsondiff.exception.NoDataException;
import br.com.fedelix.jsondiff.model.DiffData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JpaJsonRepositoryTest {

    private JpaJsonRepository jpaJsonRepository;
    private JsonLeftDAO jsonLeftDAO = mock(JsonLeftDAO.class);
    private JsonRightDAO jsonRightDAO = mock(JsonRightDAO.class);

    @Before
    public void setUp() {
        jpaJsonRepository = new JpaJsonRepository(jsonLeftDAO, jsonRightDAO);
    }

    @Test
    public void shouldSaveOnLeft() {
        // when
        jpaJsonRepository.saveOnLeft(1L, "ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        // then
        verify(jsonLeftDAO).save(any(JsonLeftDomain.class));
    }

    @Test
    public void shouldSaveOnRight() {
        // when
        jpaJsonRepository.saveOnRight(1L, "ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        // then
        verify(jsonRightDAO).save(any(JsonRightDomain.class));
    }

    @Test
    public void shouldFindLeftById() {
        // given
        JsonLeftDomain domain = new JsonLeftDomain();
        domain.id = 1L;
        domain.encodedJson = "ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=";
        when(jsonLeftDAO.findOne(any())).thenReturn(domain);
        // when
        DiffData response = jpaJsonRepository.getLeftById(1L);
        // then
        assertNotNull(response);
    }

    @Test(expected = NoDataException.class)
    public void shouldNotFindLeftById() {
        // when
        jpaJsonRepository.getLeftById(346354346L);
    }

    @Test
    public void shouldFindRightById() {
        // given
        JsonRightDomain domain = new JsonRightDomain();
        domain.id = 1L;
        domain.encodedJson = "ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=";
        when(jsonRightDAO.findOne(any())).thenReturn(domain);
        // when
        DiffData response = jpaJsonRepository.getRightById(1L);
        // then
        assertNotNull(response);
    }

    @Test(expected = NoDataException.class)
    public void shouldNotFindRightById() {
        // when
        jpaJsonRepository.getLeftById(346354346L);
    }
}