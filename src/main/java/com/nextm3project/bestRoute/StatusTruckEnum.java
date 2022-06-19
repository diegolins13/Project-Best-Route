package com.nextm3project.bestRoute;

import java.util.Arrays;
import java.util.Locale;

public enum StatusTruckEnum {

	CHEIO, VAZIO;

    public static Boolean check(final String status){
        return Arrays.stream(StatusTruckEnum.values()).anyMatch(
                value -> value.name().equals(status.toUpperCase(Locale.ROOT)));
    }
}
