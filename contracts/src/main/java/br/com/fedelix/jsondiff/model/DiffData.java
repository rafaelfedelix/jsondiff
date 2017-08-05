package br.com.fedelix.jsondiff.model;

public class DiffData {

    private String encodedJson;

    public DiffData(String encodedJson) {
        this.encodedJson = encodedJson;
    }

    public String getEncodedJson() {
        return encodedJson;
    }
}
