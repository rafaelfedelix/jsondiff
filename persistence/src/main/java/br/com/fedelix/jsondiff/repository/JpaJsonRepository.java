package br.com.fedelix.jsondiff.repository;

import br.com.fedelix.jsondiff.dao.JsonLeftDAO;
import br.com.fedelix.jsondiff.dao.JsonRightDAO;
import br.com.fedelix.jsondiff.domain.JsonLeftDomain;
import br.com.fedelix.jsondiff.domain.JsonRightDomain;
import br.com.fedelix.jsondiff.exception.NoDataException;
import br.com.fedelix.jsondiff.model.DiffData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JpaJsonRepository implements JsonRepository {

    private JsonLeftDAO jsonLeftDAO;
    private JsonRightDAO jsonRightDAO;

    @Autowired
    JpaJsonRepository(JsonLeftDAO jsonLeftDAO, JsonRightDAO jsonRightDAO) {
        this.jsonLeftDAO = jsonLeftDAO;
        this.jsonRightDAO = jsonRightDAO;
    }

    @Override
    public void saveOnLeft(Long id, String encodedJson) {
        JsonLeftDomain domain = new JsonLeftDomain();
        domain.id = id;
        domain.encodedJson = encodedJson;
        jsonLeftDAO.save(domain);
    }

    @Override
    public void saveOnRight(Long id, String encodedJson) {
        JsonRightDomain domain = new JsonRightDomain();
        domain.id = id;
        domain.encodedJson = encodedJson;
        jsonRightDAO.save(domain);
    }

    @Override
    public DiffData getLeftById(Long id) {
        JsonLeftDomain left = jsonLeftDAO.findOne(id);
        if (null != left) {
            return new DiffData(left.encodedJson);
        }
        throw new NoDataException("No data found for id " + id);
    }

    @Override
    public DiffData getRightById(Long id) {
        JsonRightDomain left = jsonRightDAO.findOne(id);
        if (null != left) {
            return new DiffData(left.encodedJson);
        }
        throw new NoDataException("No data found for id " + id);
    }
}