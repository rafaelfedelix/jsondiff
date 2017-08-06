package br.com.fedelix.jsondiff.services;

import br.com.fedelix.jsondiff.repository.JsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private JsonRepository jsonRepository;

    @Autowired
    public PersistenceService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    /**
     * Saves the json on Left domain
     * @param id the json id
     * @param encodedJson base 64 encoded json
     */
    public void saveOnLeft(Long id, String encodedJson) {
        jsonRepository.saveOnLeft(id, encodedJson);
    }

    /**
     * Saves the json on Right domain
     * @param id the json id
     * @param encodedJson base 64 encoded json
     */
    public void saveOnRight(Long id, String encodedJson) {
        jsonRepository.saveOnRight(id, encodedJson);
    }
}