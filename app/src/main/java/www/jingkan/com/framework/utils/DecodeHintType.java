/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.utils;

/**
 * Encapsulates a type of hint that a caller may pass to a barcode reader to help it
 * more quickly or accurately decode it. It is up to implementations to decide what,
 * if anything, to do with the information that is supplied.
 *
 * @author Sean Owen
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class DecodeHintType {

    // No, we can't use an enum here. J2ME doesn't support it.

    /**
     * Unspecified, application-specific hint. Maps to an unspecified {@link Object}.
     */
    public static final DecodeHintType OTHER = new DecodeHintType();

    /**
     * Image is a pure monochrome image of a barcode. Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    public static final DecodeHintType PURE_BARCODE = new DecodeHintType();


    public static final DecodeHintType POSSIBLE_FORMATS = new DecodeHintType();

    /**
     * Spend more time to try to find a barcode; optimize for accuracy, not speed.
     * Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    public static final DecodeHintType TRY_HARDER = new DecodeHintType();

    /**
     * Specifies what character encoding to use when decoding, where applicable (type String)
     */
    public static final DecodeHintType CHARACTER_SET = new DecodeHintType();

    /**
     * Allowed lengths of encoded data -- reject anything else. Maps to an int[].
     */
    public static final DecodeHintType ALLOWED_LENGTHS = new DecodeHintType();

    /**
     * Assume Code 39 codes employ a check digit. Maps to {@link Boolean}.
     */
    public static final DecodeHintType ASSUME_CODE_39_CHECK_DIGIT = new DecodeHintType();


    public static final DecodeHintType NEED_RESULT_POINT_CALLBACK = new DecodeHintType();

    private DecodeHintType() {
    }

}
