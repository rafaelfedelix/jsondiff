package br.com.fedelix.jsondiff.model;

public class Difference {

    private int offset;
    private int length;

    public Difference(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }
}
