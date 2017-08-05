package br.com.fedelix.jsondiff.dao;

import br.com.fedelix.jsondiff.domain.JsonLeftDomain;
import org.springframework.data.repository.CrudRepository;

public interface JsonLeftDAO extends CrudRepository<JsonLeftDomain, Long> {

}