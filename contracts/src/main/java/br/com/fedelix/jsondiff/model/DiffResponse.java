package br.com.fedelix.jsondiff.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiffResponse {

    private String message;
    private List<Difference> differences = new ArrayList<>();

    public DiffResponse() {
        this("Differences found");
    }

    public DiffResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Difference> getDifferences() {
        return differences;
    }

    public void addDifference(Difference difference) {
        this.differences.add(difference);
    }
}
