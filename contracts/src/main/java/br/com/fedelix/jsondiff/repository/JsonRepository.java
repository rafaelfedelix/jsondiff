package br.com.fedelix.jsondiff.repository;

import br.com.fedelix.jsondiff.model.DiffData;

public interface JsonRepository {

    void saveOnLeft(Long id, String encodedJson);
    void saveOnRight(Long id, String encodedJson);
    DiffData getLeftById(Long id);
    DiffData getRightById(Long id);
}
