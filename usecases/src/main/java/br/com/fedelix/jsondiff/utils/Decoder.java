package br.com.fedelix.jsondiff.utils;

import javax.xml.bind.DatatypeConverter;

public class Decoder {

    /**
     * Decodes the base 64 encoded json
     * @param encodedJson to be decoded
     * @return decoded json
     */
    public static String decodeJson(String encodedJson) {
        return new String(DatatypeConverter.parseBase64Binary(encodedJson));
    }
}