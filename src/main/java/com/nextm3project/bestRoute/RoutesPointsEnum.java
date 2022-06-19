package com.nextm3project.bestRoute;

import java.util.Arrays;
import java.util.Locale;

public enum RoutesPointsEnum {

	INT1, INT2, INT3, INT4, INT5, INT6, INT7, INT8, INT9, INT10, INT11, INT12, INT13,
    ESC1, ESC2, ESC3,
    DESC1, DESC2, DESC3;

    public static Boolean check(final String location){
        return Arrays.stream(RoutesPointsEnum.values()).anyMatch(
                value -> value.name().equals(location.toUpperCase(Locale.ROOT)));
    }
}
