package br.com.fedelix.jsondiff.services;

import br.com.fedelix.jsondiff.repository.JsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private JsonRepository jsonRepository;

    @Autowired
    PersistenceService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    public void saveOnLeft(Long id, String encodedJson) {
        jsonRepository.saveOnLeft(id, encodedJson);
    }

    public void saveOnRight(Long id, String encodedJson) {
        jsonRepository.saveOnRight(id, encodedJson);
    }
}