package br.com.fedelix.jsondiff.utils;

import javax.xml.bind.DatatypeConverter;

public class Decoder {

    public static String decodeJson(String encodedJson) {
        return new String(DatatypeConverter.parseBase64Binary(encodedJson));
    }
}