package br.com.fedelix.jsondiff.services;

import br.com.fedelix.jsondiff.model.DiffResponse;
import br.com.fedelix.jsondiff.model.Difference;
import br.com.fedelix.jsondiff.repository.JsonRepository;
import br.com.fedelix.jsondiff.utils.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiffService {

    private JsonRepository jsonRepository;

    @Autowired
    DiffService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    /**
     * Get the json difference by specified id
     * @param id json's id
     * @return new DiffResponse
     */
    public DiffResponse getDiff(Long id) {
        String decodedLeft = getDecodedLeftById(id);
        String decodedRight = getDecodedRightById(id);
        DiffResponse diff = new DiffResponse();
        if (isNullOrDifferentLength(decodedLeft, decodedRight)) {
            diff.setMessage("Left and right have different length");
        } else if (decodedLeft.equals(decodedRight)) {
            diff.setMessage("Left and right are equal");
        } else {
            diff = buildDiffResponse(decodedLeft, decodedRight);
        }
        return diff;
    }

    private DiffResponse buildDiffResponse(String decodedLeft, String decodedRight) {
        DiffResponse response = new DiffResponse();
        int offset = 0;
        int length = 0;
        for (int index = 0; index < decodedLeft.length(); index++) {
            char left = decodedLeft.charAt(index);
            char right = decodedRight.charAt(index);
            if (shouldAddDifference(decodedLeft, length, index, left, right)) {
                response.addDifference(new Difference(offset, length));
                length = 0;
            }
            if (left != right) {
                if (length == 0) {
                    offset = index;
                }
                length++;
            }
        }
        return response;
    }

    private String getDecodedLeftById(Long id) {
        String leftData = jsonRepository.getLeftById(id).getEncodedJson();
        return Decoder.decodeJson(leftData);
    }

    private String getDecodedRightById(Long id) {
        String rightData = jsonRepository.getRightById(id).getEncodedJson();
        return Decoder.decodeJson(rightData);
    }

    private boolean isNullOrDifferentLength(String leftData, String rightData) {
        return null == leftData || null == rightData || leftData.length() != rightData.length();
    }

    private boolean shouldAddDifference(String decodedLeft, int length, int index, char left, char right) {
        return isEqualsAndHasLength(length, left, right) || hasLengthAndReachEnd(decodedLeft, length, index);
    }

    private boolean isEqualsAndHasLength(int length, char left, char right) {
        return left == right && length > 0;
    }

    private boolean hasLengthAndReachEnd(String decodedLeft, int length, int index) {
        return length > 0 && index == decodedLeft.length()-1;
    }
}